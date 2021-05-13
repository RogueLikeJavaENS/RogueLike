package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 * Strategy used to choose if the monster should apply the MoveAwayStrategy
 *
 */
public class EscapeStrategy extends MoveAwayStrategy {
    private final Condition condition;

    /**
     * Create the escape strategy
     * @param condition condition needed to apply this strategy instead of the next
     * @param nextStrategy the next strategy
     */
    public EscapeStrategy(Condition condition, Strategy nextStrategy){
        super(nextStrategy);
        this.condition = condition;
    }

    /**
     * Verify the condition and ask MoveAwayStrategy to act
     *
     * @param monster
     * @param player
     * @param gridMap
     * @return
     */
    public boolean act(Monster monster, Player player, GridMap gridMap){
        boolean isVerified = condition.isVerified(monster,player); // verifiy the condition
        return isVerified && super.act(monster, player, gridMap); // ask MoveAwayStrategy to act
    }
}
