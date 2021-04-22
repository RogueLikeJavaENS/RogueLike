package items.object.potionType;

import gameElement.GameState;
import items.potion.Potion;

public class EmptyBottle extends AbstractPotion implements Potion {

    public EmptyBottle() {
        super("Empty Bottle", -1);
    }

    public boolean usePotion(GameState gameState){
        return false;
    }
}
