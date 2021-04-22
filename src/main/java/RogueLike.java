import display.HUD;
import display.RendererUI;
import entity.living.LivingEntity;
import entity.living.player.Player;
import entity.object.EmptyBottle;
import items.potion.Potion;
import entity.object.potion.EmptyBottle;
import entity.object.potion.Potion;
import gameElement.Dungeon;
import gameElement.Fighting;
import gameElement.GameState;
import generation.DungeonStructure;
import generation.Seed;
import utils.Direction;
import utils.Position;
import utils.ScanPanel;
import utils.State;

import java.awt.event.KeyEvent;

/**
 * This is the main class of the RogueLike Game.
 *
 * @author Antoine and Raphael
 */

public class RogueLike {
    private boolean acted;
    private boolean turned;
    private boolean monsterPlayed;
    private boolean modifiedMenu;
    private boolean positionLocked = false;
    private final Player player;
    private final HUD hud;
    private final ScanPanel sp;
    private final GameState gs;
    private final RendererUI rendererUI;

    /**
     * Creates an instance of the game.
     */
    RogueLike() throws InterruptedException {

        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1);
        Position initialPosition = dungeon.getRoom(0).getCenter();
        player = new Player(initialPosition,100, 100, "Hero", 1);
        hud = new HUD(player);
        sp = new ScanPanel();
        gs = new GameState(player, dungeon, hud);
        rendererUI = new RendererUI(gs, hud);
        rendererUI.display();

        gameLoop(); // until state equals WIN or LOSE or END
        rendererUI.clearConsole();
        if (gs.getState() == State.WIN) {
            rendererUI.winEnd();
        }
        else if (gs.getState() == State.LOSE) {
            rendererUI.loseEnd();
        }

