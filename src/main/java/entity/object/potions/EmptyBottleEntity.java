package entity.object.potions;

import stuff.item.ItemType;
import utils.Colors;
import utils.Position;

public class EmptyBottleEntity extends AbstractPotionEntity {

    public EmptyBottleEntity(Position position) {
        super(position,"( )", Colors.GREY,  "Empty Bottle", ItemType.EMPTY_BOTTLE);
    }

}
