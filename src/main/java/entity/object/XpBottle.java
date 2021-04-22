package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import items.potion.PotionType;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractObjectPotion {

    public XpBottle(Position position) {
        super(position,"( )", Colors.GREEN, "XP Potion", PotionType.XP);
    }


}
