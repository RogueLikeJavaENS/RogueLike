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
        boss.updatePosBoss(gridmap);
        if (boss.hasSpecial()) {
            boss.doSpecialAction(player, gridmap);
            boss.setActedSpecial(false);
        } else {
            boss.setActedSpecial(true);
        }
        return true;
    }
}