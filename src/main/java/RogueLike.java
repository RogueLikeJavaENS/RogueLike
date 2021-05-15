import com.diogonunes.jcolor.Attribute;
import display.HUD;
import display.RendererUI;
import game.elements.Dungeon;
import game.elements.Fighting;
import game.elements.GameState;
import game.entity.living.LivingEntity;
import game.entity.living.player.Player;
import game.menu.InGameMenu;
import game.entity.living.inventory.OpenInventory;
import game.menu.StartMenu;
import game.stuff.item.ItemType;
import game.stuff.item.keys.FloorKey;
import game.stuff.item.keys.GoldKey;
import game.stuff.item.potions.Elixir;
import game.stuff.item.potions.PotionHealth;
import game.stuff.item.potions.XpBottle;
import generation.*;
import utils.*;
import static com.diogonunes.jcolor.Ansi.colorize;
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
    private final ScanPanel sp;
    private final GameState gs;
    private final RendererUI rendererUI;

    /**
     * Creates an instance of the game.
     */
    RogueLike() throws InterruptedException {
        MusicStuff musicStuff = new MusicStuff();
        musicStuff.playNormalMusic();
        sp = new ScanPanel();
        StartMenu start = new StartMenu(sp);

        Seed seed = new Seed();
        Dungeon dungeon = DungeonStructure.createDungeon(seed, 1, start.getClasse());
        Position initialPosition = dungeon.getRoom(dungeon.getDungeonSize()-3).getCenter();
        Player player = new Player(initialPosition, start.getName(), start.getClasse());
        gs = new GameState(player, dungeon, new HUD(player), sp, musicStuff);
        rendererUI = new RendererUI(gs);
        rendererUI.display();

        player.getInventory().addItem(new FloorKey());
        player.getInventory().addItem(new GoldKey());
        player.getInventory().addItem(new Elixir());
        player.getInventory().addItem(new Elixir());
        player.getInventory().addItem(new XpBottle());
        player.getInventory().addItem(new XpBottle());
        player.getInventory().addItem(new PotionHealth());
        player.getInventory().addItem(new PotionHealth());
        player.getInventory().addItem(new PotionHealth());


        gameLoop(); // until state equals WIN or LOSE or END

        RendererUI.clearConsole();
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
                    rendererUI.updateAll(gs);
                    rendererUI.display();
                }
                sp.reset();
            }
            gs.isPlayerAlive();
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
            inactiveStateInput("Monster's turn, press any key ...");
            gs.isMonsterOnEntity(entity);
        }
        if (acted || monsterPlayed) { // if a monster or the Player played, go to the next turn.
            if (acted){
                gs.getPlayer().getPlayerStats().manageTemporaryBonus();
                int turnPassed = gs.getPlayer().getPlayerStats().getTurnPassed();
                if (turnPassed != -1){
                        gs.getDescriptor().updateDescriptor(
                                String.format("Il vous reste %d tour sur votre Bonus", turnPassed));
                        if (turnPassed == 0) {
                            gs.getPlayer().getPlayerStats().resetTurnPassed();
                        }
                }
            }
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
        Player player = gs.getPlayer();

        switch (keyCode) { // Process the pressed key bu the player.
            case KeyEvent.VK_Z:
                if (state == State.NORMAL || state == State.FIGHT) {
                    turned = hasTurned(player, Direction.NORTH);
                    player.setDirection(Direction.NORTH);
                    if (!positionLocked) {
                        acted = gs.movePlayer(0, -1);
                    }
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
                    gs.getHud().spellListString(); //remove the highlightning of the selected spell
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (state == State.FIGHT) {
                    int indexSpell =  player.getPlayerStats().getSpells().indexOf(player.getPlayerStats().getSelectedSpell());
                    int newIndexSpell = (indexSpell+1) % player.getPlayerStats().getSpells().size();
                    player.getPlayerStats().setSelectedSpell(player.getPlayerStats().getSpells().get(newIndexSpell));
                    gs.getHud().spellSelectionString(newIndexSpell);
                    gs.updateRange();
                    turned = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (state == State.FIGHT) {
                    int indexSpell = player.getPlayerStats().getSpells().indexOf(player.getPlayerStats().getSelectedSpell());
                    int nbSpells = player.getPlayerStats().getSpells().size();
                    int newIndexSpell = (indexSpell+nbSpells-1) % nbSpells;
                    player.getPlayerStats().setSelectedSpell(player.getPlayerStats().getSpells().get(newIndexSpell));
                    gs.getHud().spellSelectionString(newIndexSpell);
                    gs.updateRange();
                    turned = true;
                }
                break;
            case KeyEvent.VK_M:
                if (state != State.MAP) {
                    gs.setState(State.MAP);
                    gs.getMiniMap().updateMap();
                }
                modifiedMenu = true;
                break;
            case KeyEvent.VK_I:
                OpenInventory inv = new OpenInventory(gs, player.getInventory());
                modifiedMenu = true;
                acted = inv.hasActed();
                break;
            case KeyEvent.VK_H:
                gs.setHelp(!gs.getHelp());
                modifiedMenu = true;
                break;
            case KeyEvent.VK_V:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    acted = player.getInventory().useItem(ItemType.HEALTH_POTION, gs);
                }
                break;
            case KeyEvent.VK_B:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    acted = player.getInventory().useItem(ItemType.ELIXIR, gs);
                }
                break;
            case KeyEvent.VK_N:
                if (state == State.NORMAL || state == State.FIGHT) {
                    modifiedMenu = true;
                    acted = player.getInventory().useItem(ItemType.XP_BOTTLE, gs);
                }
                break;
            case KeyEvent.VK_CAPS_LOCK:
                if (state == State.NORMAL || state == State.FIGHT) {
                    positionLocked = !positionLocked;
                    if (positionLocked) {
                        player.setBottomSprites(colorize(player.getSprites(1), Colors.DEEP_GREY.bgApply()));
                    } else {
                        player.setBottomSprites("/^\\");
                    }
                    turned = true;
                }
                break;
            case KeyEvent.VK_ESCAPE: // escape
                if (state == State.MAP) {
                    gs.setState(State.NORMAL);
                    gs.isThereMonsters();
                }
                else {
                    gs.setState(State.PAUSE_MENU);
                    new InGameMenu(gs);
                }
                modifiedMenu = true;
                //exitStateInput();
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
    private void inactiveStateInput(String message) throws InterruptedException {
        System.out.println(colorize(message, Attribute.ITALIC(), Colors.RED.textApply()));
        int a = retrieveKey(sp);
        if (a == KeyEvent.VK_ESCAPE) {
            gs.setState(State.PAUSE_MENU);
            new InGameMenu(gs);
        }
        monsterPlayed = true;
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