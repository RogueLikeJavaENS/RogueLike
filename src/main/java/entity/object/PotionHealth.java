package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import items.potion.Potion;
import items.potion.PotionType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class manage the usePotion method of the Health Potion, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class PotionHealth extends AbstractObjectPotion {
    public PotionHealth(Position position) {
        super(position,"( )", Colors.RED, "Health Potion",PotionType.HEALTH);
    }


}
