package gameElement;

import display.tiles.Tile;
import entity.Entity;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;

import java.util.List;

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

    public Room(int roomNum, int[] nearRoom, int[][] contents,  Position position, int width, int height) {
        this.roomNum = roomNum;
        this.nearRoom = nearRoom;
        this.contents = contents;
        this.width = width;
        this.height = height;
        this.position = position;
        this.entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Position> getAvailablePositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int ord = 0; ord < contents.length; ord++) {
            for (int abs = 0; abs < contents[0].length; abs++) {
                if (contents[ord][abs] == Tile.FLOOR.getId()) {
                    positions.add(new Position(abs, ord));
                }
            }
        }
        return positions;
    }

    public Position getPosition(){ return position;}

    public Position getCenter() {
        return (new Position(width /2, height /2));
    }

    /**
     *
     * @return int The room number.
     */
    public int getRoomNum() {
        return roomNum;
    }

    /**
     * Get the near room list
     * @return ArrayList<Integer>
     */
    public int[] getNearRoom() {
        return nearRoom;
    }

    /**
     *
     * @return int[][] The table that contains all elements.
     */
    public int[][] getContents() {
        return contents;
    }

    /**
     *
     * @return int Width of the gameElement.Room.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return int Height of the room.
     */
    public int getHeight() {
        return height;
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

    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }
}
