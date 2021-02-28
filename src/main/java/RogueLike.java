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
    RogueLike() {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed);
        Player player = new Player(new Position(0,0),100, 100, "Hero", 1);
        GridMap gridMap = new GridMap(dungeon.getRoom(0));
        MiniMap miniMap = new MiniMap(dungeon);
        HUD hud = new HUD(player);

        RendererUI.roomRender(gridMap);
        RendererUI.miniMapRender(miniMap);
        RendererUI.hudRender(hud);
    }

    public static void main(String[] args) {
        RogueLike rogueLike = new RogueLike();
    }
}