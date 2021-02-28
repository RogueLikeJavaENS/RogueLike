package gameElement;

import java.util.List;

/**
 * This class is graph representation of the dungeon.
 * 1 room can be connected by min 1 room and max 4 rooms.
 *
 * @author Antoine
 */

public class Dungeon {
    public List<Room> getRoomList() {
        return roomList;
    }

    List<Room> roomList;
    // width -> max numbers of rooms in a column
    // height -> max numbers of rooms in a row
    /**
     * List of List of roomNumber.
     * Index 0 -> North,
     *       1 -> East,
     *       2 -> South,
     *       3 -> West.
     */

    public Dungeon(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Room getRoom(int roomNum) {
        return roomList.get(roomNum);
    }

    /**
     * Get the dungeon size.
     * @return int
     */
    public int getDungeonSize(){
        return roomList.size();
    }

}
