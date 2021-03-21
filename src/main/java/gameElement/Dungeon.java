package gameElement;

import java.util.List;
import java.util.Objects;

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

    public GraphDungeon getGraph() {
        return graph;
    }

    private GraphDungeon graph;
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

    public Dungeon(List<Room> roomList, GraphDungeon graph) {
        this.roomList = roomList;
        this.graph = graph;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        if ((this.roomList.size() == dungeon.roomList.size()) &&
                (roomList.equals(dungeon.roomList))) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomList);
    }
}
