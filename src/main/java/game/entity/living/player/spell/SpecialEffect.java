package game.entity.living.player.spell;

import game.elements.GameState;

/**
 * Functional interface of effect. Uses the gameState to perform the action.
 */
public interface SpecialEffect {
    boolean doEffect(GameState gameState);
}
