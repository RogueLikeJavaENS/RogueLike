package stuff.item;

import gameElement.GameState;
import stuff.Stuff;

public interface Item extends Stuff {
    boolean useItem(GameState gameState);
    ItemType getType();
}
