package game.entity.living.player.spell;

import utils.Direction;
import utils.Position;

public class SpellUtils {
    public static void setTopLeftCornerUlti(Range range, Position entityPos, Direction direction){
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
        range.setTopLeftCorner(new Position(rangeAbs, rangeOrd));
    }

    public static void setBottomRightCornerUlti(Range range, Position entityPos, Direction direction){
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
        range.setBottomRightCorner(new Position(rangeAbs, rangeOrd));
    }

    public static void setTopLeftCornerArround(Range range, Position entityPos, Direction direction) {
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

    public static void setBottomRightCornerArround(Range range, Position entityPos, Direction direction){
        range.setBottomRightCorner(new Position(
                entityPos.getAbs()+1, entityPos.getOrd()+1
        ));
    }
}
