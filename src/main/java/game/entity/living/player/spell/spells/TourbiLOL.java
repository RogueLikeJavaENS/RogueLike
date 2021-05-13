package game.entity.living.player.spell.spells;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Warrior is turing on himself, dealing damage around him.
 */
public class TourbiLOL extends AbstractSpell {
    public TourbiLOL() {
        super(colorize("TourbiLOL", Attribute.BOLD(), Colors.PINK.textApply()),
                1.2,
                15,
                new Range(),
                10,
                true,
                1,
                null);
    }

    public boolean isZoning() {
        return true;
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
