import java.util.ArrayList;
import java.util.List;

public class DungeonStructure {
    public static List<Room> createDungeon(seed){
    //number's of room is going to be seed defined later
        List<Room> roomList = new ArrayList<>();
        int quantity = 2;
        //for (int i = 1; i <= quantity ; i++) {}
        Room 1 = RoomStructure.createRoom(1 , 2, seed, "north");
        roomList.add(1);
        Room 2 = RoomStructure.createRoom(2 , 1, seed, "south");
        roomList.add(2);
        return roomList;
    }
}