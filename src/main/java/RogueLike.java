import display.GridMap;
import display.HUD;
import display.RendererUI;
import entity.Player;
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
        MiniMap miniMap = new MiniMap(dungeon);
        HUD hud = new HUD(player);
        ScanPanel sp = new ScanPanel();
        GameState gs = new GameState(player, dungeon, miniMap, gridMap);

        while(gs.getState() != 0) {
            // print new GameState
              RendererUI.roomRender(gridMap);
//            RendererUI.miniMapRender(miniMap);
//            RendererUI.hudRender(hud);

            // Wait for a key to be pressed and return its ASCII code
            int a = retrieveKey(sp);

            // Process Player Input
            switch ((char) a) {
                case 'Z':
                    gs.movePlayer(player, 0, -1, gridMap);
                    //Tries to change the player's position, if something is blocking then the player's turn is not consumed.
                    break;
                case 'Q':
                    gs.movePlayer(player, -1, 0, gridMap);
                    break;
                case 'S':
                    gs.movePlayer(player, 0, 1, gridMap);
                    break;
                case 'D':
                    gs.movePlayer(player, 1, 0, gridMap);
                    break;
                case '\u001B': // escape
                    gs.displayMinimap();
                    break;
                default:
                    continue;
            }
            // If action is correct ok (GameState + input)
            // Else break

            // Monsters world interaction etc ...

            // Animation

            // Update GameState
            Thread.sleep(2000);
            sp.reset();
        }

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