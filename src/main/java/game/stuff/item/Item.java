package game.stuff.item;

import game.elements.GameState;
import game.stuff.Stuff;

public interface Item extends Stuff {
    boolean useItem(GameState gameState);
    ItemType getType();
}
