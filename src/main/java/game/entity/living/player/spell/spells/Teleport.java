package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Colors;
import utils.Direction;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Teleport extends AbstractSpell {
    public Teleport() {
        super(colorize("Teleport", Colors.BLUE.textApply()),
                0,
                0,
                new Range(),
                15,
                false,
                3,
                null
        );
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int availableRange = 4;
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
