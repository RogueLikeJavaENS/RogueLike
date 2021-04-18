package entity.object.potion;

import entity.living.player.Player;
import gameElement.GameState;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractPotion{

    public XpBottle(Position position) {
        super(position,"( )", Colors.GREEN, 2, "XP Potion");
    }

    public void usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int xpAmount = 20+(5*player.getStats().getLevel());
        player.getPlayerStats().grantXP(xpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a XP bottle and gained %s xp",player.getName(),colorize(Integer.toString(xpAmount),Colors.GREEN.textApply())));
        player.usePotion(this);
    }
}
