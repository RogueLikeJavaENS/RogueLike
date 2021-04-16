package spells;

import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * A basic AOE attack that deals damage to any monster around the player.
 */

public class FireAura extends AbstractSpell {

    public FireAura() {
        super(colorize("Fire Aura", Colors.RED.textApply()),
                1.4,
                new Range(),
                5
        );
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        range.setTopLeftCorner(new Position(
                entityPos.getAbs()-1, entityPos.getOrd()-1
        ));
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        range.setBottomRightCorner(new Position(
                entityPos.getAbs()+1, entityPos.getOrd()+1
        ));
    }
}
