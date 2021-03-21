package generation;

import entity.Entity;
import gameElement.Room;
import utils.Position;

import java.util.List;
import utils.Position;

import java.lang.reflect.Array;
import java.util.Arrays;

public class RoomStructure {
    public static Room createRoom(int current, Seed seed, int[] nextList) {
        int ROOM_WIDTH = 22;
        int ROOM_HEIGHT = 10;

        Position roomPosition = new Position(nextList[5], nextList[4]); // using y x might need to reverse because i'm a dumbass
        int[][] contents;
        contents = new int[ROOM_HEIGHT][ROOM_WIDTH];
        for (int z = 0; z < 4; z++) {
            if (nextList[z] != -1) {
                switch (z) {
                    case 0:
                        contents[0][ROOM_WIDTH/2] = 3;
                        break;
                    case 1:
                        contents[ROOM_HEIGHT/2][ROOM_WIDTH-1] = 3;
                        break;
                    case 2:
                        contents[ROOM_HEIGHT-1][ROOM_WIDTH/2] = 3;
                        break;
                    case 3:
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
        return new Room(current, nextList, contents, roomPosition, ROOM_WIDTH, ROOM_HEIGHT);
    }
}