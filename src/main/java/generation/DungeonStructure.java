package generation;

import game.entity.living.player.classeSystem.InGameClasses;
import game.elements.Dungeon;
import game.elements.Room;

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
    private final static int DUNGEON_HEIGHT = 6;
    private final static int DUNGEON_WIDTH = 6;
    private final static int ROOM_WIDTH = 15;
    private final static int ROOM_HEIGHT = 11;

    public static Dungeon createDungeon(Seed seed, int floor, InGameClasses classe){
        List<Room> roomList = new ArrayList<>();
        GraphDungeon dungeon1 = new GraphDungeon(seed);
        HashMap<Integer,int[]> graph = dungeon1.getGraph();
        List<RoomType> roomTypes = dungeon1.getRoomsType();
        RoomFactory rs = new RoomFactory(ROOM_WIDTH, ROOM_HEIGHT, 2, floor);
        for (int i = 0; i < graph.size(); i++) {
            Room room = rs.getRoom(seed, roomTypes.get(i), i, graph.get(i));
            roomList.add(room);
        }

        return new Dungeon(roomList, DUNGEON_WIDTH, DUNGEON_HEIGHT, dungeon1,ROOM_HEIGHT,ROOM_WIDTH, floor, classe);
    }

    /**
     * This method takes the seed List and concatenates it in one Integer.
     *
     * @param seed seed from which the value will be returned.
     * @return the seed as an int.
     */
    private static int seedValue(Seed seed){
        List<String> iterseed = seed.getSeed();
        int seedValue = 0;
        for (String s : iterseed) {
            seedValue += Integer.valueOf(s, 16);
        }
        return seedValue;
    }

    /**
     * This method takes the seed and uses it to generate a random number for our dungeon.
     *
     * @param seed seed from which the number of room will be decided.
     * @return the number of room decided from the seed.
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