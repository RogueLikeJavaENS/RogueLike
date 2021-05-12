package game.tile.tiles;

import game.tile.AbstractTile;
import game.tile.Tile;

public class DoorTile extends AbstractTile {
    public DoorTile() {
        super("", true,false);
    }


    public Tile getTileFromId(int id){
        return new DoorTile();
    }
}
