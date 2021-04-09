import display.HUD;
import display.RendererUI;
import entity.living.LivingEntity;
import entity.living.Player;
import gameElement.Dungeon;
import gameElement.Fighting;
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
    private boolean acted;
    private boolean turned;
    private boolean modifiedMenu;
    private boolean monsterTurned;
    private Seed seed;
    private Dungeon dungeon;
    private Player player;
    private HUD hud;
    private ScanPanel sp;
    private GameState gs;
    private MiniMap miniMap;
    private RendererUI rendererUI;

    /**
     * Creates an instance of the game.
     */
    RogueLike() throws InterruptedException {
        seed = new Seed();
        dungeon = DungeonStructure.createDungeon(seed);
        Position initialPosition = dungeon.getRoomList().get(0).getCenter();
        player = new Player(initialPosition,100, 100, "Hero", 1);
        hud = new HUD(player);
        sp = new ScanPanel();
        gs = new GameState(player, dungeon);
        miniMap = new MiniMap(dungeon, gs);

        // Create the renderer and first print of it

        rendererUI = new RendererUI(gs, miniMap, hud);
        rendererUI.display();

        while(gs.getState() != State.END) {

            acted = false;
            turned = false;
            modifiedMenu = false;
            monsterTurned = false;

            switch(gs.getState()) {
                case NORMAL:    //default state
                    normalStateInput();
                    break;

                case FIGHT:
                    doTurnOrder();
                    break;

                case MAP:
                    minimapStateInput();
                    break;

                case INVENTORY:
                    inventoryStateInput();
                    break;

                case MONSTER:
                    monsterStateInput();
            }

            if (!acted) {
                if(turned) {
                    rendererUI.updateGrid(gs.getGridMap(), hud);
                    rendererUI.display();
                }
                else if (modifiedMenu) {
                    rendererUI.updateAll(gs.getGridMap(), hud,miniMap);
                    rendererUI.display();
                }
                else if (monsterTurned)
                Thread.sleep(100);
                sp.reset();
            } else {
                gs.isOnEntity();

                rendererUI.updateAll(gs.getGridMap(),hud,miniMap);
                rendererUI.display();
                Thread.sleep(100);
                sp.reset();
            }
        }
        System.exit(0);
    }

    public void doTurnOrder() throws InterruptedException {
        Fighting fight = gs.getFighting();
        if (fight.getBufferEntity().isEmpty()) {
            fight.refillBuffer();
        }
        LivingEntity entity = fight.getCurrentEntity();
        if (entity instanceof Player) {
            sp.reset();
            fightingStateInput();
        } else {
            entity.doAction(gs);
            rendererUI.updateAll(gs.getGridMap(),hud,miniMap);
            rendererUI.display();
            sp.reset();
            Thread.sleep(2000);
        }
        fight.next();
    }

    private void normalStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        acted = false;
        turned = false;
        modifiedMenu = false;
        // Process Player Input
        switch ((char) a) {
            case 'Z':
                turned = hadTurned(player, Direction.NORTH);
                player.setDirection(Direction.NORTH);
                acted = gs.movePlayer(0, -1);
                //Tries to change the player's position, if something is blocking then the player's turned is not consumed.
                break;
            case 'Q':
                turned = hadTurned(player, Direction.WEST);
                player.setDirection(Direction.WEST);
                acted = gs.movePlayer(-1, 0);
                break;
            case 'S':
                turned = hadTurned(player, Direction.SOUTH);
                player.setDirection(Direction.SOUTH);
                acted = gs.movePlayer(0, 1);
                break;
            case 'D':
                turned = hadTurned(player, Direction.EAST);
                player.setDirection(Direction.EAST);
                acted = gs.movePlayer(1, 0);
                break;
            case 'M':
                miniMap.updateMap();
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.INVENTORY);
                modifiedMenu = true;
                break;
            case '\u001B': // escape
                gs.exitGame();
                break;
            default:
                break;
        }
    }

    private void minimapStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        acted = false;
        turned = false;
        modifiedMenu = false;

        switch((char) a) {
            case 'M':
                gs.setState(State.NORMAL);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.INVENTORY);
                modifiedMenu = true;
                break;
        }
    }

    private void fightingStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        acted = false;
        turned = false;
        modifiedMenu = false;
        // Process Player Input
        switch ((char) a) {
            case 'Z':
                turned = hadTurned(player, Direction.NORTH);
                player.setDirection(Direction.NORTH);
                acted = gs.movePlayer(0, -1);
                //Tries to change the player's position, if something is blocking then the player's turned is not consumed.
                break;
            case 'Q':
                turned = hadTurned(player, Direction.WEST);
                player.setDirection(Direction.WEST);
                acted = gs.movePlayer(-1, 0);
                break;
            case 'S':
                turned = hadTurned(player, Direction.SOUTH);
                player.setDirection(Direction.SOUTH);
                acted = gs.movePlayer(0, 1);
                break;
            case 'D':
                turned = hadTurned(player, Direction.EAST);
                player.setDirection(Direction.EAST);
                acted = gs.movePlayer(1, 0);
                break;
            case 'M':
                miniMap.updateMap();
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.INVENTORY);
                modifiedMenu = true;
                break;
            case '\u001B': // escape
                gs.exitGame();
                break;
            default:
                break;
        }
    }

    private void monsterStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        acted = false;
        turned = false;
        modifiedMenu = false;
        gs.setState(State.FIGHT);
    }

    private void inventoryStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        acted = false;
        turned = false;
        modifiedMenu = false;

        switch((char) a) {
            case 'M':
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.NORMAL);
                modifiedMenu = true;
                break;
        }
    }

    private boolean hadTurned(Player player, Direction dir) {
        return !player.getDirection().equals(dir);
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