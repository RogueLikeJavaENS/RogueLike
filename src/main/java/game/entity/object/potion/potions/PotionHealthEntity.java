package game.entity.object.potion.potions;

import game.entity.object.potion.AbstractPotionEntity;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class manage the usePotion method of the Health Potion, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class PotionHealthEntity extends AbstractPotionEntity {
    public PotionHealthEntity(Position position) {
        super(position,"( )", Colors.RED, "Health Potion", ItemType.HEALTH_POTION);
    }

}
