package game.entity.living.npc.monster.monsterStrategy.bossStrategy;

import display.GridMap;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.boss.Boss;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.monsterStrategy.DecoratorStrategy;
import game.entity.living.npc.monster.monsterStrategy.Strategy;

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