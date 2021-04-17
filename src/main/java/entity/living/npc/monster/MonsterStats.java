package entity.living.npc.monster;

import entity.living.AbstractStats;
import utils.Check;

/**
 * class extending the AbstractStats class for managing Monster stats
 * @author luca
 */

public class MonsterStats extends AbstractStats {

    private int xpWorth;

    public int getXpWorth() {
        return xpWorth;
    }

    public void setXpWorth(int xpWorth) {
        Check.checkPositivity(xpWorth);
        this.xpWorth = xpWorth;
    }

    public MonsterStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int money, int level, int xpWorth) {
        super(lifePoint, manaPoint, range, initiative, damage, armor, money, level);
        this.xpWorth = xpWorth;
    }
}
