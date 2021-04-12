package spells;

import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * A basic AOE attack that deals damage to any monster around the player.
 */

public class FireAura extends AbstractSpell {

    public FireAura() {
        super(colorize("Fire Aura", Colors.RED.textApply()),
                1.4, 1, 5,
                colorize("u", Colors.RED.textApply()));
    }

}
