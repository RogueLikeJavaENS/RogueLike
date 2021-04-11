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

import java.util.Scanner;

/**
 * This is the main class of the RogueLike Game.
 *
 * @author Antoine & Raphael
 */

public class RogueLike {
    private boolean acted;
    private boolean turned;
    private boolean monsterPlayed;
    private boolean modifiedMenu;
    private final Player player;
    private final HUD hud;
    private final ScanPanel sp;
    private final GameState gs;
    private final MiniMap miniMap;
    private final RendererUI rendererUI;

    /**
     * Creates an instance of the game.
     */
    RogueLike() throws InterruptedException {
        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed);
        Position initialPosition = dungeon.getRoomList().get(0).getCenter();
        player = new Player(initialPosition,100, 100, "Hero", 1);
        hud = new HUD(player);
        sp = new ScanPanel();
        gs = new GameState(player, dungeon);
        miniMap = new MiniMap(dungeon, gs);
        rendererUI = new RendererUI(gs, miniMap, hud);
        rendererUI.display();

        gameLoop();
        System.exit(0);
    }

    /**
     * Principal loop of the game.
     * While the state is not END, the loop continue.
     *
     * @throws InterruptedException
     */
    private void gameLoop() throws InterruptedException{
        while(gs.getState() != State.END) {

            acted = false;
            turned = false;
            modifiedMenu = false;
            monsterPlayed = false;

            switch(gs.getState()) { // take input according to the GameState.
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
            }
            if (!gs.getState().equals(State.END)) {
                if (!acted) { // The player didn't consumed his action
                    if(turned) {
                        rendererUI.updateGrid(gs.getGridMap(), hud);
                        rendererUI.display();
                    }
                    else if (modifiedMenu) {
                        rendererUI.display();
                    } else if (monsterPlayed) {
                        rendererUI.updateGrid(gs.getGridMap(), hud);
                        rendererUI.display();
                    }
                } else {
                    gs.isOnEntity();
                    rendererUI.updateAll(gs.getGridMap(),hud,miniMap);
                    rendererUI.display();
                }
                Thread.sleep(100);
                sp.reset();
            }
        }
    }

    /**
     * This methods represents the order in fight. it chooses if the player play or if a monster play.
     * If a monster play, it throws the action of it.
     *
     * @throws InterruptedException
     */
    private void doTurnOrder() throws InterruptedException {
        Fighting fight = gs.getFighting();
        LivingEntity entity = fight.getCurrentEntity();
        if (entity instanceof Player) {
            fightingStateInput();
        } else {
            entity.doAction(gs);
            monsterStateInput();
        }
        if (acted || monsterPlayed) { // if a monster or the Player played, go to the next turn.
            fight.next();
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a NORMAL state.
     *
     * @throws InterruptedException
     */
    private void normalStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        switch ((char) a) { // Process the pressed key bu the player.
            case 'Z':
                turned = hadTurned(player, Direction.NORTH);
                player.setDirection(Direction.NORTH);
                acted = gs.movePlayer(0, -1); //
                // Tries to modify the player's position,
                // if something is blocking then the player's turned is not consumed.
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
                exitStateInput();
                break;
            default:
                break;
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a MAP state.
     *
     * @throws InterruptedException
     */
    private void minimapStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        switch((char) a) {
            case 'M':
                gs.setState(State.NORMAL);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.INVENTORY);
                modifiedMenu = true;
                break;
            case '\u001B': // escape
                exitStateInput();
                break;
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a FIGHT state.
     *
     * @throws InterruptedException
     */
    private void fightingStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
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
                exitStateInput();
                break;
            default:
                break;
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a FIGHT state during a monster turn.
     *
     * @throws InterruptedException
     */
    private void monsterStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        monsterPlayed = true;
        if ((char) a == '\u001B') { // escape
            exitStateInput();
        }
    }

    /**
     * Represents all of the actions that can be done by the player during an INVENTORY state.
     *
     * @throws InterruptedException
     */
    private void inventoryStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        switch((char) a) {
            case 'M':
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.NORMAL);
                modifiedMenu = true;
                break;
            case '\u001B': // escape
                exitStateInput();
                break;
        }
    }

    /**
     * Ask the player confirmation of to exit the game.
     *
     * @throws InterruptedException
     */
    private void exitStateInput() throws InterruptedException {
        System.out.println(" \t############################################################\n" +
                            "\t#  Do you want to exit the game ? Everything will be lost. #\n" +
                            "\t############################################################\n\n" +
                            "Type (y) to confirm, others to abandon and return to the game : \n");
        int a = retrieveKey(sp);
        if ((char) a == 'Y') {
            rendererUI.clearConsole();
            gs.exitGame();
        }
        acted = false;
        turned = false;
        modifiedMenu = true;
        monsterPlayed = false;
    }

    /**
     * Check if the player had turned. Use to refresh the GridMap to print the new direction.
     * @param player current player
     * @param dir current direction.
     * @return a boolean if the player turned or not.
     */
    private boolean hadTurned(Player player, Direction dir) {
        return !player.getDirection().equals(dir);
    }

    /**
     * Get the last key pressed by the player.
     * @param sp The ScanPanel used to Listen the keyboard.
     * @return int key.
     * @throws InterruptedException
     */
    private int retrieveKey(ScanPanel sp) throws InterruptedException {
        int a = 0;
        while(a == 0) {
            a = sp.getKeyPressed();
            Thread.sleep(1);  // Without that, Java deletes the loop
        }
        return a;
    }

    public static void main(String[] args) throws InterruptedException {
        new RogueLike();
    }
}