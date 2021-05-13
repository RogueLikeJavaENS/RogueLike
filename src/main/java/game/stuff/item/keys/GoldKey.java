package game.stuff.item.keys;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

/**
 * This class describe the Gold key.
 * This key is used to open a Golden Chest
 */
public class GoldKey extends AbstractItem {

    /**
     * Create a goldKey
     */
    public GoldKey() {
        super("Golden Key", ItemType.GOLD_KEY);
        setDescription("Opens a golden chest");
    }
}
