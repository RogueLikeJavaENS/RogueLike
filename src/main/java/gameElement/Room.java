package gameElement;

import entity.Entity;
import utils.Position;

import java.util.ArrayList;
import utils.Position;

import java.util.List;

/**
 * This class contains the content of all the elements of the game.
 *
 * @author Antoine
 */

public class Room {
    int roomNum;
    int[][] contents;
    int width;
    int height;
    int[] nearRoom;
    Position position;
    private List<Entity> entities;

    /**
     *
     * @param roomNum Number of the room.
     * @param nearRoom List of all rooms. 0-N, 1-E, 2-S, 3-W
     * @param contents Int table that content the id of the game's elements.
     * @param width Width of the gameElement.Dungeon
     * @param height Height of the gameElement.Dungeon
     *
     */

    public Room(int roomNum, int[] nearRoom, int[][] contents, int width, int height, Position position) {
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
     * Get the northern room number. If the room doesn't have north way, return -1
     * @return int
     */
    public int getNorth() {
        return this.nearRoom[0];
    }

    /**
     * Get the eastern room number. If the room doesn't have north way, return -1
     * @return int
     */
    public int getEast() {
        return this.nearRoom[1];
    }
    /**
     * Get the southern room number. If the room doesn't have north way, return -1
     * @return int
     */
    public int getSouth() {
        return this.nearRoom[2];
    }

    /**
     * Get the western room number. If the room doesn't have north way, return -1
     * @return int
     */
    public int getWest() {
        return this.nearRoom[3];
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
