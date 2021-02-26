import java.util.List;

public class Dungeon {
    List<Room> roomList;

    Dungeon(Seed seed) {
        this.roomList = DungeonStructure.createDunegeon(seed);
    }

}
