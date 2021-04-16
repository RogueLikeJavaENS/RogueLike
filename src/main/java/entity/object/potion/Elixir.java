package entity.object.potion;

import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Elixir extends AbstractPotion{

    public Elixir(Position position) {
        super(position, colorize("( )", Colors.BLUE.textApply()), 1);
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        player.getStats().recoverMp(5*player.getStats().getLevel());
        player.usePotion(this);
    }
}
