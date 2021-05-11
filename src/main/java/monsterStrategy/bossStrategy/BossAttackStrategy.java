package monsterStrategy.bossStrategy;

import display.GridMap;
import entity.living.npc.monster.Monster;
import entity.living.npc.monster.boss.Boss;
import entity.living.player.Player;
import monsterStrategy.DecoratorStrategy;
import monsterStrategy.Strategy;
import monsterStrategy.StrategyUtils;

public class BossAttackStrategy extends DecoratorStrategy {

    public BossAttackStrategy(Strategy strategy) {
        super(strategy);
    }

    public boolean act(Monster monster, Player player, GridMap gridmap) {
        Boss boss = (Boss) monster;
        boolean canAttack = StrategyUtils.isNextToBoss(boss, player);
        if (canAttack) {
            int damage = boss.getMonsterStats().getDamageTotal();
            player.getPlayerStats().sufferDamage(damage);
            this.updateStrategyDescription(String.format("%s attacked and inflicted %s damages to %s", boss.getName(), damage, player.getName()));
        }
        return canAttack;
    }
}
