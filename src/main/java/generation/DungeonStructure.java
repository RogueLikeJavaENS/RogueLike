package generation;

import entity.Entity;
import entity.object.Door;
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
        int quantity = 2;

        int[] nextList1 = new int[4];
        int[] nextList2 = new int[4];
        Arrays.fill(nextList1, -1);
        Arrays.fill(nextList2, -1);
        nextList1[0] = 2;
        nextList2[2] = 1;

        //for (int i = 1; i <= quantity ; i++) {}
        Room room1 = RoomStructure.createRoom(1 , nextList1, seed, "north");
        roomList.add(room1);
        Room room2 = RoomStructure.createRoom(2 , nextList2, seed, "south");
        roomList.add(room2);

        Door door1 = new Door(new Position(6,0),room2, 0);
        Door door2 = new Door(new Position(6,9),room1, 2);
        room1.addEntity(door1);
        room2.addEntity(door2);

        door1.setNext(door2);
        door2.setNext(door1);
        return new Dungeon(roomList);
    }
}