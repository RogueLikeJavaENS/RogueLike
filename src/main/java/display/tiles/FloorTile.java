package display.tiles;

import display.AbstractTile;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class FloorTile extends AbstractTile {

    public FloorTile() {
        super(colorize("..", Colors.GREY.textApply()), true);
    }
}
