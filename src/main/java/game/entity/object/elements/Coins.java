package game.entity.object.elements;

import display.GridMap;
import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

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
        super(position,Colors.YELLOW, true,true);
        this.value = 1;
        setSprites(" O ", "   ", Colors.YELLOW);
    }

    @Override
    public void doAction(GameState gameState) {
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        player.getStats().gainMoney(value);
        gameState.getDescriptor().updateDescriptor(String.format("%s found "+colorize("1", Colors.YELLOW.textApply())+" coin !",player.getName()));
        gridMap.update(this, false);
    }

    @Override
    public String toString() {
        return colorize(" O ", Colors.YELLOW.textApply());
    }
}
