package game.entity.living.player.spell;

import utils.Position;

public class Range {
    private Position topLeftCorner;
    private Position bottomRightCorner;

    public Range() {
        topLeftCorner = null;
        bottomRightCorner = null;
    }

    public Position getTopLeftCorner() {
        return topLeftCorner;
    }

    public Position getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(Position bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    public void setTopLeftCorner(Position topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }
}