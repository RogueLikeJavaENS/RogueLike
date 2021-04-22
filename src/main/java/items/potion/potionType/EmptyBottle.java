package items.potion.potionType;

import gameElement.GameState;
import items.potion.AbstractPotion;
import items.potion.Potion;
import items.potion.PotionType;

public class EmptyBottle extends AbstractPotion implements Potion {

    public EmptyBottle() {
        super("Empty Bottle", PotionType.EMPTY);
    }

    public boolean usePotion(GameState gameState){
        return false;
    }
}
