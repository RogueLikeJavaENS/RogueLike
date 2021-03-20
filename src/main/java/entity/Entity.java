package entity;

import gameElement.GameState;
import utils.Position;

/**
 * This class is the interface of all type of entity who can be on a room in the game
 *
 * @author Juliette
 * */

public interface Entity {

    String toString();
    Position getPosition();
    void doAction(GameState gameState);
}
