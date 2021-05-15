package game.stuff.item;

import game.elements.GameState;
import game.stuff.AbstractStuff;

public class AbstractItem extends AbstractStuff implements Item {

    private final ItemType itemType;

    public AbstractItem(String name, ItemType itemType) {
        super(name, true, false);
        this.itemType = itemType;
    }

    @Override
    public boolean useItem(GameState gameState) {
        return true;
    }

    @Override
    public ItemType getType() {
        return itemType;
    }
}
