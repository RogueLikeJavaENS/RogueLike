package stuff.item.potions;

import entity.living.player.Player;
import gameElement.GameState;
import stuff.item.AbstractItem;
import stuff.item.ItemType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Elixir extends AbstractItem {

    public Elixir(){
        super("Elixir", ItemType.ELIXIR);
    }

    public boolean useItem(GameState gameState) {
        Player player = gameState.getPlayer();
        int mpAmount = 10+(5*player.getStats().getLevel());
        if (player.getPlayerStats().getManaPointNatural()!=player.getPlayerStats().getManaPointActual()) {
            player.getPlayerStats().recoverMp(mpAmount);
            gameState.getDescriptor().updateDescriptor(String.format("%s used an Elixir of Mana and gained %s mana", player.getName(), colorize(Integer.toString(mpAmount), Colors.BLUE.textApply())));
            return true;
        }
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s is already full of energy, he doesn't need to drink his potion", player.getName()));
            return false;
        }
    }
}
