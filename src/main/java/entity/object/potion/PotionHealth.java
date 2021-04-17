package entity.object.potion;

import entity.living.player.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class PotionHealth extends AbstractPotion{

    public PotionHealth(Position position) {
        super(position,"( )", Colors.RED, 0, "Health Potion");
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int hpAmount = 5*player.getStats().getLevel();
        player.getPlayerStats().recoverHp(hpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a Health Potion and gained %s HP",player.getName(),colorize(Integer.toString(hpAmount),Colors.RED.textApply())));
        player.usePotion(this);
    }
}
