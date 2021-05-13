package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Ranger shoots a powerful arrow with an unlimited Range. The arrow is so strong nothing can stop it.
 */
public class Sniper extends AbstractSpell {
    public Sniper() {
        super(colorize("Sniper", Attribute.BOLD(), Colors.MAGENTA.textApply()),
                1.0,
                12,
                new Range(),
                25,
                true,
                20,
                null
        );
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
