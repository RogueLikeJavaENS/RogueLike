package game.tile.tiles;

import game.tile.AbstractTile;
import game.tile.Tile;
import utils.Colors;


import static com.diogonunes.jcolor.Ansi.colorize;

public class WallTile extends AbstractTile {
    public WallTile() { super(colorize("###", Colors.DARK_GREY.textApply()), false,false); }
    @Override
    public Tile getTileFromId(int id){
        return new WallTile();
    }
}
