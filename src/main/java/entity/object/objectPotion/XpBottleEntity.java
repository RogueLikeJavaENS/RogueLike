package entity.object.objectPotion;

import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottleEntity extends AbstractPotionEntity {

    public XpBottleEntity(Position position) {
        super(position,"( )", Colors.GREEN, "XP Potion", 2);
    }


}
