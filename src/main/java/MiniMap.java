import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to see the whole dungeon as a minimap
 *
 * @author Antoine
 */

public class MiniMap {
    Dungeon dungeon;
    List<String>[][] miniMapString;

    MiniMap(Dungeon dungeon) {
        this.dungeon = dungeon;
        List<Room> roomList = dungeon.getRoomList();

        // TODO : Create dynamically the minimap in space, and print it
        miniMapString = new List[2][3];
        for (int y = 0; y < 2; y++) {
            for (int x =0; x < 3; x++) {
                miniMapString[y][x] = getEmptyRoom();
            }
        }

        miniMapString[0][1] = buildRoom(roomList.get(1));
        miniMapString[1][1] = buildRoom(roomList.get(0));
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 2; y++) {
            for (int i = 0; i<4; i++) {
                for (int x = 0; x<3; x++) {
                    sb.append(miniMapString[y][x].get(i));
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private List<String> buildRoom(Room room) {
        List<String> sl = new ArrayList<>();
        // North Row

        if (room.getNorth() != -1) {
            sl.add(" __#__ ");
        } else sl.add(" _____ ");
        sl.add("|     |");

        // West and East row
        StringBuilder sb = new StringBuilder();
        if (room.getWest() != -1) {
            sb.append("#  ");
        } else sb.append("|  ");
        sb.append(room.getRoomNum());
        if (room.getEast() != -1) {
            sb.append("  #");
        } else sb.append("  |");
        sl.add(sb.toString());

        // South Row
        if (room.getSouth() != -1) {
            sl.add("|__#__|");
        } else sl.add("|_____|");
        return sl;
    }

    private List<String> getEmptyRoom() {
        List<String> sl = new ArrayList<>();
        sl.add("       ");
        sl.add("       ");
        sl.add("       ");
        sl.add("       ");
        return sl;
    }
}
