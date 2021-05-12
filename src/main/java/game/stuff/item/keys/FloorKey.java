package game.stuff.item.keys;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

public class FloorKey extends AbstractItem {

    public FloorKey() {
        super("Floor Key", ItemType.FLOORKEY);
        setDescription("Opens the stair's room.");
    }
}
