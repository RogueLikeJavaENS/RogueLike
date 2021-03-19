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

        while(player.getCurrentHP() > 0) {
            // print new GameState
            RendererUI.roomRender(gridMap);
            RendererUI.miniMapRender(miniMap);
            RendererUI.hudRender(hud);

            boolean didAction = false; //boolean that states if the player did something or not.
            while (!didAction) {

                //Wait for a key to be pressed and return its ASCII code
                int a = retrieveKey(sp);

                // Process Player Input
                switch ((char) a) {
                    case '\t':
                        //TODO display the list of the valid inputs to help the player
                        RendererUI.miniMapRender(miniMap);
                        RendererUI.hudRender(hud);
                        //Does not consume the player's turn.
                        break;
                    case 'z':
                        //Tries to change the player's position, if something is blocking then the player's turn is not consumed.
                        didAction = movePlayer(player, 1, 0, gridMap);
                        break;
                    case 'q':
                        didAction = movePlayer(player, 0, -1, gridMap);
                        break;
                    case 's':
                        didAction = movePlayer(player, -1, 0, gridMap);
                        break;
                    case 'd':
                        didAction = movePlayer(player, 0, 1, gridMap);
                        break;
                }
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
            Thread.sleep(1); //Without that, Java deletes the loop
        }
        return a;
    }

    /**
     * Checks if the player can access the tile at his position + x & y, and changes its position if so.
     *
     * @param player Player whose move will be checked and possibly executed.
     * @param x Abscissa of the tile to check, using the player's position as a base.
     * @param y Ordinate of the tile to check, using the player's position as a base.
     * @param gridMap GridMap containing the player.
     * @return Returns true if the player's position was updated successfully, false otherwise.
     *
     * @author Raphael
     */
    private boolean movePlayer(Player player, int x, int y, GridMap gridMap) {
        Position newPosition = new Position (player.getPosition().getAbs() + x, player.getPosition().getOrd() + y);
        if (gridMap.getTileAt(newPosition.getAbs(), newPosition.getOrd()).isAccessible()) {
            player.setPosition(newPosition);
            return true;
        }
        return false;
    }
}