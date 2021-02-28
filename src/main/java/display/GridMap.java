package display;

import gameElement.Room;

/**
 * This class Contains the actual gameElement.Room. It contains a
 */
public class GridMap {
    Room room;
    Tile[][] tiles;
    // Entity[][] entities;
    // Player player;

    public GridMap(Room room/*, Player player*/) {
        this.room = room;
        this.tiles = new Tile[room.getHeight()][room.getWidth()];
        fillRoomContent();
    }

    private void fillRoomContent() {
        int[][] contents = room.getContents();
        int width = room.getWidth();
        int height = room.getHeight();
        TileFactory tileFactory = new TileFactory("src/main/java/xmlExample.xml");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int contentId = contents[y][x];
                Tile tile = tileFactory.getTile(contentId);
                tiles[y][x] = tile;
            }
        }
    }

    private void fillEntityContent() {
        // nothing now
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < room.getHeight(); y++) {

            for(int x = 0; x < room.getWidth(); x++) {
                sb.append(tiles[y][x]);
            }
            sb.append("\n");
            for(int x = 0; x < room.getWidth(); x++) {
                sb.append(tiles[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
