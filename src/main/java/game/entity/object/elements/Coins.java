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

    private final int value;

    /**
     * Create a coin entity, at a given position, with a value of 1, and the correct sprite.
     * @param position the position of the coins
     **/
    public Coins(Position position) {
        super(position,Colors.YELLOW, Colors.YELLOW,true,true);
        this.value = 1;
        setSprites(" O ", "   ", Colors.YELLOW);
    }

    /**
     * Handle what to do when the doAction is triggered (in this case only when a player walk on it).
     * @param gameState the state of the game at the instant of the doAction.
     */
    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playCoinFx();
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        player.getStats().gainMoney(value);
        gameState.getDescriptor().updateDescriptor(String.format("%s found "+colorize("1", Colors.YELLOW.textApply())+" coin !",player.getName()));
        gridMap.update(this, false);
    }

    /**
     * override the toString with the correct sprite of the correct color.
     * @return the colorized sprite.
     */
    @Override
    public String toString() {
        return colorize(" O ", Colors.YELLOW.textApply());
    }

    public int getValue() {
        return value;
    }
}
