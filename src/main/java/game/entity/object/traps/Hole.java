package game.entity.object.traps;

import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * A simple hole in the map. Watch your step!
 */
public class Hole extends ObjectEntity {

    public Hole(Position position) {
        super(position, Colors.BLACK,  Colors.BLACK,true, false);
        setSprites(colorize("   ", Colors.SOFT_DARK.bgApply()),colorize("   ", Colors.SOFT_DARK.bgApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playFallFX();
        int damage = gameState.getDungeon().getFloor()*8;
        Player player = gameState.getPlayer();
        damage = player.getPlayerStats().sufferDamageIgnoringArmor(damage);
        player.setPosition(gameState.getCurrentRoom().getCenter());
        gameState.updateChangingRoom(gameState.getDungeon().getRoom(0));
        gameState.getDescriptor().updateDescriptor(String.format(
                "%s fell in a hole which lead him at the begin of the floor, and lost %d HP.",player.getName(), damage));
    }

    @Override
    public boolean isTrap() {
        return true;
    }
}
