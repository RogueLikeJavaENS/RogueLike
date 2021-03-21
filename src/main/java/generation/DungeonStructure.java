package generation;

import gameElement.Dungeon;
import gameElement.GraphDungeon;
import gameElement.Room;
import utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.*;

/**
 * This class uses the graphDungeon to generate a Dungeon
 *
 * @author Luca
 */
public class DungeonStructure {
    public static Dungeon createDungeon(Seed seed){
        List<Room> roomList = new ArrayList<>();
        GraphDungeon dungeon1 = new GraphDungeon(seed);
        HashMap<Integer,int[]> graph = dungeon1.getGraph();
        for (int i = 0; i < graph.size(); i++) {
            Room room = RoomStructure.createRoom(i, seed, graph.get(i));
            roomList.add(room);
        }
        return new Dungeon(roomList, dungeon1);
    }

    /**
     * This method takes the seed List and concatenates it in one Integer.
     *
     * @param seed
     * @return seedValue
     */
    private static int seedValue(Seed seed){
        List<String> iterseed = seed.getSeed();
        int seedValue = 0;
        for (int i = 0; i < iterseed.size(); i++) {
              seedValue += Integer.valueOf(iterseed.get(i), 16);
        }
        return seedValue;
    }

    /**
     * This method takes the seed and uses it to generate a random number for our dungeon.
     *
     * @param seed
     * @return NbRoom
     */
    public static int numberOfRoom(Seed seed){
        int MIN_NUMBER_ROOM = 14;
        int MAX_NUMBER_ROOM = 20;
        int seedValue=seedValue(seed);
        int NbRoom = (int) Math.floor(seedValue%MAX_NUMBER_ROOM);
        if (NbRoom<MIN_NUMBER_ROOM){
            NbRoom=MIN_NUMBER_ROOM;
        }
        return NbRoom;
    }

    /**
     * This method initialize the nextList, a list of integer that contains the linked room respectively in North, East, South, West
     * and the Ordinate and Abscissa of the room, at -1 to be filled later.
     *
     * @return nextList
     */
    public static int[] initNextlist(){
        int[] nextList = new int[6];
        Arrays.fill(nextList, -1);
        return nextList;
    }
}