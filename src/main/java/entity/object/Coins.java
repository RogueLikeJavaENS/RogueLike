package entity.object;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * this class manage the coin object, and what it does when picked up by a player.
 * @author luca
 */

public class Coins extends ObjectEntity {

    public int getValue() {
        return value;
    }

    private final int value;

    public Coins(Position position) {
        super(position,Colors.YELLOW, true);
        this.value = 1;
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize(" O ", Colors.YELLOW.textApply()));
        sprites.add("   ");
        setSprites(sprites);
    }

    @Override
    public void doAction(GameState gameState) {
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        player.getStats().gainMoney(value);
        gridMap.update(this, false);
    }

    @Override
    public String toString() {
        return colorize(" O ", Colors.YELLOW.textApply());
    }
}