        System.exit(0);
    }

    /**
     * Principal loop of the game.
     * While the state is not END, the loop continue.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void gameLoop() throws InterruptedException{
        while(gs.getState() != State.WIN && gs.getState() != State.LOSE && gs.getState() != State.END) {
            gs.getDescriptor().clearDescriptor();
            acted = false;
            turned = false;
            modifiedMenu = false;
            monsterPlayed = false;

            if (gs.getState() == State.FIGHT) {
                gs.updateRange();
                doTurnOrder();
            } else {
                playerInput();
            }

            if (!gs.getState().equals(State.END)) {
                if (!acted) { // The player didn't consume his action
                    if(turned) {
                        if (gs.getState() == State.FIGHT) {
                            gs.updateRange();
                        }
                        rendererUI.updateGrid(gs.getGridMap());
                        rendererUI.display();
                    }
                    else if (modifiedMenu) {
                        rendererUI.display();
                    } else if (monsterPlayed) {
                        rendererUI.updateGrid(gs.getGridMap());
                        rendererUI.display();
                    }
                } else {
                    gs.isOnEntity();
                    rendererUI.updateAll(gs, hud);
                    rendererUI.display();
                }
                sp.reset();
            }
        }
    }

    /**
     * This methods represents the order in fight. it chooses if the player play or if a monster play.
     * If a monster play, it throws the action of it.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void doTurnOrder() throws InterruptedException {
        Fighting fight = gs.getFighting();
        LivingEntity entity = fight.getCurrentEntity();
        if (entity instanceof Player) {
            playerInput();
        } else {
            entity.doAction(gs);
            monsterStateInput();
        }
        if (acted || monsterPlayed) { // if a monster or the Player played, go to the next turn.
            fight.next();
        }
        gs.isPlayerAlive();
    }

    /**
     * Get the player input and process it according to the game state.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void playerInput() throws InterruptedException {
        int keyCode = retrieveKey(sp);
        State state = gs.getState();

        Potion potionToDelete = new EmptyBottle(null);
        switch (keyCode) { // Process the pressed key bu the player.
            case KeyEvent.VK_Z:
                if (state == State.NORMAL || state == State.FIGHT) {
                    turned = hasTurned(player, Direction.NORTH);
                    player.setDirection(Direction.NORTH);
                    if (!positionLocked) {
                        acted = gs.movePlayer(0, -1);
                    }
                    // Tries to modify the player's position,
                    // if something is blocking then the player's turned is not consumed.
                }
                break;
            case KeyEvent.VK_Q:
                if (state == State.NORMAL || state == State.FIGHT) {
                    turned = hasTurned(player, Direction.WEST);
                    player.setDirection(Direction.WEST);
                    if (!positionLocked) {
                        acted = gs.movePlayer(-1, 0);
                    }
                }
                break;
            case KeyEvent.VK_S:
                if (state == State.NORMAL || state == State.FIGHT) {
                    turned = hasTurned(player, Direction.SOUTH);
                    player.setDirection(Direction.SOUTH);
                    if (!positionLocked) {
                        acted = gs.movePlayer(0, 1);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (state == State.NORMAL || state == State.FIGHT) {
                    turned = hasTurned(player, Direction.EAST);
                    player.setDirection(Direction.EAST);
                    if (!positionLocked) {
                        acted = gs.movePlayer(1, 0);
                    }
                }
                break;
            case KeyEvent.VK_W:
                if (state == State.FIGHT) {
                    acted = true;
                }
                break;
            case KeyEvent.VK_A:
                if (state == State.FIGHT) {
                    acted = gs.useSpell(); //true if the spell was casted, false if not enough pm
                    modifiedMenu = true;
                    hud.spellListString(); //remove the highlightning of the selected spell
                    rendererUI.updateHUD(hud);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (state == State.FIGHT) {
                    int indexSpell =  player.getSpells().indexOf(player.getSelectedSpell());
                    int newIndexSpell = (indexSpell+1) % player.getSpells().size();
                    player.setSelectedSpell(player.getSpells().get(newIndexSpell));
                    hud.spellSelectionString(newIndexSpell);
                    gs.updateRange();
                    rendererUI.updateHUD(hud);
                    turned = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (state == State.FIGHT) {
                    int indexSpell = player.getSpells().indexOf(player.getSelectedSpell());
                    int nbSpells = player.getSpells().size();
                    int newIndexSpell = (indexSpell+nbSpells-1) % nbSpells;
                    player.setSelectedSpell(player.getSpells().get(newIndexSpell));
                    hud.spellSelectionString(newIndexSpell);
                    gs.updateRange();
                    rendererUI.updateHUD(hud);
                    turned = true;
                }
                break;
            case KeyEvent.VK_M:
                if (state == State.MAP) {
                    gs.isThereMonsters();
                } else {
                    gs.setState(State.MAP);
                    gs.getMiniMap().updateMap();
                }
                modifiedMenu = true;
                break;
            case KeyEvent.VK_I:
                if (state == State.INVENTORY) {
                    gs.isThereMonsters();
                } else {
                    gs.setState(State.INVENTORY);
                }
                modifiedMenu = true;
                break;
            case KeyEvent.VK_H:
                gs.setHelp(!gs.getHelp());
                modifiedMenu = true;
                break;
            case KeyEvent.VK_V:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    for (Potion potion:
                            player.getPotionBelt()) {
                        if (potion.getPotionType()==0){
                            potionToDelete=potion;
                            break;
                        }
                    }
                    acted=potionToDelete.usePotion(gs);
                }
                break;
            case KeyEvent.VK_B:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    for (Potion potion:
                            player.getPotionBelt()) {
                        if (potion.getPotionType()==1){
                            potionToDelete=potion;
                            break;
                        }
                    }
                    acted=potionToDelete.usePotion(gs);
                }
                break;
            case KeyEvent.VK_N:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    for (Potion potion:
                            player.getPotionBelt()) {
                        if (potion.getPotionType()==2){
                            potionToDelete=potion;
                            break;
                        }
                    }
                    acted=potionToDelete.usePotion(gs);
                }
                break;
            case KeyEvent.VK_CAPS_LOCK:
                if (state == State.NORMAL || state == State.FIGHT) {
                    positionLocked = !positionLocked;
                }
                break;
            case KeyEvent.VK_ESCAPE: // escape
                exitStateInput();
                break;
            case KeyEvent.VK_E:
                if (state == State.NORMAL || state == State.FIGHT) {
                    acted = gs.interact();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a FIGHT state during a monster turn.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void monsterStateInput() throws InterruptedException {
        System.out.println("Monsters' turn, press any key ...");
        int a = retrieveKey(sp);
        monsterPlayed = true;
        if ((char) a == '\u001B') { // escape
            exitStateInput();
        }
    }

    /**
     * Ask the player confirmation of to exit the game.
     *
     * @throws InterruptedException Something went wrong.
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
    private boolean hasTurned(Player player, Direction dir) {
        return !player.getDirection().equals(dir);
    }

    /**
     * Get the last key pressed by the player.
     * @param sp The ScanPanel used to Listen the keyboard.
     * @return int key.
     * @throws InterruptedException Something went wrong.
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