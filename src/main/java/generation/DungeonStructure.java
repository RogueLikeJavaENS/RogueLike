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


public class DungeonStructure {
//    public static Dungeon createDungeon(Seed seed){
//    //number's of room is going to be seed defined later
//        List<Room> roomList = new ArrayList<>();
//        //HashMap<Room,int[]> DungeonStructure;
//        int quantity = NumberOfRoom(seed);
//
//        /*int[] nextList1 = new int[4];
//        int[] nextList2 = new int[4];
//        Arrays.fill(nextList1, -1);
//        Arrays.fill(nextList2, -1);
//        nextList1[0] = 2;
//        nextList2[2] = 1;*/
//
//        for (int i = 1; i <= quantity ; i++) {
//            if (i==1){
//                Position StartingPosition = new Position(Integer.parseInt(seed.getSeed().get(1)) , Integer.parseInt(seed.getSeed().get(2)));
//                roomList.add(RoomStructure.createRoom(i, seed, StartingPosition));
//            }
//            Position position = new
//        }
//        /*Room room1 = RoomStructure.createRoom(1 , seed, "north");
//        roomList.add(room1);
//        Room room2 = RoomStructure.createRoom(2 , seed, "south");
//        roomList.add(room2);*/
//        return new Dungeon(roomList);
//    }
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