package stuff.item.potions;

import entity.living.player.Player;
import gameElement.GameState;
import stuff.item.AbstractItem;
import stuff.item.ItemType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractItem {

    public XpBottle() {
        super("Xp bottle", ItemType.XP_BOTTLE);
    }

    public boolean useItem(GameState gameState){
        Player player = gameState.getPlayer();
        int xpAmount = 20+(5*player.getStats().getLevel());
        player.getPlayerStats().grantXP(xpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a XP bottle and gained %s xp",player.getName(),colorize(Integer.toString(xpAmount), Colors.GREEN.textApply())));
        return true;
    }
}
