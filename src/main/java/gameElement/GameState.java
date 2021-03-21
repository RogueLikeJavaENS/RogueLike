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
    private List<Entity> entities;

    public GameState(Player player, Dungeon dungeon, GridMap gridMap) {
        this.dungeon = dungeon;
        this.player = player;
        this.gridMap = gridMap;
        this.currentRoom = dungeon.getRoomList().get(0);
        entities = currentRoom.getEntities();
        entities.add(player);
        player.setPosition(currentRoom.getCenter());
        state = 1;
        gridMap.update(entities);
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
            gridMap.removeEntity(player);
            player.getPosition().updatePos(x, y);
            gridMap.update(entities);
            acted = true;
        }
        return acted;
    }

    public void isOnEntity() {
        for(Entity entity : entities) {
            if (player.getPosition().equals(entity.getPosition()) && entity != player) {
                entity.doAction(this);
            }
        }
    }

    public void updateChangingRoom(Room room) {
        setCurrentRoom(room);
        setGridMap(new GridMap(room, player));
        fillEntities();

    }

    public void fillEntities() {
        entities = currentRoom.getEntities();
        entities.add(player);
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
    public List<Entity> getEntities() {
        return entities;
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
        setEntities(currentRoom.getEntities());
    }

    public void setGridMap(GridMap gridMap) {
        this.gridMap = gridMap;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
