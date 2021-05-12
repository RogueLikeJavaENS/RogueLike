package game.entity.object.potion.potions;

import game.entity.object.potion.AbstractPotionEntity;
import game.stuff.item.ItemType;
import utils.Colors;
import utils.Position;

/**
 * This class manage the usePotion method of the elixir, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class ElixirEntity extends AbstractPotionEntity {

    public ElixirEntity(Position position) {
        super(position, "( )", Colors.BLUE,  "Elixir", ItemType.ELIXIR);
    }

}
