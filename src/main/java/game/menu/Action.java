package game.menu;

import game.elements.GameState;

/**
 * An action takes the gameState. Functional Interface.
 * @author Antoine
 */

public interface Action {
    void doAction(GameState state);
}
