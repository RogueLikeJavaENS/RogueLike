package game.entity.living.npc.monster.boss;

import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.boss.bosses.KillerRabbit;
import game.entity.living.npc.monster.monsterStrategy.bossStrategy.BossApproachStrategy;
import game.entity.living.npc.monster.monsterStrategy.bossStrategy.BossAttackStrategy;
import utils.Colors;
import utils.Position;

/**
 * Factory to generate bosses.
 */
public class BossFactory {

    private final int floor;

    public BossFactory(int floor){
        this.floor = floor;
    }

    public Boss getBoss(Bosses bossType, Position position) {
        Boss boss;
        if (bossType == Bosses.KILLER_RABBIT) {
            boss = new KillerRabbit("Killer Rabbit",
                    position,
                    Colors.PINK,
                    new BossAttackStrategy(new BossApproachStrategy(null)),
                    new MonsterStats(120 + (floor * 5 * 2), 1, 1, 1, 5 + floor, 5, 100, floor + 3, 200));
        } else {
            boss = new KillerRabbit("Killer Rabbit?",
                    position,
                    Colors.PINK,
                    new BossAttackStrategy(new BossApproachStrategy(null)),
                    new MonsterStats(120 + (floor * 5 * 2), 1, 1, 1, 5 + floor, 5, 100, floor + 3, 200));
        }
        return boss;
    }
}
