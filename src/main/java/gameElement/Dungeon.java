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
    private List<Room> roomList;
    private int width; // width -> max numbers of rooms in a column
    private int height;  // height -> max numbers of rooms in a row
    private GraphDungeon graph;

    /**
     * List of List of roomNumber.
     * Index 0 -> North,
     *       1 -> East,
     *       2 -> South,
     *       3 -> West.
     */

    public Dungeon(List<Room> roomList, int width, int height, GraphDungeon graph) {
        this.roomList = roomList;
        this.graph = graph;
        this.width = width;
        this.height = height;
    }

    public int getHeight() { return height; }
    public int getWidth() { return width; }

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

    public List<Room> getRoomList() {
        return roomList;
    }
    public GraphDungeon getGraph() {
        return graph;
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
