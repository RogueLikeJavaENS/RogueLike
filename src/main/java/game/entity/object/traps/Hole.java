package game.entity.object.traps;

import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Hole extends ObjectEntity {


    public Hole(Position position) {
        super(position, Colors.BLACK, true, false);
        setSprites(colorize("   ", Colors.SOFT_DARK.bgApply()),colorize("   ", Colors.SOFT_DARK.bgApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getPlayer().setPosition(gameState.getCurrentRoom().getCenter());
        gameState.updateChangingRoom(gameState.getDungeon().getRoom(0));
        gameState.getDescriptor().updateDescriptor(String.format("%s fell in a hole which lead him at the begin of the floor.",gameState.getPlayer().getName()));
    }

    @Override
    public boolean isTrap() {
        return true;
    }
}