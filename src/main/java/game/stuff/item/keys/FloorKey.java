package game.stuff.item.keys;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

/**
 * This class describe a FoorKey.
 * This key is use to open the door of the room which contains the stair used to go to the next floor.
 */
public class FloorKey extends AbstractItem {

    /**
     * Creat a FloorKey
     */
    public FloorKey() {
        super("Floor Key", ItemType.FLOORKEY);
        setDescription("Opens the stair's room.");
    }
}
