package display;

import display.tiles.EmptyTile;
import entity.Entity;
import entity.living.LivingEntity;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;
import gameElement.Room;
import spells.Range;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class Contains the actual gameElement.Room. It contains a
 */
public class GridMap {
    private final Room room;
    private final Tile[][] tiles;
    private List<Entity> entities;
    private List<String> strByLine;
    private List<Position> rangeList;

    public GridMap(Room room) {
        this.room = room;
        this.tiles = new Tile[room.getHeight()][room.getWidth()];
        fillRoomContent();
        fillEntityContent();
        rangeList = new ArrayList<>();
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

    private void fillEntityContent() { entities = room.getEntities(); }

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

    /**
     * Update the List of String used to print the GridMap
     *
     */
    public void updateDisplayGridMap () {
        List<String> strLine = new ArrayList<>();
        for (int ord = 0; ord<room.getHeight(); ord++)
        {
            for (int i = 0; i <2; i++) // made 2 times because the tile has a height of 2
            {
                StringBuilder sb = new StringBuilder();     // create each line
                int nbEmptyTile = 0;
                for (int abs = 0; abs < room.getWidth(); abs++)
                {
                    List<Entity> entitiesAt = getEntitiesAt(abs, ord);
                    if (entitiesAt.size() != 0)// print the first entity of the tile
                    {
                        if (isInRange(abs, ord) && !(entities.get(0) instanceof Player)) {
                            sb.append(colorize(entitiesAt.get(0).getSprites(i), Colors.SOFT_GREY.bgApply()));
                        }
                        else {
                            sb.append(entitiesAt.get(0).getSprites(i));
                        }
                    }
                    else // if no entity, print the tile
                    {
                        if (isInRange(abs, ord)) {
                            sb.append(colorize(tiles[ord][abs].toString(), Colors.SOFT_GREY.bgApply()));
                        }
                        else {
                            sb.append(tiles[ord][abs]);
                        }
                        if (tiles[ord][abs] instanceof EmptyTile) // if the tile is empty increment nbEmptyTile
                        {
                            nbEmptyTile++;
                        }
                    }
                }
                if (nbEmptyTile != room.getWidth()) // if all the tile on the line are empty, don't add the line on the result
                {
                    strLine.add(sb.toString());
                }
            }
        }
        strByLine = strLine;
    }

    public void updateRangeList(Range range) {
        rangeList.clear();
        for (int abs = range.getTopLeftCorner().getAbs();
             abs <= range.getBottomRightCorner().getAbs();
             abs++) {
            for (int ord = range.getTopLeftCorner().getOrd();
                 ord <= range.getBottomRightCorner().getOrd();
                 ord++) {
                if (isOnValidPosition(abs, ord)) {
                    rangeList.add(new Position(abs, ord));
                }

            }
        }
    }

    public void clearRangeList() {
        rangeList.clear();
    }

    public boolean isOnValidPosition(int abs, int ord) {
        if (abs < 0 || ord < 0) {
            return false;
        }
        else if (abs >= tiles[0].length || ord >= tiles.length) {
            return false;
        }
        else if (!getTileAt(abs, ord).isAccessible()) {
            return false;
        }
        for (Entity entity : getEntitiesAt(abs, ord)) {
            if (!(entity instanceof Monster) && (!entity.getIsAccessible())) {
                return false;
            }
        }
        return true;
    }

    public boolean isInRange(int abs, int ord) {
        return rangeList.contains(new Position(abs, ord));
    }

    /**
     * Return the list of the line of gridmap
     *
     * @return the list which contains all the line in order to print gridMap
     */
    public List<String> getStrByLine() { return strByLine; }

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
    public List<Entity> getEntities() {
        return entities;
    }
    public List<LivingEntity> getLivingEntities() {
        List<LivingEntity> livingEntities = new ArrayList<>();
        for(Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                livingEntities.add((LivingEntity) entity);
            }
        }
        return livingEntities;
    }
    public List<LivingEntity> getMonsters() {
        List<LivingEntity> monsters = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add((LivingEntity) entity);
            }
        }
        return monsters;
    }

    public List<Position> getRangeList() {
        return rangeList;
    }
}
