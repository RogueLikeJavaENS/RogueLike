import display.HUD;
import display.RendererUI;
import entity.living.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.DungeonStructure;
import gameElement.MiniMap;
import generation.Seed;
import utils.Direction;
import utils.Position;
import utils.ScanPanel;
import utils.State;

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
        HUD hud = new HUD(player);
        ScanPanel sp = new ScanPanel();
        GameState gs = new GameState(player, dungeon);
        MiniMap miniMap = new MiniMap(dungeon, gs);

        // Create the renderer and first print of it

        RendererUI rendererUI = new RendererUI(gs, miniMap, hud);
        rendererUI.display();

        State state = gs.getState();
        while(state != State.LOOSE) {


            // Wait for a key to be pressed and return its ASCII code
            int a = retrieveKey(sp);
            System.out.printf("%d",a);
            boolean acted = false;
            boolean turned = false;
            boolean modifiedMenu = false;
            // Process Player Input
            switch ((char) a) {
                case 'Z':
                    if (gs.getState() == State.FIGHT || gs.getState() == State.NORMAL){
                        turned = true;
                        player.setDirection(Direction.NORTH);
                        acted = gs.movePlayer(0, -1);
                    }
                    //Tries to change the player's position, if something is blocking then the player's turned is not consumed.
                    break;
                case 'Q':
                    if (gs.getState() == State.FIGHT || gs.getState() == State.NORMAL) {
                        turned = true;
                        player.setDirection(Direction.WEST);
                        acted = gs.movePlayer(-1, 0);
                    }
                    break;
                case 'S':
                    if (gs.getState() == State.FIGHT || gs.getState() == State.NORMAL) {
                        turned = true;
                        player.setDirection(Direction.SOUTH);
                        acted = gs.movePlayer(0, 1);

                    }
                    break;
                case 'D':
                    if (gs.getState() == State.FIGHT || gs.getState() == State.NORMAL) {
                        turned = true;
                        player.setDirection(Direction.EAST);
                        acted = gs.movePlayer(1, 0);
                    }
                    break;
                case '\u001B': // escape
                    gs.exitGame();
                    break;
                case 'M':
                    if (gs.getState() == State.MAP){ // quit the minimap and return to the game
                        gs.setState(State.NORMAL);
                        modifiedMenu = true;
                        break;
                    }
                    else { // print the minimap
                        miniMap.updateMap();
                        gs.setState(State.MAP);
                        modifiedMenu = true;
                        break;
                    }
                case 'I':
                    if (gs.getState() == State.INVENTORY){ // quit the inventory and return to the game
                        gs.setState(State.NORMAL);
                        modifiedMenu = true;
                        break;
                    }
                    else { // print the inventory
                        gs.setState(State.INVENTORY);
                        modifiedMenu = true;
                        break;
                    }
                default:
                    continue;
            }
            if (!acted) {
                state = gs.getState();
                if(turned) {
                    rendererUI.updateGrid(gs.getGridMap(), hud);
                    rendererUI.display();
                }
                if(modifiedMenu){
                    rendererUI.updateAll(gs.getGridMap(), hud,miniMap);
                    rendererUI.display();
                }
                Thread.sleep(100);
            } else {
                gs.isOnEntity();
                // If action is correct ok (GameState + input)
                // Else break

                // Monsters world interaction etc ...

                // Animation

                // Update GameState
                rendererUI.updateAll(gs.getGridMap(),hud,miniMap);
                rendererUI.display();
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

    public static void main(String[] args) throws InterruptedException {
        RogueLike rogueLike = new RogueLike();
    }
}