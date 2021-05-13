package game.elements;

import game.tile.TileEnum;
import game.entity.Entity;
import game.entity.object.elements.Door;
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
    private boolean wasVisited;

    /**
     * Create a room
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
        if (roomType == RoomType.START){
            wasVisited = true;
        }
        else {
            wasVisited = false;
        }
    }

    /**
     * Add an game.entity to the room
     * @param entity the game.entity to add
     */
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
    public boolean getWasVisited(){ return wasVisited;}
    public void setWasVisited(boolean wasVisited){ this.wasVisited = wasVisited;}


    /**
     * Remove an game.entity in the room
     * @param entity the game.entity to remove
     */
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

    /**
     * Return the position which is not in front of a door and where there is no entities
     *
     * @return the list of available position
     */
    public List<Position> getAvailablePositions() {
        List<Position> positions = new ArrayList<>();
        for (int ord = 0; ord < contents.length; ord++) {
            for (int abs = 0; abs < contents[0].length; abs++) {
                if (abs == width/2 && ord == height/2) break;
                if (contents[ord][abs] == TileEnum.FLOOR.getId() && getEntitiesAt(abs, ord).size() == 0) {
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

    /**
     * Return the list of entities which are at the position abs,ord
     *
     * @param abs the abscissa of the position
     * @param ord the ordinate of the position
     * @return the list of entities at the position abs,ord
     */
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
     * Return the list of the position in front of a door
     *
     * @return the list of position
     */
    public List<Position> getForbiddenPositions() {
        ArrayList<Position> forbiddenPosition = new ArrayList<>(); // positions in front of a door is forbidden.
        forbiddenPosition.add(new Position(width/2, 1)); // North
        forbiddenPosition.add(new Position(width/2, height-2)); // South
        forbiddenPosition.add(new Position(1, height/2)); // West
        forbiddenPosition.add(new Position(width-2, height/2)); // East
        return forbiddenPosition;
    }

    public List<Position> getWallAvailablePosition() {
        List<Position> positionList = new ArrayList<>();
        for (int abs = 0; abs < width; abs++) {
            for (int ord = 0; ord < height; ord++) {
                if (contents[ord][abs] == TileEnum.WALL.getId() && !isCorner(abs,ord)) {
                    positionList.add(new Position(abs, ord));
                }
            }
        }
        return positionList;
    }

    private boolean isCorner(int abs, int ord) {
        if (abs > 1 && abs < width-1) {
            int l = contents[ord][abs-1];
            int r = contents[ord][abs+1];
            return l != r;
        }
        if (ord > 1 && ord < height-1) {
            int u = contents[ord-1][abs];
            int d = contents[ord+1][abs];
            return u != d;
        }
        return false;
    }

    /**
     * Return the list of doors of the room
     *
     * @return list of doors
     */
    public List<Door> getDoors(){
        List<Door> doors = new ArrayList<>();
        for (Entity entity : entities){
            if (entity instanceof Door){
                doors.add((Door) entity);
            }
        }
        return doors;
    }

    public void setNearRoomBossEndVisited(){
        for (Door door : this.getDoors()){
            Room nextRoom = door.getNextRoom();
            if (nextRoom.getRoomType() == RoomType.END || nextRoom.getRoomType() == RoomType.BOSS){
                nextRoom.setWasVisited(true);
            }
        }
    }


}
