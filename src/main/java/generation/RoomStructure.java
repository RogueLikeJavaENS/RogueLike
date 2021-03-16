package generation;

import gameElement.Room;
import utils.Position;

public class RoomStructure {
    public static Room createRoom(int current, int[] nextList, Seed seed, String direction, Position pos) {
        int[][] contents;
        contents = new int[10][22];
        switch (direction) {
            case "north" : contents[0][6]=3;
                break;
            case "south" : contents[9][6]=3;
                break;
            case "east" : contents[6][0]=3;
                break;
            case "west" : contents[6][21]=3;
        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 22; y++) {
                if (x==0 || x==9 || y==0 || y==21){
                    if(contents[x][y]!=3) {
                        contents[x][y] = 1;
                    }
                    else{
                        contents[x][y] = 3;
                    }
                }
                else{
                    contents[x][y]=2;
                }
            }
        }
        return new Room(current, nextList, contents, 22, 10,pos);
    }
}
