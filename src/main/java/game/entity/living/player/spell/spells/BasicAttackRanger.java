package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Basic Ranger's attack. The Ranger shoots a simple arrow which hits the first ennemy it meets.
 * It has a long Range but lower damages.
 */
public class BasicAttackRanger extends AbstractSpell {
    public BasicAttackRanger() {
        super(colorize("Attack", Attribute.BOLD()),
                1.0,
                11,
                new Range(),
                0,
                true,
                3,
                null
        );
    }
}
