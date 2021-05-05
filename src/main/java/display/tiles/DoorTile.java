package display.tiles;

import display.AbstractTile;
import display.Tile;

public class DoorTile extends AbstractTile {
    public DoorTile() {
        super("", true,false);
    }


    public Tile getTileFromId(int id){
        return new DoorTile();
    }
}
