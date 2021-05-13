package game.stuff.item.potions;

import game.entity.living.player.Player;
import game.elements.GameState;
import game.stuff.item.AbstractItem;
import game.stuff.item.ItemType;
import utils.Colors;

import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * class handling the xpBottle items
 * @author luca
 */
public class XpBottle extends AbstractItem {

    /**
     * create an XpBottle, using the AbstractItem constructor &
     * just setting the correct descriptor.
     */
    public XpBottle() {
        super("Xp bottle", ItemType.XP_BOTTLE);
        setDescription("Grants Experiences points to the player.");
    }

    /**
     * useItem handle what to do when you use an XpBottle (gain x amount of xp).
     * @param gameState state of the game when called.
     * @return a boolean checking if they're was a possibility to consume the item and
     * use is effect.
     */
    @Override
    public boolean useItem(GameState gameState){
        Player player = gameState.getPlayer();
        int xpAmount = 20+(5*player.getStats().getLevel());
        List<String> descriptionLevelUp = player.getPlayerStats().grantXP(xpAmount,gameState);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a XP bottle and gained %s xp",player.getName(),colorize(Integer.toString(xpAmount), Colors.GREEN.textApply())));
        if (descriptionLevelUp.size() != 0){
            for (String str : descriptionLevelUp){
                gameState.getDescriptor().updateDescriptor(String.format("%s"+str,gameState.getPlayer().getName()));
            }
        }
        return true;
    }
}
