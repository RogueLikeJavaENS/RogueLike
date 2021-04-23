package entity.object.potions;

import stuff.item.ItemType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class manage the usePotion method of the elixir, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class ElixirEntity extends AbstractPotionEntity {

    public ElixirEntity(Position position) {
        super(position, "( )", Colors.BLUE,  "Mana Potion", ItemType.ELIXIR);
    }

}
