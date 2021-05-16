package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameRule;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Warrior throw his axe to touch an enemy.
 */
public class ThrowAxe extends AbstractSpell {
    public ThrowAxe() {
        super(colorize("Throw Axe", Attribute.BOLD(), Colors.RED.textApply()),
                true,
                3,
                5,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }
}
