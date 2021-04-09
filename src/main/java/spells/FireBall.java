package spells;

import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class FireBall extends AbstractSpell {

    public FireBall() {
        super(colorize("Fire Ball", Colors.RED.textApply()),
                1.2, 3,
                colorize("o", Colors.RED.textApply()));
    }
}
