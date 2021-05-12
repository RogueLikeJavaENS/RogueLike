package game.stuff.item.potions;

import game.entity.living.player.Player;
import game.elements.GameState;
import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractItem {

    public XpBottle() {
        super("Xp bottle", ItemType.XP_BOTTLE);
        setDescription("Grants Experiences points to the player.");
    }

    public boolean useItem(GameState gameState){
        Player player = gameState.getPlayer();
        int xpAmount = 20+(5*player.getStats().getLevel());
        player.getPlayerStats().grantXP(xpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a XP bottle and gained %s xp",player.getName(),colorize(Integer.toString(xpAmount), Colors.GREEN.textApply())));
        return true;
    }
}
