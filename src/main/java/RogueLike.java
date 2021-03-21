import display.GridMap;
import display.HUD;
import display.RendererUI;
import entity.living.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import gameElement.MiniMap;
import generation.Seed;
import utils.Position;

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
        Player player = new Player(new Position(0,0),100, 100, "Hero", 1);
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
        while(gs.getState() != 0) {
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
                continue;
            }
            // If action is correct ok (GameState + input)
            // Else break

            // Monsters world interaction etc ...

            // Animation

                // Update GameState
                System.out.println("Escape : Exit | Z : Up | Q : Left | S : Down | D : Right\n");
                rendererUI.updateAll(gridMap,hud,miniMap);
                System.out.println(rendererUI.toString());
                Thread.sleep(100);
                sp.reset();
                state = gs.getState();
            }
        }

        System.exit(0);
    }

    public static void main(String[] args) throws InterruptedException {
        RogueLike rogueLike = new RogueLike();
    }

    private int retrieveKey(ScanPanel sp) throws InterruptedException {
        int a = 0;
        while(a == 0) {
            a = sp.getKeyPressed();
            Thread.sleep(1);  // Without that, Java deletes the loop
        }
        return a;
    }

}