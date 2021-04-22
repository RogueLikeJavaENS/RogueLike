package items.potion.potionType;

import entity.living.player.Player;
import entity.object.ObjectPotion;
import gameElement.GameState;
import items.potion.AbstractPotion;
import items.potion.Potion;
import items.potion.PotionType;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class XpBottle extends AbstractPotion implements Potion {

    public XpBottle() {
        super("Xp bottle", PotionType.XP);
    }

    public boolean usePotion(GameState gameState){
        Player player = gameState.getPlayer();
        int xpAmount = 20+(5*player.getStats().getLevel());
        player.getPlayerStats().grantXP(xpAmount);
        gameState.getDescriptor().updateDescriptor(String.format("%s used a XP bottle and gained %s xp",player.getName(),colorize(Integer.toString(xpAmount), Colors.GREEN.textApply())));
        player.consummePotion(this);
        return true;
    }
}
