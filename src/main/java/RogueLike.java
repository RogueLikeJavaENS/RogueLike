/**
 * This is the main class of the RogueLike Game.
 *
 * @author Antoine
 */

public class RogueLike {
    private Seed seed;
    private Dungeon dungeon;
    private GridMap gridMap;
    private Player player;

    /**
     * Create an instance of the game.
     */
    RogueLike() {
        this.seed = new Seed();
        this.dungeon = new Dungeon(seed);
        this.player = new Player("Hero", 1, 100, 100);
        this.gridMap = new GridMap();

        while(true) {

        }
    }

    public static void main(String[] args) {
        RogueLike rogueLike = new RogueLike();
    }
}
