package stuff.item;

import gameElement.GameState;
import stuff.AbstractStuff;

public class AbstractItem extends AbstractStuff implements Item {

    private final ItemType itemType;

    public AbstractItem(String name, ItemType itemType) {
        super(name, true, false);
        this.itemType = itemType;
    }

    @Override
    public boolean useItem(GameState gameState) {
        return false;
    }

    @Override
    public ItemType getType() {
        return itemType;
    }
}
