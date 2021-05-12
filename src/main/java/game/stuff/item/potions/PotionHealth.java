package game.stuff.item.potions;

import game.entity.living.player.Player;
import game.elements.GameState;
import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class PotionHealth extends AbstractItem {

    public PotionHealth() {
        super("Health Potion", ItemType.HEALTH_POTION);
        setDescription("Heals the player.");
    }

    public boolean useItem(GameState gameState){
        Player player = gameState.getPlayer();
        int hpAmount = 10+(5*player.getStats().getLevel());
        if (player.getPlayerStats().getLifePointNatural()!=player.getPlayerStats().getLifePointActual()) {
            gameState.getDescriptor().updateDescriptor(String.format("%s used a Health Potion and gained %s HP", player.getName(),
                                    colorize(Integer.toString(Math.min(hpAmount,player.getPlayerStats().getLifePointTotal()-player.getPlayerStats().getLifePointActual())),
                            Colors.RED.textApply())));
            player.getPlayerStats().recoverHp(hpAmount);
            return true;
        }
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s is already fully healed, he doesn't need to drink his potion", player.getName()));
            return false;
        }
    }
}
