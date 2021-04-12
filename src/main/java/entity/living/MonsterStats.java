package entity.living;

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
        this.xpWorth = xpWorth;
    }

    public MonsterStats(int lifePoint, int manaPoint, int range, int rawDamage, int naturalArmor, int level, int xpWorth) {
        super(lifePoint, manaPoint, range, rawDamage, naturalArmor, level);
        this.xpWorth = xpWorth;
    }
}
