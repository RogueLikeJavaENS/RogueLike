package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This spell improve the warrior defense of armorBonus for the duration of numberTurn
 * The bonus is managed in playerStats and the gameloop.
 * @author luca
 */

public class IronSkin extends AbstractSpell {
    public IronSkin() {
        super("IronSkin",
                0,
                0,
                new Range(),
                10,
                false,
                0,
                (gameState -> {
                    int numberTurn = 3;
                    int level = gameState.getPlayer().getPlayerStats().getLevel();
                    int armorBonus = 4+level;
                    gameState.getPlayer().getPlayerStats().setBonusArmorTemporary(armorBonus, numberTurn);
                    gameState.getDescriptor().updateDescriptor(
                    String.format("%s used "+
                            colorize("IronSkin", Attribute.BOLD(), Colors.DEEP_GREY.textApply())+
                            " and improved his armor by" +
                            colorize(String.format(" %d ",armorBonus), Attribute.BOLD(), Colors.DEEP_GREY.textApply()) +
                            "DEF.", gameState.getPlayer().getName()));
                }));
    }
}
