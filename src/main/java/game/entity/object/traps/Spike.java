package game.entity.object.traps;

import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Spikes are a small trap dealing damage to the Player if they step/stay on it.
 */
public class Spike extends ObjectEntity {

    public Spike(Position position) {
        super(position, Colors.DARK_GREY, true, false);
        setSprites(colorize("^^^",Colors.GREY.textApply()),colorize("^^^",Colors.GREY.textApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playStabsFX();
        int damage = gameState.getDungeon().getFloor()*2;
        Player player = gameState.getPlayer();
        damage = player.getPlayerStats().sufferDamage(damage);
        gameState.getDescriptor().updateDescriptor(String.format("%s walked on spikes and lost %d HP.",player.getName(),damage));
    }

    @Override
    public boolean isTrap() {
        return true;
    }
}
