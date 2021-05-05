package monsterStrategy.bossStrategy;

import display.GridMap;
import entity.living.npc.monster.Monster;
import entity.living.npc.monster.boss.Boss;
import entity.living.player.Player;
import monsterStrategy.DecoratorStrategy;
import monsterStrategy.Strategy;

public class BossApproachStrategy extends DecoratorStrategy {

    public BossApproachStrategy(Strategy strategy) {
        super(strategy);
    }

    public boolean act(Monster monster, Player player, GridMap gridmap) {
        Boss boss = (Boss) monster;
        if (boss.hasMoved()) {
            boss.doSpecialAction(player, gridmap);
            boss.setMoved(false);
        } else {
            boss.updatePosBoss(gridmap);
            boss.setMoved(true);
        }
        return true;
    }
}