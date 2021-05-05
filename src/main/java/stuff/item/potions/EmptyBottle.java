package stuff.item.potions;

import stuff.item.AbstractItem;
import stuff.item.ItemType;

public class EmptyBottle extends AbstractItem {

    public EmptyBottle() {
        super("Empty Bottle", ItemType.EMPTY_BOTTLE);
        setDescription("An useless empty bottle");
    }

}
