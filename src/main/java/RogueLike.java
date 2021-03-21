import display.GridMap;
import display.HUD;
import display.RendererUI;
import entity.Player;
import gameElement.Dungeon;
import gameElement.Room;
import generation.DungeonStructure;
import gameElement.MiniMap;
import generation.Seed;
import utils.Position;

import javax.xml.transform.Source;
import java.util.Arrays;
import java.util.List;

/**
 * This is the main class of the RogueLike Game.
 *
 * @author Antoine
 */

public class RogueLike {

//    /**
//     * Create an instance of the game.
//     */
//    RogueLike() {
//        Seed seed = new Seed();
//        Dungeon dungeon = DungeonStructure.createDungeon(seed);
//        Player player = new Player(new Position(0,0),100, 100, "Hero", 1);
//        GridMap gridMap = new GridMap(dungeon.getRoom(0));
//        MiniMap miniMap = new MiniMap(dungeon);
//        HUD hud = new HUD(player);
//
//        RendererUI.roomRender(gridMap);
//        RendererUI.miniMapRender(miniMap);
//        RendererUI.hudRender(hud);
//    }
//
//    public static void main(String[] args) {
//        RogueLike rogueLike = new RogueLike();
//    }
public static void main(String[] args) {
    Seed seed = new Seed();
    Dungeon dungeon = DungeonStructure.createDungeon(seed);
    List<Room> test=dungeon.getRoomList();
    for (int i = 0; i < dungeon.getGraph().getGraph().size(); i++) {
        System.out.println(Arrays.toString(dungeon.getGraph().getGraph().get(i)) + " " + i);
    }
    for (Room room:
         test) {
        System.out.println(room.getHeight());
        System.out.println(room.getWidth());
        System.out.println(room.getContents().length);
        System.out.println(room.getContents()[0].length);
        for (int i = 0; i < room.getHeight(); i++) {
            for (int j = 0; j < room.getWidth(); j++) {
                System.out.printf("%d ",room.getContents()[i][j]);
            }
            System.out.println("\n");
        }
        System.out.println("-------------------");
    }
}
}