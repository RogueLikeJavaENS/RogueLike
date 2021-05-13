package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 * Strategy used when a monster approach the player
 *
 */
public class ApproachStrategy extends DecoratorStrategy {
    private Condition condition;

    /**
     * Create the Approach strategy
     * @param condition condition needed to apply this strategy instead of the next
     * @param nextStrategy the next strategy
     */
    public ApproachStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    /**
     * Make the monster approach the player if he aggro the player and is not near enough to attack him
     *
     * @param monster the monster which use the strategy
     * @param player the player
     * @param gridMap the gridMap where the player and the monster are
     * @return true if the monster used this strategy
     */
    public boolean act(Monster monster, Player player, GridMap gridMap) {
        boolean canMove = StrategyUtils.getDistance(monster, player) > monster.getMonsterStats().getRangeTotal();
        // if the monster can't attack the player
        if (canMove){
            if (!monster.isAgroPlayer()){ // if the monster is not aggro make it aggro
                monster.setAgroPlayer(true);
            }
            StrategyUtils.aStarAlgorithmMonster(monster, player,gridMap); // search the best path to reach the player whit A* and make him move
            this.updateStrategyDescription(String.format("%s approaches %s",monster.getName(),player.getName())); //update the descriptor
        }
        return canMove; // return if the strategy was applied
    }

}
