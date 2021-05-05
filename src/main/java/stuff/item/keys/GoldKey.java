package stuff.item.keys;

import stuff.item.AbstractItem;
import stuff.item.ItemType;

public class GoldKey extends AbstractItem {

    public GoldKey() {
        super("Golden Key", ItemType.GOLD_KEY);
        setDescription("Opens a golden chest");
    }
}
