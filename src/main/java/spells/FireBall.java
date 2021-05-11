package spells;

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
                15);
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int availableRange = 3;
        switch (direction) {
            case NORTH:
                if (entityPos.getOrd() < availableRange) {
                    availableRange = entityPos.getOrd();
                }
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd() - availableRange
                ));
                break;
            case WEST:
                if (entityPos.getAbs() < availableRange) {
                    availableRange = entityPos.getAbs();
                }
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs() - availableRange, entityPos.getOrd()
                ));
                break;
            default:
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd()
                ));
        }
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        switch (direction) {
            case EAST:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs() + 3, entityPos.getOrd()
                ));
                break;
            case SOUTH:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd() + 3
                ));
                break;
            default:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd()
                ));
        }
    }
}
