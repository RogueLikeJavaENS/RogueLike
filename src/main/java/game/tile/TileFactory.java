package game.tile;

import game.tile.tiles.*;

public class TileFactory {

    public TileFactory() {
    }

    /**
     * Create and return the tile according the Int given as argument.
     * @param id Type from which the Tile to create will be decided.
     * @return A Tile : Wall = Type 1, Floor = Type 2, Door = Type 3
     */
    public Tile getTile(int id) {
        switch(id) {
            case 1:
                return new WallTile(); //Wall = Type 1

            case 2:
                return new FloorTile(); //Floor = Type 2

            case 3:
                return new DoorTile(); // Door = Type 3

            case 4:
                return new EmptyTile(); // Empty = Type 4

            default: return new GlitchedTile();
        }
    }
}
