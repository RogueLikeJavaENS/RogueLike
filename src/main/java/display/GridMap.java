package display;

import entity.Entity;
import entity.living.Player;
import gameElement.Room;
import utils.Position;

/**
 * This class Contains the actual gameElement.Room. It contains a
 */
public class GridMap {
    Room room;
    Tile[][] tiles;
    Entity[][] entities;
    Player player;

    public GridMap(Room room, Player player) {
        this.player = player;
        this.room = room;
        this.tiles = new Tile[room.getHeight()][room.getWidth()];
        this.entities = new Entity[room.getHeight()][room.getWidth()];
        fillRoomContent();
    }

    public void update() {
        fillEntityContent();
    }
    private void fillRoomContent() {
        int[][] contents = room.getContents();
        int width = room.getWidth();
        int height = room.getHeight();
        TileFactory tileFactory = new TileFactory();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int contentId = contents[y][x];
                Tile tile = tileFactory.getTile(contentId);
                tiles[y][x] = tile;
            }
        }
    }

    private void fillEntityContent() {
        Position pp = player.getPosition();
        for (int y = 0; y < room.getHeight(); y++) {
            for (int x = 0; x < room.getWidth(); x++) {
                entities[y][x] = null;
            }
        }
        entities[pp.getOrd()][pp.getAbs()] = player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < room.getHeight(); y++) {
            for (int i = 0; i < 2; i++) {
                for(int x = 0; x < room.getWidth(); x++) {
                    if (entities[y][x] != null) {
                        sb.append(entities[y][x]);
                    } else {
                        sb.append(tiles[y][x]);
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * @param x Abscissa of the wanted tile.
     * @param y Ordinate of the wanted tile.
     * @return Returns the tile at the given coordinates.
     *
     * @author Raphael
     */
    public Tile getTileAt(int x, int y) {
        return tiles[y][x]; //Needs to be tested, coordinates might be inverted.
    }

}
