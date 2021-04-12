package entity.living;

/**
 * class extending the AbstractStats class for managing Monster stats
 * @author luca
 */

public class MonsterStats extends AbstractStats {
    public MonsterStats(int lifePoint, int manaPoint, int range, int rawDamage, int naturalArmor, int level) {
        super(lifePoint, manaPoint, range, rawDamage, naturalArmor, level);
    }
}
