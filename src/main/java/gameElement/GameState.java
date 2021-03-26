package gameElement;

import display.GridMap;
import entity.Entity;
import entity.living.Player;

import java.util.ArrayList;
import java.util.List;

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
    private GridMap gridMap;

    public GameState(Player player, Dungeon dungeon) {
        this.dungeon = dungeon;
        this.player = player;
        this.currentRoom = dungeon.getRoomList().get(0);
        this.gridMap = dungeon.getGridMap(currentRoom);
        player.setPosition(currentRoom.getCenter());
        state = 1;
        gridMap.update(player, true);
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
            acted = true;
        }
        return acted;
    }

    public void isOnEntity() {
        int playerAbs = player.getPosition().getAbs();
        int playerOrd = player.getPosition().getOrd();
        List<Entity> entitiesAt = gridMap.getEntitiesAt(playerAbs, playerOrd);
        for(Entity entity : entitiesAt) {
            if (entity != player) {
                entity.doAction(this);
            }
        }
    }

    public void updateChangingRoom(Room room) {
        gridMap.update(player, false);
        setCurrentRoom(room);
        setGridMap(dungeon.getGridMap(room));
        gridMap.update(player, true);
    }

    public void exitGame() {
        state = 0;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
    public GridMap getGridMap() {
        return gridMap;
    }
    public Player getPlayer() {
        return player;
    }
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public int getState() {
        return state;
    }
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setGridMap(GridMap gridMap) {
        this.gridMap = gridMap;
    }
}
