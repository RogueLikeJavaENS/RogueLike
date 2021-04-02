package display.tiles;

import display.AbstractTile;
import utils.Colors;


import static com.diogonunes.jcolor.Ansi.colorize;

public class WallTile extends AbstractTile {
    public WallTile() { super(colorize("##", Colors.DARK_GREY.textApply()), false); }
}
