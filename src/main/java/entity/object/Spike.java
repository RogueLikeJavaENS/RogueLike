package entity.object;

import entity.living.player.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Spike extends ObjectEntity{

    public Spike(Position position) {
        super(position, Colors.DARK_GREY, true, false);
        setSprites(colorize("^^^",Colors.GREY.textApply()),colorize("^^^",Colors.GREY.textApply()));
    }



    @Override
    public void doAction(GameState gameState) {
        int damage = gameState.getDungeon().getFloor()*2;
        Player player = gameState.getPlayer();
        damage = player.getPlayerStats().sufferDamage(damage);
        gameState.getDescriptor().updateDescriptor(String.format("%s walked on spikes and lost %d HP.",player.getName(),damage));
    }
}
