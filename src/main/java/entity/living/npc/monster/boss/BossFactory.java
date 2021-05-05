package entity.living.npc.monster.boss;

import entity.living.npc.monster.MonsterStats;
import entity.living.npc.monster.boss.bosses.KillerRabbit;
import monsterStrategy.Strategy;
import monsterStrategy.bossStrategy.BossApproachStrategy;
import utils.Colors;
import utils.Position;

public class BossFactory {

    private final int floor;

    public BossFactory(int floor){
        this.floor = floor;
    }

    public Boss getBoss(Bosses bossType, Position position) {
        Boss boss;
        switch (bossType) {
            case KILLER_RABBIT:
                boss = new KillerRabbit("Killer Rabbit",
                        position,
                        Colors.PINK,
                        new BossApproachStrategy(null),
                        new MonsterStats(250,1,1,1,5,5,5,floor+3,200));
                break;
            default:
                boss = new KillerRabbit("Killer Rabbit?",
                        position,
                        Colors.PINK,
                        new BossApproachStrategy(null),
                        new MonsterStats(250,1,1,1,5,5,5,floor+3,200));
                break;
        }
        return boss;
    }
}
