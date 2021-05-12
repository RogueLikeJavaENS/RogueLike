package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Simple Fire Ball that hits the first enemies it encounters within a 3 cases range in front of the player.
 */

public class FireBall extends AbstractSpell {

    public FireBall() {
        super(colorize("Fire Ball", Colors.RED.textApply()),
                1.6,
                16,
                new Range(),
                15,
                true,
                3,
                null
        );
    }

    @Override
    public boolean isZoning() {
        return true;
    }
}
