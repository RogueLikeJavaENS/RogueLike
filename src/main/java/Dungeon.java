import java.util.List;

public class Dungeon {
    List<Room> roomList;

    Dungeon(Seed seed) {
        this.roomList = DungeonStructure.createDungeon(seed);
    }

    public Room getRoom(int roomNum) {
        return roomList.get(roomNum);
    }

}
