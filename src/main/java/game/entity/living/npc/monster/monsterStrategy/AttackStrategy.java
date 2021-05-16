package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Strategy used when a Monster is close enough to attack the player and make the monster attack
 *
 */
public class AttackStrategy extends DecoratorStrategy {

    /**
     * Create the attack strategy
     * @param strategy always null because attack is the last used strategy
     */
    public AttackStrategy(Strategy strategy){
        super(strategy);
    }

    /**
     * Make the monster attack the player
     *
     * @param monster the monster which use this strategy
     * @param player the player
     * @param gridMap the gridMap where is the monster
     * @return always true
     */
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        // look if the player has avoid the attack with his agility
        if (player.getPlayerStats().hasAvoided()){
            this.updateStrategyDescription(String.format("%s",player.getName()) + colorize(" dodged ", Colors.GREEN.textApply()) + String.format("%s's attack!", monster.getName()));
        }
        // if he don't avoid the attack, calculate the damage make to the player, inflict the damage and update the descriptor
        else {
            int damage =  Math.max(13, 2 * monster.getMonsterStats().getDamageNatural() + 3 * monster.getMonsterStats().getLevel());

            damage = player.getPlayerStats().sufferDamage(damage);

            this.updateStrategyDescription(String.format("%s attacked and inflicted %s damages to %s", monster.getName(), colorize(Integer.toString(damage), Colors.RED.textApply()), player.getName()));
        }
        return true;   // always because when we arrive to this strategy we can always attack the player
    }
}
