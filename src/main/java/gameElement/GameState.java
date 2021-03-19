package gameElement;

import display.GridMap;
import entity.living.Player;

/**
 * This class represents the State of the Game at any moment. Each action will pass by this class.
 * It determines rather or not if the action is permitted.
 *
 * Each state of the game is determined by an int :
 * states :
 *         0 -> End
 *         1 -> Game
 *         2 -> Menu
 *         3 -> MiniMap
 *
 * @author Antoine
 */

public class GameState {
    private int state;
    private Room currentRoom;
    private final Player player;
    private final Dungeon dungeon;
    private final GridMap gridMap;

    public GameState(Player player, Dungeon dungeon, GridMap gridMap) {
        this.dungeon = dungeon;
        this.player = player;
        this.gridMap = gridMap;
        this.currentRoom = dungeon.roomList.get(0);
        player.setPosition(currentRoom.getCenter());
        state = 1;
        gridMap.update();
    }

    public int getState() {
        return state;
    }

    /**
     * Checks if the player can access the tile at his position + x & y, and changes its position if so.
     *
     * @param x Abscissa of the tile to check, using the player's position as a base.
     * @param y Ordinate of the tile to check, using the player's position as a base.
     *
     * @author Raphael
     */
    public boolean movePlayer(int x, int y) {
        boolean acted = false;
        int abs = player.getPosition().getAbs();
        int ord = player.getPosition().getOrd();

        if (gridMap.getTileAt(abs + x, ord + y).isAccessible()) {
            player.getPosition().updatePos(x, y);
            gridMap.update();
            acted = true;
        }
        return acted;
    }

    public void exitGame() {
        state = 0;
    }
}
