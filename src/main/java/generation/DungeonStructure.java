package generation;

import entity.Entity;
import entity.object.Door;
import gameElement.Dungeon;
import gameElement.GraphDungeon;
import gameElement.Room;
import utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.*;


public class DungeonStructure {
    private final static int DUNGEON_HEIGHT = 6;
    private final static int DUNGEON_WIDTH = 6;

    public static Dungeon createDungeon(Seed seed){
        List<Room> roomList = new ArrayList<>();
        GraphDungeon dungeon1 = new GraphDungeon(seed);
        HashMap<Integer,int[]> graph = dungeon1.getGraph();
        for (int i = 0; i < graph.size(); i++) {
            Room room = RoomStructure.createRoom(i, seed, graph.get(i));
            roomList.add(room);
        }
        return new Dungeon(roomList, DUNGEON_WIDTH, DUNGEON_HEIGHT, dungeon1);
    }
    private static int seedValue(Seed seed){
        List<String> iterseed = seed.getSeed();
        int seedValue = 0;
        for (int i = 0; i < iterseed.size(); i++) {
              seedValue += Integer.valueOf(iterseed.get(i), 16);
        }
        return seedValue;
    }
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
    public static int[] initNextlist(){
        int[] nextList = new int[6];
        Arrays.fill(nextList, -1);
        return nextList;
    }
}