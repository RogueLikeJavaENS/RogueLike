package spells;

import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Simple Fire Ball that hits the first enemies it encounters within a 3 cases range in front of the player.
 */

public class FireBall extends AbstractSpell {

    public FireBall() {
        super(colorize("Fire Ball", Colors.RED.textApply()),
                1.2, 3, 3,
                colorize("o", Colors.RED.textApply()));
    }
}
