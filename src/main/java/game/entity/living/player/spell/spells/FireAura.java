package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
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
                15,
                new Range(),
                10
        );
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int availableRange = 1;
        int topLeftAbs = entityPos.getAbs()-availableRange;
        int topLeftOrd = entityPos.getOrd()-availableRange;

        if (entityPos.getAbs() < availableRange) {
            topLeftAbs = entityPos.getAbs();
        }
        if (entityPos.getOrd() < availableRange) {
            topLeftOrd = entityPos.getOrd();
        }
        range.setTopLeftCorner(new Position(
                topLeftAbs, topLeftOrd
        ));
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        range.setBottomRightCorner(new Position(
                entityPos.getAbs()+1, entityPos.getOrd()+1
        ));
    }
}
