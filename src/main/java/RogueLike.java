import display.HUD;
import display.RendererUI;
import entity.living.LivingEntity;
import entity.living.player.Player;
import entity.object.EmptyBottle;
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
        gs = new GameState(player, dungeon);
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

            switch(gs.getState()) { // take input according to the GameState.
                case NORMAL:    //default state
                    normalStateInput();
                    break;

                case FIGHT:
                    gs.updateRange();
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
                if (!acted) { // The player didn't consume his action
                    if(turned) {
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
            fightingStateInput();
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
     * Represents all of the actions that can be done by the player during a NORMAL state.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void normalStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        Potion potionToDelete = new EmptyBottle(null);
        switch ((char) a) { // Process the pressed key bu the player.
            case 'Z':
                turned = hasTurned(player, Direction.NORTH);
                player.setDirection(Direction.NORTH);
                if (!positionLocked) {
                    acted = gs.movePlayer(0, -1);
                }
                // Tries to modify the player's position,
                // if something is blocking then the player's turned is not consumed.
                break;
            case 'Q':
                turned = hasTurned(player, Direction.WEST);
                player.setDirection(Direction.WEST);
                if (!positionLocked) {
                    acted = gs.movePlayer(-1, 0);
                }
                break;
            case 'S':
                turned = hasTurned(player, Direction.SOUTH);
                player.setDirection(Direction.SOUTH);
                if (!positionLocked) {
                    acted = gs.movePlayer(0, 1);
                }
                break;
            case 'D':
                turned = hasTurned(player, Direction.EAST);
                player.setDirection(Direction.EAST);
                if (!positionLocked) {
                    acted = gs.movePlayer(1, 0);
                }
                break;
            case 'M':
                gs.getMiniMap().updateMap();
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
            case 'V':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==0){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
                break;
            case 'B':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==1){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
                break;
            case 'N':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==2){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
                break;
            case '\u0014':  //caps lock
                positionLocked = !positionLocked;
                break;
            case '\u001B': // escape
                exitStateInput();
                break;
            case 'E':
                acted = gs.interact();
                break;
            default:
                break;
        }
    }

    /**
     * Represents all of the actions that can be done by the player during a MAP state.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void minimapStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        switch((char) a) {
            case 'M':
                gs.isThereMonsters();
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
                exitStateInput();
                break;

        }
    }

    /**
     * Represents all of the actions that can be done by the player during a FIGHT state.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void fightingStateInput() throws InterruptedException {
        Potion potionToDelete = new EmptyBottle(null);
        // Process Player Input
        int a = retrieveKey(sp);

        switch ((char) a) {
            case 'A':
                acted = gs.useSpell(); //true if the spell was casted, false if not enough pm
                modifiedMenu = true;
                hud.spellListString(); //remove the highlightning of the selected spell
                rendererUI.updateHUD(hud);
                break;
            case 'Z':
                turned = hasTurned(player, Direction.NORTH);
                player.setDirection(Direction.NORTH);
                if (!positionLocked) {
                    acted = gs.movePlayer(0, -1);
                }
                gs.updateRange();
                //Tries to change the player's position, if something is blocking then the player's turned is not consumed.
                break;
            case 'Q':
                turned = hasTurned(player, Direction.WEST);
                player.setDirection(Direction.WEST);
                if (!positionLocked) {
                    acted = gs.movePlayer(-1, 0);
                }
                gs.updateRange();
                break;
            case 'S':
                turned = hasTurned(player, Direction.SOUTH);
                player.setDirection(Direction.SOUTH);
                if (!positionLocked) {
                    acted = gs.movePlayer(0, 1);
                }
                gs.updateRange();
                break;
            case 'D':
                turned = hasTurned(player, Direction.EAST);
                player.setDirection(Direction.EAST);
                if (!positionLocked) {
                    acted = gs.movePlayer(1, 0);
                }
                gs.updateRange();
                break;
            case 'W': // wait
                acted = true;
                break;
            case 'M':
                gs.getMiniMap().updateMap();
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
            case 'V':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==0){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
                break;
            case 'B':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==1){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
                break;
            case 'N':
                modifiedMenu = true;
                for (Potion potion:
                        player.getPotionBelt()) {
                    if (potion.getPotionType()==2){
                        potionToDelete=potion;
                        break;
                    }
                }
                acted=potionToDelete.usePotion(gs);
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
                int playerInput = a % ASCII_CODE_FOR_ZERO;
                if (playerInput <= player.getSpells().size()) {
                    hud.spellSelectionString(playerInput);
                    updateHUDDisplay();
                    gs.updateRange();
                    turned = true;
                }
                break;
            case '0':
                if (player.getSpells().size() == 10) { //0 is after 9 on the keyboard, so it stands for 10
                    hud.spellSelectionString(10);
                    updateHUDDisplay();
                    gs.updateRange();
                    turned = true;
                }
                break;
            case '\u0014':  //caps lock
                positionLocked = !positionLocked;
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
     * Represents all of the actions that can be done by the player during an INVENTORY state.
     *
     * @throws InterruptedException Something went wrong.
     */
    private void inventoryStateInput() throws InterruptedException {
        int a = retrieveKey(sp);
        switch((char) a) {
            case 'M':
                gs.setState(State.MAP);
                modifiedMenu = true;
                break;
            case 'I':
                gs.isThereMonsters();
                modifiedMenu = true;
                break;
            case 'H':
                gs.setHelp(!gs.getHelp());
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

    private void updateHUDDisplay(){
        rendererUI.updateHUD(hud);
        rendererUI.display();
    }

    public static void main(String[] args) throws InterruptedException {
        new RogueLike();
    }
}