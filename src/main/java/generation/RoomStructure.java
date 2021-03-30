package generation;

import display.tiles.Tile;
import gameElement.Room;
import utils.Position;

public class RoomStructure {
    private final static int WIDTH = 15;
    private final static int HEIGHT = 11;
    private final static int EMPTY = 2;

    /**
     * @param current The current number of the room
     * @param nextList Array with the position and the number of the next Room
     * @return Room with the correct wall and doors
     */

    public static Room createRoom(int current, int[] nextList) {

        Position roomPosition = new Position(nextList[5], nextList[4]); // using y x might need to reverse
        int[][] contents;
        contents = new int[HEIGHT][WIDTH];

        Tile empty = Tile.EMPTY;
        Tile floor = Tile.FLOOR;

        fillSquare(0, 0, EMPTY, EMPTY, empty.getId(), contents); // fill void NORTH-WEST
        fillSquare(WIDTH - EMPTY, 0, WIDTH -1, EMPTY, empty.getId(), contents); // NORTH-EAST
        fillSquare(0, HEIGHT - EMPTY, EMPTY, HEIGHT -1 ,empty.getId(),contents); // SOUTH-WEST
        fillSquare(WIDTH - EMPTY, HEIGHT - EMPTY, WIDTH -1 , HEIGHT -1, empty.getId(), contents);
        fillSquare(EMPTY+1, EMPTY+1,WIDTH-(EMPTY+2), HEIGHT-(EMPTY+2),floor.getId(), contents);

        fillNorth(nextList[0] != -1, contents);
        fillEast(nextList[1] != -1, contents);
        fillSouth(nextList[2] != -1, contents);
        fillWest(nextList[3] != -1, contents);

        int[] nextRoom = new int[4];
        System.arraycopy(nextList, 0, nextRoom, 0, 4);
        return new Room(current, nextRoom, contents, roomPosition, WIDTH, HEIGHT);
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private static void fillNorth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(EMPTY, 0, WIDTH -(EMPTY +1), EMPTY, Tile.WALL.getId(), contents); //Northern wall
            fillSquare(EMPTY +1, 1, WIDTH -(EMPTY +2), EMPTY, Tile.FLOOR.getId(), contents); // Floor
            contents[0][WIDTH /2] = Tile.DOOR.getId(); // Door
        } else {
            fillSquare(EMPTY, 0, WIDTH -(EMPTY +1), EMPTY -1, Tile.EMPTY.getId(), contents);
            fillSquare(EMPTY +1, EMPTY, WIDTH -(EMPTY +2), EMPTY, Tile.WALL.getId(), contents); // Northern wall
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private static void fillSouth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(EMPTY, HEIGHT -(EMPTY +1), WIDTH -(EMPTY +1), HEIGHT -1,
                    Tile.WALL.getId(), contents);
            fillSquare(EMPTY +1, HEIGHT -(EMPTY +1), WIDTH -(EMPTY +2), HEIGHT -(EMPTY),
                    Tile.FLOOR.getId(), contents);
            contents[HEIGHT -1][WIDTH /2] = Tile.DOOR.getId();
        } else {
            fillSquare(EMPTY, HEIGHT -(EMPTY), WIDTH -(EMPTY +1), HEIGHT -1,
                    Tile.EMPTY.getId(), contents);
            fillSquare(EMPTY +1, HEIGHT -(EMPTY +1), WIDTH -(EMPTY +2), HEIGHT -(EMPTY +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private static void fillEast(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(WIDTH -(EMPTY +1), EMPTY, WIDTH -1, HEIGHT -(EMPTY +1),
                    Tile.WALL.getId(), contents);
            fillSquare(WIDTH -(EMPTY +1), EMPTY +1 , WIDTH -2, HEIGHT -(EMPTY +2),
                    Tile.FLOOR.getId(), contents);
            contents[HEIGHT /2][WIDTH -1] = Tile.DOOR.getId();
        } else {
            fillSquare(WIDTH -(EMPTY), EMPTY, WIDTH -1, HEIGHT -(EMPTY +1),
                    Tile.EMPTY.getId(), contents);
            fillSquare(WIDTH -(EMPTY +1), EMPTY, WIDTH -(EMPTY +1), HEIGHT -(EMPTY +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private static void fillWest(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(0, EMPTY, EMPTY, HEIGHT -(EMPTY +1),
                    Tile.WALL.getId(), contents);
            fillSquare(1, EMPTY +1, EMPTY, HEIGHT -(EMPTY +2),
                    Tile.FLOOR.getId(), contents);
            contents[HEIGHT /2][0] = Tile.DOOR.getId();
        } else {
            fillSquare(0, EMPTY, EMPTY -1, HEIGHT -(EMPTY +1),
                    Tile.EMPTY.getId(), contents);
            fillSquare(EMPTY, EMPTY, EMPTY, HEIGHT -(EMPTY +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param abs1 int abs position from the upper-left position
     * @param ord1 int ord position from the upper-left position
     * @param abs2 int abs position from the up-right position
     * @param ord2 int ord position from the up-right position
     * @param tile int id of the tile
     * @param contents array of tiles' id
     *
     * The method fills the contents' array with the given tile id in the square's position
     */

    private static void fillSquare(int abs1, int ord1, int abs2, int ord2, int tile, int[][] contents) {
        if (abs1 > abs2 || ord1 > ord2) {
            int absTmp = abs1;
            int ordTmp = ord1;
            abs1 = abs2; ord1 = ord2;
            abs2 = absTmp; ord2 = ordTmp;
        }
        for (int ord = ord1; ord <= ord2; ord++) {
            for (int abs = abs1; abs <= abs2; abs++) {
                contents[ord][abs] = tile;
            }
        }
    }

}