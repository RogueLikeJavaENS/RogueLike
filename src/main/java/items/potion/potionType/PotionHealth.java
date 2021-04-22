package items.potion.potionType;

import entity.living.player.Player;
import gameElement.GameState;
import items.potion.AbstractPotion;
import items.potion.Potion;
import items.potion.PotionType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class PotionHealth extends AbstractPotion implements Potion {

    public PotionHealth() {
        super("Health Potion", PotionType.HEALTH);
    }

    public boolean usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int hpAmount = 10+(5*player.getStats().getLevel());
        player.getPlayerStats().recoverHp(hpAmount);
        if (player.getPlayerStats().getLifePointTotal()!=player.getPlayerStats().getLifePointActual()) {
            gameState.getDescriptor().updateDescriptor(String.format("%s used a Health Potion and gained %s HP", player.getName(), colorize(Integer.toString(hpAmount), Colors.RED.textApply())));
            player.consummePotion(this);
            return true;
        }
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s is already fully healed, he doesn't need to drink his potion", player.getName()));
            return false;
        }
    }
}
