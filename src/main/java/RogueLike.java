import display.HUD;
import display.RendererUI;
import entity.Entity;
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
    private final Seed seed;
    private final Dungeon dungeon;
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

            resetBools();

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
            }

            if (!acted) {
                if(turned) {
                    rendererUI.updateGrid(gs.getGridMap());
                    rendererUI.display();
                }
                if(modifiedMenu) {
                    rendererUI.updateAll(gs.getGridMap(), miniMap, hud);
                    rendererUI.display();
                }
                Thread.sleep(100);
            } else {
                gs.isOnEntity();

                rendererUI.updateAll(gs.getGridMap(), miniMap, hud);
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
            fightingStateInput();
        } else {
            entity.doAction(gs);
            Thread.sleep(1000);
        }
        fight.next();
    }

    private void normalStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        resetBools();
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
            case 'H':
                gs.setHelp(!gs.getHelp());
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
        resetBools();

        switch((char) a) {
            case 'M':
                gs.isFighting();
                modifiedMenu = true;
                break;
            case 'I':
                gs.setState(State.INVENTORY);
                modifiedMenu = true;
                break;
            case 'H':
                gs.setHelp(!gs.getHelp());
                modifiedMenu = true;
                break;
        }
    }

    private void fightingStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        resetBools();
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
            case 'H':
                gs.setHelp(!gs.getHelp());
                modifiedMenu = true;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                int ASCII_CODE_FOR_ZERO = 48;
                int playerInput = a%ASCII_CODE_FOR_ZERO;
                if (playerInput <= player.getSpells().size()){
                    hud.spellSelectionString(playerInput);
                    updateHUDDisplay();
                    skillSelectionInput(playerInput);
                } else {
                    fightingStateInput();
                }
                break;
            case '0':
                if (player.getSpells().size() == 10) { //0 is after 9 on the keyboard, so it stands for 10
                    hud.spellSelectionString(10);
                    updateHUDDisplay();
                    skillSelectionInput(10);
                } else {
                    fightingStateInput();
                }
                break;
            case '\u001B': // escape
                gs.exitGame();
                break;
            default:
                fightingStateInput(); //break; statement was only skipping the player's turn
        }
    }

    private void skillSelectionInput(int firstInput) throws InterruptedException {
        int ASCII_CODE_FOR_ZERO = 48;
        int a = retrieveKey(sp);
        resetBools();

        int secondInput = a%ASCII_CODE_FOR_ZERO;

        switch ((char) a) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if (secondInput == firstInput) {
                    hud.spellListString(); //resets the spellBar (removes the highlightning)
                    System.out.println(player.getName() + " uses " + player.getSpells().get(secondInput-1).toString()); //dummy string to simulate the use of the spell
                } else if (secondInput <= player.getSpells().size()) {
                    hud.spellSelectionString(secondInput); //updates the spellBar (moves the highlightning)
                    updateHUDDisplay();
                    skillSelectionInput(secondInput);
                } else {
                    hud.spellListString();
                    updateHUDDisplay();
                    skillSelectionInput(-1);
                }
                break;
            case '0':
                if (firstInput == 10) { //0 is after 9 on the keyboard, so it stands for 10
                    hud.spellListString(); //resets the spellListString (removes the highlightning)
                    System.out.println(player.getName() + " uses " + player.getSpells().get(firstInput-1).toString());
                } else if (secondInput <= player.getSpells().size()) {
                    hud.spellSelectionString(10); //updates the spellBar (moves the highlightning)
                    updateHUDDisplay();
                    skillSelectionInput(10);
                } else {
                    hud.spellListString();
                    updateHUDDisplay();
                    skillSelectionInput(-1);
                }
                break;
            case '\u001B': // escape
                hud.spellListString(); //resets the spellListString (removes the highlightning) and gets out of the spell selection
                updateHUDDisplay();
                fightingStateInput(); //gets back to fighting inputs
                break;
            default:
                skillSelectionInput(-1);
        }
    }

    private void inventoryStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        resetBools();

        switch((char) a) {
            case 'M':
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.isFighting();
                modifiedMenu = true;
                break;
            case 'H':
                gs.setHelp(!gs.getHelp());
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

    private void updateHUDDisplay(){
        rendererUI.updateHUD(hud);
        rendererUI.display();
    }

    private void resetBools() {
        acted = false;
        turned = false;
        modifiedMenu = false;
    }

    public static void main(String[] args) throws InterruptedException {
        RogueLike rogueLike = new RogueLike();
    }
}