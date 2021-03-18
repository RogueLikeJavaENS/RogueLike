package gameElement;

import display.GridMap;
import entity.Player;
import utils.Position;

public class GameState {
    /*
     states :
        0 -> End
        1 -> Game
        2 -> Menu
        3 -> MiniMap
    */
    private int state;
    private Room currentRoom;
    private final Player player;
    private final Dungeon dungeon;
    private final MiniMap miniMap;

    public GameState(Player player, Dungeon dungeon, MiniMap miniMap, GridMap gridMap) {
        this.dungeon = dungeon;
        this.player = player;
        this.miniMap = miniMap;
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
     * @param player Player whose move will be checked and possibly executed.
     * @param x Abscissa of the tile to check, using the player's position as a base.
     * @param y Ordinate of the tile to check, using the player's position as a base.
     * @param gridMap GridMap containing the player.
     * @return Returns true if the player's position was updated successfully, false otherwise.
     *
     * @author Raphael
     */
    public void movePlayer(Player player, int x, int y, GridMap gridMap) {
        Position newPosition = new Position (player.getPosition().getAbs() + x, player.getPosition().getOrd() + y);
        if (gridMap.getTileAt(newPosition.getAbs(), newPosition.getOrd()).isAccessible()) {
            player.setPosition(newPosition);
            gridMap.update();
        }
    }

    public void displayMinimap() {
    }
}
