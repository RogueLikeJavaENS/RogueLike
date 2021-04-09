package gameElement;

import display.GridMap;
import entity.Entity;
import entity.living.LivingEntity;
import entity.living.Player;
import utils.State;

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
    private State state;
    private Room currentRoom;
    private final Player player;
    private final Dungeon dungeon;
    private GridMap gridMap;
    private Fighting fighting;

    public GameState(Player player, Dungeon dungeon) {
        this.dungeon = dungeon;
        this.player = player;
        this.currentRoom = dungeon.getRoomList().get(0);
        this.gridMap = dungeon.getGridMap(currentRoom);
        player.setPosition(currentRoom.getCenter());
        state = State.NORMAL;
        gridMap.update(player, true);

    }

    public void updateChangingRoom(Room room) {
        gridMap.update(player, false);
        setCurrentRoom(room);
        setGridMap(dungeon.getGridMap(room));
        gridMap.update(player, true);
        isFighting();
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
            boolean accessibilityEntity = true;
            List<Entity> entitiesAt = gridMap.getEntitiesAt(abs + x, ord + y);
            for (Entity entity : entitiesAt){
                if (!entity.getIsAccessible()){
                    accessibilityEntity = false;
                    break;
                }
            }
            if (accessibilityEntity){
                player.getPosition().updatePos(x, y);
                acted = true;
            }
        }
        isFighting();
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
        isFighting();
    }

    public void isFighting() {
        List<LivingEntity> monsters = getGridMap().getMonsters();
        if (monsters.size() > 0) {
            state = State.FIGHT;
            initFight(monsters);
        }
        else {
            state = State.NORMAL;
        }
    }

    public void initFight(List<LivingEntity> monsters) {
        List<LivingEntity> fightList = new ArrayList<>(monsters);
        fightList.add(player);
        fighting = new Fighting(fightList);
    }

    public void exitGame() {
        state = State.END;
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
    public State getState() {
        return state;
    }
    public Fighting getFighting() {
        return fighting;
    }
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setGridMap(GridMap gridMap) {
        this.gridMap = gridMap;
    }
}