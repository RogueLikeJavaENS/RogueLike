package game.entity.object.potion.potions;

import game.entity.object.potion.AbstractPotionEntity;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;

public class EmptyBottleEntity extends AbstractPotionEntity {

    public EmptyBottleEntity(Position position) {
        super(position,"( )", Colors.GREY,  "Empty Bottle", ItemType.EMPTY_BOTTLE);
    }

}
