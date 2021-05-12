package game.entity.living.player.spell.spells;

import game.entity.living.player.spell.AbstractSpell;
import game.entity.living.player.spell.Range;
import utils.Direction;
import utils.Position;

public class Multishot extends AbstractSpell {

    public Multishot() {
        super("Multi-Shot",
                2.0,
                100,
                new Range(),
                50,
                true,
                1,
                null);
    }

    @Override
    public boolean isZoning() {
        return true;
    }

    @Override
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int rangeAbs = entityPos.getAbs();
        int rangeOrd = entityPos.getOrd();
        switch (direction) {
            case NORTH:
                rangeOrd = entityPos.getOrd()-3; //3 parce qu'on veut faire du 3x3
                rangeAbs = entityPos.getAbs()-1;

                if (entityPos.getAbs() < 1) {
                    rangeAbs = entityPos.getAbs();
                }
                if (entityPos.getOrd() < 3) {
                    rangeOrd = entityPos.getOrd() + (3 - entityPos.getOrd());
                }
                break;

            case WEST:
                rangeOrd = entityPos.getOrd()-1;
                rangeAbs = entityPos.getAbs()-3; //3 parce qu'on veut faire du 3x3

                if (entityPos.getAbs() < 3) {
                    rangeAbs = entityPos.getAbs() + (3 - entityPos.getAbs());
                }
                if (entityPos.getOrd() < 1) {
                    rangeOrd = entityPos.getOrd();
                }
                break;

            case SOUTH:
                rangeOrd = entityPos.getOrd()+1;
                rangeAbs = entityPos.getAbs()-1;
                break;

            case EAST:
                rangeOrd = entityPos.getOrd()-1;
                rangeAbs = entityPos.getAbs()+1;
                break;
        }
        range.setTopLeftCorner(new Position(
                rangeAbs, rangeOrd
        ));
    }

    @Override
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        int rangeAbs = entityPos.getAbs();
        int rangeOrd = entityPos.getOrd();
        switch (direction) {
            case NORTH:
                rangeOrd = entityPos.getOrd()-1;
                rangeAbs = entityPos.getAbs()+1;
                break;

            case WEST:
                rangeOrd = entityPos.getOrd()+1;
                rangeAbs = entityPos.getAbs()-1;
                break;

            case SOUTH:
                rangeOrd = entityPos.getOrd()+3; //3 parce qu'on veut faire du 3x3
                rangeAbs = entityPos.getAbs()+1;

                break;

            case EAST:
                rangeOrd = entityPos.getOrd()+1;
                rangeAbs = entityPos.getAbs()+3; //3 parce qu'on veut faire du 3x3

                break;
        }
        range.setBottomRightCorner(new Position(
                rangeAbs, rangeOrd
        ));
    }
}
