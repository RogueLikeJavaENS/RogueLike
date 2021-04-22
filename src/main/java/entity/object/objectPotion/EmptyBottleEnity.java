package entity.object.objectPotion;

import utils.Colors;
import utils.Position;

public class EmptyBottleEnity extends AbstractPotionEntity {

    public EmptyBottleEnity(Position position) {
        super(position,"( )", Colors.GREY,  "Empty Bottle", -1);
    }

}
