package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class manage the usePotion method of the elixir, returning a boolean, to allow us to check
 * if the player turn need to be consumed.
 *
 * @author luca
 */

public class Elixir extends AbstractPotion {

    public Elixir(Position position) {
        super(position, "( )", Colors.BLUE, 1, "Mana Potion");
    }

    public boolean usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int mpAmount = 10+(5*player.getStats().getLevel());
        player.getPlayerStats().recoverMp(mpAmount);
        if (player.getPlayerStats().getManaPointTotal()!=player.getPlayerStats().getManaPointActual()) {
            gameState.getDescriptor().updateDescriptor(String.format("%s used an Elixir of Mana and gained %s mana", player.getName(), colorize(Integer.toString(mpAmount), Colors.BLUE.textApply())));
            player.consummePotion(this);
            return true;
        }
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s is already full of energy, he doesn't need to drink his potion", player.getName()));
            return false;
        }
    }
}
