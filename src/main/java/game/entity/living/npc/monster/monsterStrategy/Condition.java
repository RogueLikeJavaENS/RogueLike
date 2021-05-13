package game.entity.living.npc.monster.monsterStrategy;

import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;

/**
 * Functional Class used to make the condition to apply a strategy.
 * A condition is always made with to object, a monster and the player
 */
public interface Condition {
    boolean isVerified(Monster monster, Player player);

}
