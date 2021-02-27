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
        return new Dungeon(roomList);
    }
}