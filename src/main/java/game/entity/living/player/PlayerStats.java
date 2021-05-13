package game.entity.living.player;

import com.sun.jna.platform.mac.Carbon;
import game.elements.GameState;
import game.entity.living.player.classeSystem.*;
import game.entity.living.AbstractStats;
import game.entity.living.player.spell.Spell;
import utils.Colors;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * class adding to AbstractStats (by extending it) the stats necessary to a player.
 * class now also handle the player game.entity.living.player.spell List to more easily link it to its leveling process.
 * @author luca
 */

public class PlayerStats extends AbstractStats {
    private final List<Spell> spellList;
    private Spell selectedSpell;
    private final InGameClasses classe;
    private final int[] classFactor;
    private final Map<Integer, Integer> levelCap;
    private int xp;
    private int xpRequired;
    private int killCounter;
    private int turnPassed;
    private int bonusArmorTemporary;

    public int getXpRequired() {
        return xpRequired;
    }

    public void setXpRequired(int xpRequired) {
        this.xpRequired = xpRequired;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public PlayerStats(InGameClasses classe, int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int money, int level) {
        super(lifePoint, manaPoint, range, initiative, damage, armor, money, level);
        turnPassed = -1;
        this.classe=classe;
        spellList = new ArrayList<>();
        getRewardForLevelForClass(classe, level);
        selectedSpell = spellList.get(0);
         //Default selected attack is the BasicAttack
        switch (classe.ordinal()) {
            case 0:
                this.classFactor = new int[] {10, 7, 2, 1, 1};
                break;
            case 1:
                this.classFactor = new int[] {20, 5, 1, 2, 2};
                break;
            case 2:
                this.classFactor = new int[] {5, 20, 1, 0, 1};
                break;
            case 3:
            default:
                this.classFactor = new int[] {10, 10, 1, 1, 1};
                break;
        }
        this.xp=0;
        killCounter = 0;
        this.levelCap=loadXpPerLevel();
        checkCurrentXP();
    }

    /**
     * handle the temporary bonuses and what to do when the max turn allowed reach 0.
     */
    public void manageTemporaryBonus(){
        if (turnPassed != -1) {
            turnPassed--;
            if(turnPassed == 0){
                changeArmorTotal(-bonusArmorTemporary);
                bonusArmorTemporary = 0;
            }
        }
    }

    /**
     * set the values necessary to handle the temporary bonuses.
     * Only work for armor for now.
     * @param bonus value added
     * @param turn number of turn it should stay up
     */
    public void setBonusArmorTemporary(int bonus, int turn){
        this.turnPassed=turn;
        this.bonusArmorTemporary=bonus;
        changeArmorTotal(bonus);
    }

    private Map<Integer, Integer> loadXpPerLevel(){
        int baseXPneeded = 100;
        int xpGivenByAMonster = 25;
        Map<Integer, Integer> xpPerLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 100; i++) {
            xpPerLevel.put(i, baseXPneeded+(xpGivenByAMonster*i));
        }
        return xpPerLevel;
    }

    /**
     * check if xp amount is not inferior to zero, grant the xpAmount awarded to the player.
     * Then check if that was enough xp to allow him to up is level.
     * @param xpAmount xp to be added
     */
    public List<String> grantXP(int xpAmount, GameState gs) {
        utils.Check.checkPositivity(xpAmount);
        xp += xpAmount;
        List<String> descriptionLevelUp = checkCurrentXP();
        return descriptionLevelUp;
    }

    private List<String> checkCurrentXP() {
        List<String> descriptionLevelUp = new ArrayList<>();
        setXpRequired(levelCap.get(getLevel()));
        int MAX_LEVEL = 100;
        while (getXp() >= getXpRequired() && getLevel()< MAX_LEVEL) {
            descriptionLevelUp = levelUp();
            setXp(getXp() - getXpRequired());
            setXpRequired(levelCap.get(getLevel()));
        }
        if (getLevel()== MAX_LEVEL){
            setXp(0);
        }
        return descriptionLevelUp;
    }

    private List<String> levelUp(){
        upgradeLifePointNatural(classFactor[0]);
        upgradeManaPointNatural(classFactor[1]);
        upAgilityNatural(classFactor[2]);
        changeDamageNatural(classFactor[3]);
        changeArmorNatural(classFactor[4]);
        setLevel(getLevel()+1);
        Spell newSpell = getRewardForLevelForClass(getClasse(), getLevel());

        List<String> description = new ArrayList<>();
        description.add(String.format(" gained a %s, congratulations !",colorize("level",Colors.GREEN.textApply())));
        if (newSpell != null) {
            description.add(String.format(" learned a new Spell : %s which cost"+ colorize(" %d mana ", Colors.BLUE.textApply()) +"to use.", newSpell.getName(), newSpell.getManaCost()));
        }
        return description;
    }

    //extract what gain each class depending on their level
    private Spell getRewardForLevelForClass(InGameClasses classe, int level){
        Spell spellToAdd = null;
        switch (classe){
            case DUMMY:
            case RANGER:
                for (pathRanger reward:
                        pathRanger.values()) {
                    if (reward.getLevel() == level){
                        spellToAdd = addSpell(reward.getReward());
                    }
                }
                break;
            case WARRIOR:
                for (pathWarrior reward:
                        pathWarrior.values()) {
                    if (reward.getLevel() == level){
                       spellToAdd = addSpell(reward.getReward());
                    }
                }
                break;
            case MAGE:
                for (pathMage reward:
                     pathMage.values()) {
                    if (reward.getLevel() == level){
                        spellToAdd = addSpell(reward.getReward());
                    }
                }
                break;
        }
        return spellToAdd;
    }
    /**
     * add a Spell to the Player spellList using reflection.
     * @param spellname Name of the Spell being added.
     **/
    public Spell addSpell(String spellname){
        try {
            Class<?> spellLookUp = Class.forName("game.entity.living.player.spell.spells."+spellname);
            Spell spellToAdd = (Spell) spellLookUp.getDeclaredConstructor().newInstance();
            spellList.add(spellToAdd);
            return spellToAdd;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void incrementeKillCounter() {
        this.killCounter += 1;
    }
    public void setSelectedSpell(Spell selectedSpell) { this.selectedSpell = selectedSpell; }
    public void resetTurnPassed() {
        turnPassed = -1;
    }

    public int getKillCounter() {
        return killCounter;
    }

    public List<Spell> getSpells() { return spellList; }
    public Spell getSelectedSpell() { return selectedSpell; }
    public  InGameClasses getClasse() {return classe;}
    public int getTurnPassed() {
        return turnPassed;
    }
}
