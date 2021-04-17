package entity.object.potion;

import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Elixir extends AbstractPotion{

    public Elixir(Position position) {
        super(position, "( )", Colors.BLUE, 1, "Mana Potion");
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int mpAmount = 5*player.getStats().getLevel();
        player.getPlayerStats().recoverMp(mpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used an Elixir of Mana and gained %s mana",player.getName(),colorize(Integer.toString(mpAmount),Colors.BLUE.textApply())));
        player.usePotion(this);
    }
}
