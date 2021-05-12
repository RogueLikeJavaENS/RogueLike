package game.tile.tiles;

import game.tile.AbstractTile;
import game.tile.Tile;

/**
 * Creates a default tile with unique sprite to let the player know (and the dev) that something went wrong here.
 *
 * @author Raphael
 */
public class GlitchedTile extends AbstractTile {

    public GlitchedTile() {
        super(" □ ", false,false);
    }

    public  Tile getTileFromId(int id){
        return null;
    }
}
