package game.stuff.item;

import game.element.GameState;
import game.stuff.Stuff;

public interface Item extends Stuff {
    boolean useItem(GameState gameState);
    ItemType getType();
}
