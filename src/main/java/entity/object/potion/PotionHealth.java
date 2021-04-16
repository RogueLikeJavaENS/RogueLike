package entity.object.potion;

import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class PotionHealth extends AbstractPotion{

    public PotionHealth(Position position) {
        super(position, colorize("( )", Colors.RED.textApply()), 0);
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        player.getPlayerStats().recoverHp(5*player.getStats().getLevel());
        player.usePotion(this);
    }
}
