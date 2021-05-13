package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 * Strategy used to make the monster move away from the player position
 *
 */
public class MoveAwayStrategy extends DecoratorStrategy{

    /**
     *Create the move away strategy
     * @param strategy
     */
   public MoveAwayStrategy(Strategy strategy){
       super(strategy);
   }

    /**
     *Make the monster escape the player
     *
     * @param monster
     * @param player
     * @param gridMap
     * @return
     */
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = StrategyUtils.getDistance(monster,player) >= 1;
        if (canMove) {
            if (monster.isAgroPlayer()){ // if the monster si aggro make it not aggro
                monster.setAgroPlayer(false);
            }
            StrategyUtils.moveAwayFromPlayer(monster, player, gridMap);  // make the monster move away and update the descriptor
            this.updateStrategyDescription(String.format("%s is afraid and running away from %s",monster.getName(), player.getName()));
        }
        return canMove;
    }
}
