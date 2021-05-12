package game.tile.tiles;

import game.tile.AbstractTile;
import game.tile.Tile;

public class EmptyTile extends AbstractTile {
    public EmptyTile() {
        super("   ", false,false);
    }
    public Tile getTileFromId(int id){
        return null;
    }
}

