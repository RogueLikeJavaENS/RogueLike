import java.util.ArrayList;

/**
 * This class is used to fill based on the seed, the room structure.
 *
 * @author Luca
 */


public class RoomStructure {
    /**
     * createRoom create a Room object with the correct content
     * @return Room
     */
    public static Room createRoom(int current, int[] nextList, Seed seed, String direction){
        int[][] contents;
        contents = new int[10][22];
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
        return new Room(current, nextList, contents, 22, 10);
    }
}
