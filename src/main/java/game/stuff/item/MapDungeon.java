package game.stuff.item;

import game.elements.GameState;
import game.elements.Room;

import java.util.List;

/**
 * This class describe the item "Map of the floor"
 * This item permit to see all the map without visiting all the room.
 *
 */

public class MapDungeon extends AbstractItem{

    /**
     * Create a Map of the floor
     */
    public MapDungeon() {
        super("Map of the Floor", ItemType.DUNGEON_MAP);
        setDescription("Discover all the floor in the map. One Use.");
    }

    /**
     * The action of the the object.
     * Set the variable "wasVisited" at true which permit to update de miniMap.
     *
     * @param gameState the gameState in order to get all the room of the dungeon
     * @return true to say it was executed
     */
    @Override
    public boolean useItem(GameState gameState) {
        for (Room room : gameState.getDungeon().getRoomList()) {
            room.setWasVisited(true);
        }
        return true;
    }

    /**
     * Make the Map Dungeon, an Item
     * @return true
     */
    @Override
    public boolean isUsable() {
        return true;
    }
}
