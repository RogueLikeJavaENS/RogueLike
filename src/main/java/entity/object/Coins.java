package entity.object;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import entity.living.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Coins extends ObjectEntity {

    public int getValue() {
        return value;
    }

    private final int value;

    public Coins(Position position) {
        super(position, true);
        this.value = 1;
    }

    @Override
    public void doAction(GameState gameState) {
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        player.setMoneyCount(player.getMoneyCount()+value);
        gridMap.update(this, false);
    }

    @Override
    public String toString() {
        return colorize("OO", Colors.YELLOW.textApply());
        //return colorize("OO", Attribute.TEXT_COLOR(3));
    }
}
