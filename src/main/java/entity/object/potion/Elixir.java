package entity.object.potion;

import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Elixir extends AbstractPotion{

    public Elixir(Position position) {
        super(position, "( )", Colors.BLUE, 1);
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        player.getPlayerStats().recoverMp(5*player.getStats().getLevel());
        player.usePotion(this);
    }
}
