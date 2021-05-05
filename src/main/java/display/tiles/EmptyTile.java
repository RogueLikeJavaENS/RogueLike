package display.tiles;

import display.AbstractTile;
import display.Tile;

public class EmptyTile extends AbstractTile {
    public EmptyTile() {
        super("   ", false,false);
    }
    public Tile getTileFromId(int id){
        return null;
    }
}

