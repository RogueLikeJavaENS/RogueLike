package game.stuff.item.potions;

import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;

/**
 * class handling the emptyBottle items
 * @author luca
 */
public class EmptyBottle extends AbstractItem {

    /**
     * create an emptyBottle, using the AbstractItem constructor &
     * just setting the correct descriptor.
     */
    public EmptyBottle() {
        super("Empty Bottle", ItemType.EMPTY_BOTTLE);
        setDescription("An useless empty bottle");
    }

}
