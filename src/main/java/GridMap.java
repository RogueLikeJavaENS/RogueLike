/**
 * This class Contains the actual Room. It contains a
 */
public class GridMap {
    Room room;
    Tile[][] tiles;
    // Entity[][] entities;
    // Player player;

    GridMap(Room room/*, Player player*/) {
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
                System.out.println(width);
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
        System.out.println(room.getHeight() + " " + room.getWidth());
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
