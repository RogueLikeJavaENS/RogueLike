package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import items.potion.PotionType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class manage the usePotion method of the elixir, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class Elixir extends AbstractObjectPotion {

    public Elixir(Position position) {
        super(position, "( )", Colors.BLUE,  "Mana Potion", PotionType.ELIXIR);
    }


}
