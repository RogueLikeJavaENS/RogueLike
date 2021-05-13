package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import game.entity.living.player.spell.SpecialEffect;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * this class implement the Firebolt Spell by extending AbstractSpell
 * its a 5 range attack stopping at the first mob encountered, and do a bit more damage than
 * for example a Fireball.
 */
public class FireBolt extends AbstractSpell {
    public FireBolt() {
        super(colorize("Fire Bolt", Attribute.BOLD(), Colors.RED.textApply()),
                1.5,
                60,
                new Range(),
                40,
                true,
                5,
                null);
    }
}
