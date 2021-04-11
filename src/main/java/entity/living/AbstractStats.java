package entity.living;

import java.util.List;

/**
 * Abstract class managing the stats of any living entity
 * @author luca
 */

public abstract class AbstractStats {
    private int lifePoint;
    private int manaPoint;
    private int range;
    private int rawDamage;
    private int level;

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRawDamage() {
        return rawDamage;
    }

    public void setRawDamage(int rawDamage) {
        this.rawDamage = rawDamage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public AbstractStats(int lifePoint, int manaPoint, int range, int rawDamage, int level) {
        this.lifePoint = lifePoint;
        this.manaPoint = manaPoint;
        this.range = range;
        this.rawDamage = rawDamage;
        this.level = level;
    }
}
