import display.GridMap;
import display.HUD;
import display.RendererUI;
import entity.Player;
import gameElement.Dungeon;
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
     * Create an instance of the game.
     */
    RogueLike() throws InterruptedException {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed);
        Player player = new Player(new Position(0,0),100, 100, "Hero", 1);
        GridMap gridMap = new GridMap(dungeon.getRoom(0));
        MiniMap miniMap = new MiniMap(dungeon);
        HUD hud = new HUD(player);
        ScanPanel sp = new ScanPanel();

        while(true) {
            // print new GameState
            //RendererUI.roomRender(gridMap);
            //RendererUI.miniMapRender(miniMap);
            //RendererUI.hudRender(hud);
            int a = 0;
            while(a == 0) {
                a = sp.getKeyPressed();
                Thread.sleep(1);
            }
            System.out.println("pressed : "+a);

            // Pause Scan input

            // Process Player Input
            /* switch case input
                {
                }
            */
            // If action is correct ok (GameState + input)
            // Else break

            // Monstres world interaction etc ...

            // Animation

            // Update GameState
            Thread.sleep(2000);
            sp.reset();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        RogueLike rogueLike = new RogueLike();
    }
}