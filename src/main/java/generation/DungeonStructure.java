package generation;

import gameElement.Dungeon;
import gameElement.Room;
import utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DungeonStructure {
    public static Dungeon createDungeon(Seed seed){
    //number's of room is going to be seed defined later
        List<Room> roomList = new ArrayList<>();
        int quantity = 3;

        int[] nextList1 = new int[4];
        int[] nextList2 = new int[4];
        int[] nextList3 = new int[4];

        Arrays.fill(nextList1, -1);
        Arrays.fill(nextList2, -1);
        Arrays.fill(nextList3, -1);

        nextList1[0] = 2;
        nextList2[1] = 3;
        nextList2[2] = 1;
        nextList3[3] = 2;

        Position pos2 = new Position(0,0);
        Position pos1 = new Position(0,1);
        Position pos3 = new Position(1,0);


        //for (int i = 1; i <= quantity ; i++) {}
        Room room1 = RoomStructure.createRoom(1 , nextList1, seed, "north", pos1);
        roomList.add(room1);
        Room room2 = RoomStructure.createRoom(2 , nextList2, seed, "south", pos2);
        roomList.add(room2);
        Room room3 = RoomStructure.createRoom(3 , nextList3, seed, "south", pos3);
        roomList.add(room3);

        return new Dungeon(roomList,2,2);
    }
}