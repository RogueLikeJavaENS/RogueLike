/**
 * This class contains the content of all the elements of the game.
 *
 * @author Antoine
 */

public class Room {
    int roomNum;
    int nextRoomNum;
    int[][] contents;
    int width;
    int height;

    /**
     *
     * @param roomNum Number of the room.
     * @param nextRoomNum Number of the next room.
     * @param contents Int table that content the id of the game's elements.
     * @param width Width of the Dungeon
     * @param height Height of the Dungeon
     *
     */

    Room(int roomNum, int nextRoomNum, int[][] contents, int width, int height) {
        this.roomNum = roomNum;
        this.nextRoomNum = nextRoomNum;
        this.contents = contents;
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @return int The room number.
     */
    public int getRoomNum() {
        return roomNum;
    }

    /**
     *
     * @return int The next room's Number.
     */
    public int getNextRoomNum() {
        return nextRoomNum;
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
     * @return int Width of the Room.
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
}
