package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Heals the Player.
 */
public class Heal extends AbstractSpell {

    public Heal() {
        super(colorize("Heal", Attribute.BOLD(), Colors.GREEN.textApply()),
                false,
                0,
                4,
                (gameState -> {
                    int level = gameState.getPlayer().getPlayerStats().getLevel();
                    int recover = 7+2*level;
                    gameState.getPlayer().getPlayerStats().recoverHp(recover);
                    gameState.getDescriptor().updateDescriptor(
                            String.format(
                                    "%s used "+
                                            colorize("Heal", Attribute.BOLD(), Colors.GREEN.textApply())+
                                            " and recovered" +
                                            colorize(String.format(" %d ", recover), Attribute.BOLD(), Colors.GREEN.textApply()) +
                                            "HP.", gameState.getPlayer().getName()));
                    return true;
                }));
        GameRule.setSpellStats(this, InGameClasses.MAGE);
    }

}
