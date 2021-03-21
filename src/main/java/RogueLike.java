import display.GridMap;
import display.HUD;
import display.RendererUI;
import entity.living.Player;
import gameElement.Dungeon;
import gameElement.GameState;
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

    /**
     * Creates an instance of the game.
     */
    RogueLike() throws InterruptedException {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed);
        Position initialPosition = dungeon.getRoomList().get(0).getCenter();
        Player player = new Player(initialPosition,100, 100, "Hero", 1);
        GridMap gridMap = new GridMap(dungeon.getRoom(0), player);
        HUD hud = new HUD(player);
        ScanPanel sp = new ScanPanel();
        GameState gs = new GameState(player, dungeon, gridMap);
        MiniMap miniMap = new MiniMap(dungeon, gs);

        System.out.println("Escape : Exit | Z : Up | Q : Left | S : Down | D : Right");

        // Create the renderer and first print of it
        RendererUI rendererUI = new RendererUI(gridMap, miniMap, hud);
        System.out.println(rendererUI.toString());

        int state = gs.getState();
        while(state != 0) {
            // print new GameState
//            RendererUI.miniMapRender(miniMap);
//            RendererUI.hudRender(hud);

            // Wait for a key to be pressed and return its ASCII code
            int a = retrieveKey(sp);
            boolean acted = false;
            // Process Player Input
            switch ((char) a) {
                case 'Z':
                    acted = gs.movePlayer(0, -1);
                    //Tries to change the player's position, if something is blocking then the player's turn is not consumed.
                    break;
                case 'Q':
                    acted = gs.movePlayer(-1, 0);
                    break;
                case 'S':
                    acted = gs.movePlayer(0, 1);
                    break;
                case 'D':
                    acted = gs.movePlayer(1, 0);
                    break;
                case '\u001B': // escape
                    gs.exitGame();
                    break;
                default:
                    continue;
            }
            if (!acted) {
                state = gs.getState();
                Thread.sleep(100);
            } else {
                gs.isOnEntity();
                // If action is correct ok (GameState + input)
                // Else break

                // Monsters world interaction etc ...

                // Animation

                // Update GameState
                System.out.println("Escape : Exit | Z : Up | Q : Left | S : Down | D : Right\n");
                rendererUI.updateAll(gs.getGridMap(),hud,miniMap);
                System.out.println(rendererUI.toString());
                Thread.sleep(100);
                sp.reset();
                state = gs.getState();
            }
        }
        System.exit(0);
    }

    private int retrieveKey(ScanPanel sp) throws InterruptedException {
        int a = 0;
        while(a == 0) {
            a = sp.getKeyPressed();
            Thread.sleep(1);  // Without that, Java deletes the loop
        }
        return a;
    }

//    public static void main(String[] args) throws InterruptedException {
//        RogueLike rogueLike = new RogueLike();
//    }
    public static void main(String[] args) {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed);
        List<Room> test = dungeon.getRoomList();
        for (int i = 0; i < dungeon.getGraph().getGraph().size(); i++) {
            System.out.println(Arrays.toString(dungeon.getGraph().getGraph().get(i)) + " " + i);
        }
        for (Room room :
                test) {
//            System.out.println(room.getHeight());
//            System.out.println(room.getWidth());
//            System.out.println(room.getContents().length);
//            System.out.println(room.getContents()[0].length);
            System.out.println("num room : " + room.getRoomNum());
            for (int i = 0; i < room.getHeight(); i++) {

                for (int j = 0; j < room.getWidth(); j++) {
                    System.out.printf("%d ", room.getContents()[i][j]);
                }
                System.out.println("\n");
            }
            System.out.println("-------------------");
        }
    }
}