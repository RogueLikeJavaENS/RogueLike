package game.entity.living.player.spell.spells;


import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class BasicAttackWarrior extends AbstractSpell {

    public BasicAttackWarrior() {
        super("Attack",
                1.0,
                12,
                new Range(),
                0,
                true,
                null
        );
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int availableRange = 1;
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
                        entityPos.getAbs() + 1, entityPos.getOrd()
                ));
                break;
            case SOUTH:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd() + 1
                ));
                break;
            default:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd()
                ));
        }
    }
}
