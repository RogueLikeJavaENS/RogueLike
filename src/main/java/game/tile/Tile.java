package game.tile;

import game.elements.GameState;

/**
 * A Tile contains its type (as an int), its sprite (how it will be displayed)
 * and whether it is accessible or not.
 *
 * @author Raphael
 */

public interface Tile {

    Tile getTileFromId(int id);
    String toString();
    boolean isPlayerAccessible();
    void setPlayerAccessible(boolean isPlayerAccessible);
    boolean isNPCAccessible();
    void setNPCAccessible(boolean isNPCAccessible);
    void doAction(GameState gameState);
}
