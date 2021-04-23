package entity.object.potions;

import stuff.item.ItemType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottleEntity extends AbstractPotionEntity {

    public XpBottleEntity(Position position) {
        super(position,"( )", Colors.GREEN, "XP Potion", ItemType.XP_BOTTLE);
    }

}
