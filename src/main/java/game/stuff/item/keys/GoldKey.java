package game.stuff.item.keys;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

public class GoldKey extends AbstractItem {

    public GoldKey() {
        super("Golden Key", ItemType.GOLD_KEY);
        setDescription("Opens a golden chest");
    }
}
