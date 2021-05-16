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
 * A strong attack
 */

public class StrongPunch extends AbstractSpell {
    public StrongPunch() {
        super(colorize("Strong Punch", Attribute.BOLD(), Colors.RED.textApply()),
                true,
                1,
                5,
                null
        );
        GameRule.setSpellStats(this, InGameClasses.WARRIOR);
    }
}
