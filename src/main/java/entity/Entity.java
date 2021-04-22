package entity;

import gameElement.GameState;
import utils.Position;

/**
 * This class is the interface of all type of entity which can be on a room in the game
 *
 * @author Juliette
 * */

public interface Entity {

    void doAction(GameState gameState);

    String toString();

    String getSprites(int i);
    Position getPosition();
    boolean getIsAccessible();
    void doInteraction(GameState gameState);
}
