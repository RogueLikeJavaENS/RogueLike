package generation;

import gameElement.Room;
import utils.Direction;
import utils.Position;

public class RoomStructure {
    private final static int ROOM_WIDTH = 22;
    private final static int ROOM_HEIGHT = 10;

    public static Room createRoom(int current, int[] nextList) {

        Position roomPosition = new Position(nextList[5], nextList[4]); // using y x might need to reverse
        int[][] contents;
        contents = new int[ROOM_HEIGHT][ROOM_WIDTH];
        for (int z = 0; z < 4; z++) {
            if (nextList[z] != -1) {
                switch (Direction.intToDirection(z)) {
                    case NORTH:
                        contents[0][ROOM_WIDTH/2] = 3;
                        break;
                    case EAST:
                        contents[ROOM_HEIGHT/2][ROOM_WIDTH-1] = 3;
                        break;
                    case SOUTH:
                        contents[ROOM_HEIGHT-1][ROOM_WIDTH/2] = 3;
                        break;
                    case WEST:
                        contents[ROOM_HEIGHT/2][0] = 3;
                        break;
                }
            }
        }
        for (int x = 0; x < ROOM_HEIGHT; x++) {
            for (int y = 0; y < ROOM_WIDTH; y++) {
                if (x == 0 || x == ROOM_HEIGHT - 1 || y == 0 || y == ROOM_WIDTH - 1) {
                    if (contents[x][y] != 3) {
                        contents[x][y] = 1;
                    } else {
                        contents[x][y] = 3;
                    }
                } else {
                    contents[x][y] = 2;
                }
            }
        }
        int[] nextRoom = new int[4];
        System.arraycopy(nextList, 0, nextRoom, 0, 4);
        return new Room(current, nextRoom, contents, roomPosition, ROOM_WIDTH, ROOM_HEIGHT);
    }
}