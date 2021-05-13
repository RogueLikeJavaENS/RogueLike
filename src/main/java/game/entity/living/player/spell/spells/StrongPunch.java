package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
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
                1.2,
                15,
                new Range(),
                5,
                true,
                1,
                null);
    }
}
