package game.stuff.item.potions;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

public class EmptyBottle extends AbstractItem {

    public EmptyBottle() {
        super("Empty Bottle", ItemType.EMPTY_BOTTLE);
        setDescription("An useless empty bottle");
    }

}
