package display;

import entity.Entity;
import entity.living.Player;
import gameElement.Room;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class Contains the actual gameElement.Room. It contains a
 */
public class GridMap {
    private Room room;
    private Tile[][] tiles;
    private Entity[][] entitiess;
    private List<Entity>[][] entities;
    private Player player;

    public GridMap(Room room, Player player) {
        this.player = player;
        this.room = room;
        this.tiles = new Tile[room.getHeight()][room.getWidth()];
        this.entities = new List[room.getHeight()][room.getWidth()];
        //this.entities = new Entity[room.getHeight()][room.getWidth()];
        fillRoomContent();
        fillEntityContent();
    }

    public void update(List<Entity> entityList) {
        for(Entity entity : entityList) {
            int abs = entity.getPosition().getAbs();
            int ord = entity.getPosition().getOrd();
            if (!entities[ord][abs].contains(entity)) {
                entities[ord][abs].add(entity);
            }
        }
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
                List<Entity> entityList = new ArrayList<>();
                entities[y][x] = entityList;
            }
        }
        List<Entity> entityList = new ArrayList<>();
        entityList.add(player);
        entities[pp.getOrd()][pp.getAbs()] = entityList;
    }

    private void updtateEntityContent() {
        Position pp = player.getPosition();

        List<Entity> entityList = new ArrayList<>();
        entityList.add(player);
        entities[pp.getOrd()][pp.getAbs()] = entityList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < room.getHeight(); y++) {
            for (int i = 0; i < 2; i++) {
                for(int x = 0; x < room.getWidth(); x++) {
                    if (entities[y][x].size() > 0) {
                        sb.append(entities[y][x].get(0)); // Methods to determine which entityt to display
                    } else {
                        sb.append(tiles[y][x]);
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public List<String> StrByLine (){
        List<String> strLine = new ArrayList<>();
        for (int ord = 0; ord<room.getHeight(); ord++){
            for (int i = 0; i <2; i++) {
                StringBuilder sb = new StringBuilder();
                for (int abs = 0; abs < room.getWidth(); abs++) {
                    if (entities[ord][abs].size() != 0) {
                        sb.append(entities[ord][abs].get(0));
                    } else {
                        sb.append(tiles[ord][abs]);
                    }
                }
                strLine.add(sb.toString());
            }
        }
        return strLine;
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

    public List<Entity> getEntitiesAt(int abs, int ord) {
        return entities[ord][abs];
    }

    public void removeEntity(Entity entity) {
        int abs = entity.getPosition().getAbs();
        int ord = entity.getPosition().getOrd();
        entities[ord][abs].remove(entity);
    }
}
