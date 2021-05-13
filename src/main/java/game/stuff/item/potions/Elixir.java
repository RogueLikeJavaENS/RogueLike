package game.stuff.item.potions;

import game.entity.living.player.Player;
import game.elements.GameState;
import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * class handling the elixir items
 * @author luca
 */
public class Elixir extends AbstractItem {

    /**
     * create an elixir, using the AbstractItem constructor &
     * just setting the correct descriptor.
     */
    public Elixir(){
        super("Elixir", ItemType.ELIXIR);
        setDescription("Recovers Mana points to the player.");
    }

    /**
     * useItem handle what to do when you use an Elixir (recharge your mp).
     * @param gameState state of the game when called.
     * @return a boolean checking if they're was a possibility to consume the item and
     * use is effect.
     */
    @Override
    public boolean useItem(GameState gameState) {
        Player player = gameState.getPlayer();
        int mpAmount = 10+(5*player.getStats().getLevel());
        if (player.getPlayerStats().getManaPointNatural()!=player.getPlayerStats().getManaPointActual()) {
            gameState.getDescriptor().updateDescriptor(String.format("%s used an Elixir of Mana and gained %s mana",
                    player.getName(),
                    colorize(Integer.toString(Math.min(mpAmount, player.getPlayerStats().getManaPointTotal()-player.getPlayerStats().getManaPointActual())),
                            Colors.BLUE.textApply())));
            player.getPlayerStats().recoverMp(mpAmount);
            return true;
        }
        else {
            gameState.getDescriptor().updateDescriptor(String.format("%s is already full of energy, he doesn't need to drink his potion", player.getName()));
            return false;
        }
    }
}
