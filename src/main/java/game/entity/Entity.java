package game.entity;

import game.element.GameState;
import utils.Position;

/**
 * This class is the interface of all type of gameElement.entity which can be on a room in the game
 *
 * @author Juliette
 * */

public interface Entity {
    void doAction(GameState gameState);
    void doInteraction(GameState gameState);
    String toString();
    String getSprites(int i);
    Position getPosition();
    boolean getIsNPCAccessible();
    boolean getIsPlayerAccessible();
    boolean isMonster();
    boolean isTrap();
    boolean isDestroyable();
    boolean isBossPart();
}
