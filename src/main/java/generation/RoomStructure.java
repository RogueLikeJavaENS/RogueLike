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
        //int[] nextList = InitNextlist();
        /*switch (direction) {
            case "north" : contents[0][6]=3;
                break;
            case "south" : contents[9][6]=3;
                break;
            case "east" : contents[6][0]=3;
                break;
            case "west" : contents[6][21]=3;
        }*/
//        for (int x = 0; x < 10; x++) {
//            for (int y = 0; y < 22; y++) {
//                if (x==0 || x==9 || y==0 || y==21){
//                    if(contents[x][y]!=3) {
//                        contents[x][y] = 1;
//                    }
//                    else{
//                        contents[x][y] = 3;
//                    }
//                }
//                else{
//                    contents[x][y]=2;
//                }
//            }
//        }
        Position roomPosition = new Position(nextList[5], nextList[4]); // using y x might need to reverse because i'm a dumbass
        int[][] contents;
        contents = new int[ROOM_HEIGHT][ROOM_WIDTH];
        for (int z = 0; z < 4; z++) {
            if (nextList[z] != -1) {
                switch (z) {
                    case 0:
                        contents[0][6] = 3;
                        break;
                    case 1:
                        contents[6][0] = 3;
                        break;
                    case 2:
                        contents[9][6] = 3;
                        break;
                    case 3:
                        contents[6][21] = 3;
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
    /*private static int[] InitNextlist(){
        int[] nextList = new int[4];
        Arrays.fill(nextList, -1);
        return nextList;
    }*/