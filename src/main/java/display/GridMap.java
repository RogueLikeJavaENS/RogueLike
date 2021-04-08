package display;

import entity.Entity;
import gameElement.Room;
import java.util.ArrayList;
import java.util.List;

/**
 * This class Contains the actual gameElement.Room. It contains a
 */
public class GridMap {
    private final Room room;
    private final Tile[][] tiles;
    private List<Entity> entities;

    public GridMap(Room room) {
        this.room = room;
        this.tiles = new Tile[room.getHeight()][room.getWidth()];
        fillRoomContent();
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
        entities = room.getEntities();
    }

    public void update(List<Entity> entitiesToAdd, List<Entity> entitiesToRemove) {
        for(Entity entity : entitiesToAdd) {
            addEntity(entity);
        }
        for(Entity entity : entitiesToRemove) {
            removeEntity(entity);
        }
    }

    public void update(Entity entity, boolean add) {
        if(add) {
            addEntity(entity);
        }
        else {
            removeEntity(entity);
        }
    }

    private void addEntity(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    private void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<String> StrByLine (){
        List<String> strLine = new ArrayList<>();
        for (int ord = 0; ord<room.getHeight(); ord++){
            for (int i = 0; i <2; i++) {
                StringBuilder sb = new StringBuilder();
                for (int abs = 0; abs < room.getWidth(); abs++) {
                    List<Entity> entitiesAt = getEntitiesAt(abs, ord);
                    if (entitiesAt.size() != 0) {
                        sb.append(entitiesAt.get(0));
                    } else {
                        sb.append(tiles[ord][abs]);
                    }
                }
                strLine.add(sb.toString());
            }
        }
        return strLine;
    }

    public List<Entity> getEntitiesAt(int abs, int ord) {
        List<Entity> entitiesAt = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getPosition().equals(abs, ord)) {
                entitiesAt.add(entity);
            }
        }
        return entitiesAt;
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
