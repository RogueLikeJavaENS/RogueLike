package entity.object;

import gameElement.GameState;
import items.potion.PotionType;
import utils.Colors;
import utils.Position;

public class EmptyBottle extends AbstractObjectPotion {

    public EmptyBottle(Position position) {
        super(position,"( )", Colors.GREY,  "Empty Bottle", PotionType.EMPTY);
    }

}
