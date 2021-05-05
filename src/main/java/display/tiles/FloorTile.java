package display.tiles;

import display.AbstractTile;
import display.Tile;
import utils.Colors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class FloorTile extends AbstractTile {

    public FloorTile() {
        super(colorize(". .", Colors.GREY.textApply()), true,true);
    }

    public  Tile getTileFromId(int id){
        return new FloorTile();
    }
}
