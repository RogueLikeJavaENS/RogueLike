package items.potion.potionType;

import entity.living.player.Player;
import gameElement.GameState;
import items.potion.AbstractPotion;
import items.potion.Potion;
import items.potion.PotionType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Elixir extends AbstractPotion implements Potion {

    public Elixir(){
        super("Elixir", PotionType.ELIXIR);
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
