package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 * Strategy used when a monster is not aggro to the player.
 * The monster move randomly on the room
 *
 */
public class IdleStrategy extends DecoratorStrategy {
    Condition condition;

    /**
     *Create the IDLE Strategy
     *
     * @param condition condition needed to apply this strategy instead of the next
     * @param nextStrategy the next strategy
     */
    public IdleStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    /**
     *Make the monster move randomly if the condition is verified
     *
     * @param monster the monster
     * @param player the player
     * @param gridMap the gridMap where is the player
     * @return true if the player used this strategy
     */
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = condition.isVerified(monster,player);
        // if the condition is verified
        if (canMove) {
            if (monster.isAgroPlayer()){ // if the monster is aggro make him not aggro
                monster.setAgroPlayer(false);
            }
            updateStrategyDescription(null);
            StrategyUtils.moveRandomly(monster, gridMap); // make the monster move randomly and update the descriptor
            this.updateStrategyDescription(String.format("%s couldn't see %s.",monster.getName(), player.getName()));
        }
        return canMove; // true if the strategy was applied else false
    }

}
