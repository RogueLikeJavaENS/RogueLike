package game.entity.living.npc.monster.monsterStrategy.bossStrategy;

import display.GridMap;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.boss.Boss;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.monsterStrategy.DecoratorStrategy;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import game.entity.living.npc.monster.monsterStrategy.StrategyUtils;

/**
 * The boss attacks the player if the player is near of one of his part.
 */
public class BossAttackStrategy extends DecoratorStrategy {

    public BossAttackStrategy(Strategy strategy) {
        super(strategy);
    }

    public boolean act(Monster monster, Player player, GridMap gridmap) {
        Boss boss = (Boss) monster;
        boolean canAttack = StrategyUtils.isNextToBoss(boss, player);
        if (canAttack) {
            if (player.getPlayerStats().hasAvoided()){
                this.updateStrategyDescription(String.format("%s dodged %s's attack!", player.getName(), boss.getName()));
            } else {
                int damage = boss.getMonsterStats().getDamageTotal();
                player.getPlayerStats().sufferDamage(damage);
                this.updateStrategyDescription(String.format("%s attacked and inflicted %s damages to %s", boss.getName(), damage, player.getName()));
            }
        }
        return canAttack;
    }
}
