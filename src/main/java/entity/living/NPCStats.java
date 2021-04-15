package entity.living;

import utils.Check;

/**
 * class extending the AbstractStats class for managing Monster stats
 * @author luca
 */

public class NPCStats extends AbstractStats {

    public NPCStats(int lifePoint, int manaPoint, int range, int initiative, int damage, int armor, int money, int level) {
        super(lifePoint, manaPoint, range, initiative, damage, armor, money, level);
    }
}
