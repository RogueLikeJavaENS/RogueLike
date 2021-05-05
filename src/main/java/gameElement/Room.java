package gameElement;

import display.tiles.Tile;
import entity.Entity;
import entity.object.Door;
import generation.RoomType;
import utils.Direction;
import utils.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains the content of all the elements of the game.
 *
 * @author Antoine
 */

public class Room {
    private final int roomNum;
    private final int[][] contents;
    private final int width;
    private final int height;
    private final int[] nearRoom;
    private final Position position;
    private final List<Entity> entities;
    private final RoomType roomType;

    /**
     *
     * @param roomNum Number of the room.
     * @param nearRoom List of all rooms. 0-N, 1-E, 2-S, 3-W
     * @param contents Int table that content the id of the game's elements.
     * @param position Position object of the Room.
     * @param width Width of the gameElement.Dungeon
     * @param height Height of the gameElement.Dungeon
     *
     */

    public Room(int roomNum, int[] nearRoom, int[][] contents,  Position position, int width, int height, RoomType roomType) {
        this.roomNum = roomNum;
        this.nearRoom = nearRoom;
        this.contents = contents;
        this.width = width;
        this.height = height;
        this.position = position;
        this.entities = new ArrayList<>();
        this.roomType = roomType;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /* GETTERS */
    public Position getPosition(){ return position;}
    public Position getCenter() { return (new Position(width /2, height /2)); }
    public int getRoomNum() { return roomNum; }
    public int[] getNearRoom() { return nearRoom; }
    public int[][] getContents() { return contents; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public RoomType getRoomType() { return roomType; }
    public List<Entity> getEntities() { return new ArrayList<>(entities); }
    public void removeEntity(Entity entity){
        this.entities.remove(entity);
    }

    /**
     * Returns the roomNum of the room at the given direction.
     * @param direction NORTH, SOUTH, EAST or WEST
     * @return the corresponding roomNum in nearRoom, -1 if there's none
     *
     * @author Raphael
     */
    public int getRoomAt(Direction direction) {
        return nearRoom[direction.getValue()];
    }

    public List<Position> getAvailablePositions() {
        List<Position> positions = new ArrayList<>();
        for (int ord = 0; ord < contents.length; ord++) {
            for (int abs = 0; abs < contents[0].length; abs++) {
                if (abs == width/2 && ord == height/2) break;
                if (contents[ord][abs] == Tile.FLOOR.getId() && getEntitiesAt(abs, ord).size() == 0) {
                    positions.add(new Position(abs, ord));
                }
            }
        }

        positions = positions.stream()
                .filter(pos -> !getForbiddenPositions().contains(pos))
                .collect(Collectors.toList());
        Collections.shuffle(positions);
        return positions;
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

    public List<Position> getForbiddenPositions() {
        ArrayList<Position> forbiddenPosition = new ArrayList<>(); // positions in front of a door is forbidden.
        forbiddenPosition.add(new Position(width/2, 1)); // North
        forbiddenPosition.add(new Position(width/2, height-2)); // South
        forbiddenPosition.add(new Position(1, height/2)); // West
        forbiddenPosition.add(new Position(width-2, height/2)); // East
        return forbiddenPosition;
    }

    public List<Door> getDoors(){
        List<Door> doors = new ArrayList<>();
        for (Entity entity : entities){
            if (entity instanceof Door){
                doors.add((Door) entity);
            }
        }
        return doors;
    }

}
