package stuff.item;

import gameElement.GameState;

public interface Item {
    boolean useItem(GameState gameState);
    ItemType getType();
}
