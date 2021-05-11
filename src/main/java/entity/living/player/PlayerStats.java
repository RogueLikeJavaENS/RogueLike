package entity.living.player;

import classeSystem.*;
import entity.living.AbstractStats;
import spells.BasicAttack;
import spells.FireAura;
import spells.FireBall;
import spells.Spell;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * class adding to AbstractStats (by extending it) the stats necessary to a player.
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
        this.classe=classe;
        spellList = new ArrayList<>();
        getRewardForLevelForClass(classe, level);
        selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
        switch (classe.ordinal()){
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
        this.levelCap=loadXpPerLevel();
        checkCurrentXP();
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
    public void grantXP(int xpAmount) {
        utils.Check.checkPositivity(xpAmount);
        xp += xpAmount;
        checkCurrentXP();
    }

    private void checkCurrentXP() {
        setXpRequired(levelCap.get(getLevel()));
        int MAX_LEVEL = 100;
        while (getXp() >= getXpRequired() && getLevel()< MAX_LEVEL) {
            levelUp();
            setXp(getXp() - getXpRequired());
            setXpRequired(levelCap.get(getLevel()));
        }
        if (getLevel()== MAX_LEVEL){
            setXp(0);
        }
    }

    private void levelUp(){
        upgradeLifePointNatural(classFactor[0]);
        upgradeManaPointNatural(classFactor[1]);
        upInitiativeNatural(classFactor[2]);
        changeDamageNatural(classFactor[3]);
        changeArmorNatural(classFactor[4]);
        setLevel(getLevel()+1);
        getRewardForLevelForClass(getClasse(), getLevel());
    }

    private void getRewardForLevelForClass(InGameClasses classe, int level){
        switch (classe){
            case DUMMY:
            case RANGER:
            case WARRIOR:
            case MAGE:
                for (pathMage reward:
                     pathMage.values()) {
                    if (reward.getLevel().equals(level)){
                        addSpell(reward.getReward());
                    }
                }
                break;
        }
    }

//    public void addSpell(Spell spell) {
//        spellList.add(spell);
//    }

    public void addSpell(String spellname){
        try {
            Class<?> spellLookUp = Class.forName("spells."+spellname);
            spellList.add((Spell) spellLookUp.getDeclaredConstructor().newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedSpell(Spell selectedSpell) { this.selectedSpell = selectedSpell; }
    public List<Spell> getSpells() { return spellList; }
    public Spell getSelectedSpell() { return selectedSpell; }
    public  InGameClasses getClasse() {return classe;}
}
