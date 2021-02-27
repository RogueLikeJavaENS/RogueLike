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
        // this.player = new Player("Hero", 1, 100, 100);
        GridMap gridMap = new GridMap(dungeon.getRoom(0));
        MiniMap miniMap = new MiniMap(dungeon);

        RendererUI.roomRender(gridMap);
        RendererUI.miniMapRender(miniMap);
    }

    public static void main(String[] args) {
        RogueLike rogueLike = new RogueLike();
    }
}
