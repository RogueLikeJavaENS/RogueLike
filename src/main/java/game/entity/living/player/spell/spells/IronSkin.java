package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
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
        super(colorize("Iron Skin", Attribute.BOLD(), Colors.MAGENTA.textApply()),
                false,
                0,
                1,
                (gameState -> {
                    int numberTurn = 5;
                    int level = gameState.getPlayer().getPlayerStats().getLevel();
                    int armorBonus = 8+level;
                    gameState.getPlayer().getPlayerStats().setBonusArmorTemporary(armorBonus, numberTurn);
                    gameState.getDescriptor().updateDescriptor(
                    String.format("%s used "+
                            colorize("IronSkin", Attribute.BOLD(), Colors.DEEP_GREY.textApply())+
                            " and improved his armor by" +
                            colorize(String.format(" %d ",armorBonus), Attribute.BOLD(), Colors.DEEP_GREY.textApply()) +
                            "DEF.", gameState.getPlayer().getName()));
                    return true;
                })
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }
}
