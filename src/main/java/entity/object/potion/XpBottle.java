package entity.object.potion;

import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractPotion{

    public XpBottle(Position position) {
        super(position,"( )", Colors.GREEN, 2);
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        player.getPlayerStats().grantXP(20+(5*player.getStats().getLevel()));
        player.usePotion(this);
    }
}
