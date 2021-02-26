import java.util.ArrayList;
import java.util.List;

public class DungeonStructure {
    public static List<Room> createDungeon(Seed seed){
    //number's of room is going to be seed defined later
        List<Room> roomList = new ArrayList<>();
        int quantity = 2;
        //for (int i = 1; i <= quantity ; i++) {}
        Room room1 = RoomStructure.createRoom(1 , 2, seed, "north");
        roomList.add(room1);
        Room room2 = RoomStructure.createRoom(2 , 1, seed, "south");
        roomList.add(room2);
        return roomList;
    }
}