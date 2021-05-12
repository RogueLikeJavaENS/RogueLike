package game.entity.living.npc.monster;

import game.entity.living.AbstractStats;
import utils.Check;

/**
 * class extending the AbstractStats class for managing Monster stats
 * @author luca
 */
public class MonsterStats extends AbstractStats {
    private int xpWorth;

    /**
     *  Create a new set of Stats for the monsters
     *
     * @param lifePoint of the monster
     * @param manaPoint of the monster
     * @param range of the monster
     * @param initiative of the monster
     * @param damage caused by the monster
     * @param armor of the monster
     * @param money of the monster
     * @param level of the monster
     * @param xpWorth xp gain by the player when he kill the monster
     */
    public MonsterStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int money, int level, int xpWorth) {
        super(lifePoint, manaPoint, range, initiative, damage, armor, money, level);
        this.xpWorth = xpWorth;
    }

    /**
     * Return the amount of xp gain by the player when he kill the monster
     *
     * @return xpWorth
     */
    public int getXpWorth() {
        return xpWorth;
    }

    /**
     * Set the amount of XpWorth of the monster
     *
     * @param xpWorth an int
     */
    public void setXpWorth(int xpWorth) {
        Check.checkPositivity(xpWorth);
        this.xpWorth = xpWorth;
    }
}
