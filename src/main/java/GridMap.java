/**
 * This class Contains the actual Room. It contains a
 */
public class GridMap {
    Room room;
    Tile[][] tiles;
    Entity[][] entities;
    Player player;

    GridMap(Room room/*, Player player*/) {
        this.room = room;
    }

    private void fillRoomContent() {
        int[][] contents = room.getContents();
        int width = room.getWidth();
        int height = room.getHeight();
        TileFactory tileFactory = new TileFactory("/src/main/java/xmlExample.xml");

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int contentId = contents[x][y];
                Tile tile = tileFactory.getTile(contentId);
                tiles[x][y] = tile;
            }
        }
    }

    private void fillEntityContent() {
        // nothing
    }
}
