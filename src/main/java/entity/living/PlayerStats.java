package entity.living;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * class adding to AbstractStats (by extending it) the stats necessary to a player.
 * @author luca
 */

public class PlayerStats extends AbstractStats{
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

    public PlayerStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int money, int level) {
        super(lifePoint, manaPoint, range, initiative, damage, armor, money, level);
        this.classFactor= new int[] {10,10,1,1,1};
        this.xp=0;
        this.levelCap=loadXpPerLevel();
    }

    /**
     * This method allow to read a .txt file used to store all xp cap for each level
     * source : https://codereview.stackexchange.com/questions/208278/simple-text-based-rpg-leveling-system
     * with some adjustment.
     * @return Map of xp cap necessary to reach a level
     */
//    private Map<Integer, Integer> loadXpPerLevel() {
//        Map<Integer, Integer> xpPerLevel = new LinkedHashMap<>();
//        AtomicInteger capForLevel = new AtomicInteger(1);
//        try (Stream<String> lines = Files.lines(Paths.get("xpPerLevel.txt"))) {
//            lines.forEach(line -> xpPerLevel.put(capForLevel.getAndIncrement(), Integer.valueOf(line)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return xpPerLevel;
//    }

    private Map<Integer, Integer> loadXpPerLevel(){
        int baseXPneeded = 100;
        int xpGivenByAMonster = 10;
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
        upgradeLifePointTotal(classFactor[0]);
        upgradeManaPointTotal(classFactor[1]);
        upInitiativeNatural(classFactor[2]);
        changeDamageRaw(classFactor[3]);
        changeArmorNatural(classFactor[4]);
        setLevel(getLevel()+1);
    }
}
