package gameElement.menu;

import classeSystem.InGameClasses;
import com.diogonunes.jcolor.Attribute;
import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import generation.RoomFactory;
import utils.Colors;
import utils.Position;
import utils.StartMenu;
import utils.State;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 *this class manage all our ingame menu system
 * @author Antoine et Luca
 */
public class Menu {
    private List<MenuAction> actions;
    private MenuAction selectedAction;
    private String headMenu;
    private InGameClasses classesPicked = InGameClasses.DUMMY;

    public Menu(State gameState) {
        headMenu = "";
        actions = new ArrayList<>();
        if (gameState == State.SHOP_MENU) {
            headMenu = "Shop Menu :";
            actions.add(new MenuAction("Buy", state -> {
                state.setState(State.SHOP);
                state.merchant.getMerchantInventory().openBuyingSHop(state);
            }));
            actions.add(new MenuAction("Sell", state -> {
                boolean opened = state.merchant.getMerchantInventory().openSellingShop(state);
                if (opened) {
                    state.setState(State.SHOP);
                }
            }));
            actions.add(new MenuAction("Exit Shop", state -> {
                state.setState(State.NORMAL);
                state.isThereMonsters();
            }));
        }
        else if (gameState == State.START_MENU) {
            headMenu =
                    colorize("  _____                           _____             _     \n" +
                    " |  __ \\                         / ____|           | |    \n" +
                    " | |__) |___   __ _ _   _  ___  | (___   ___  _   _| |___ \n" +
                    " |  _  // _ \\ / _` | | | |/ _ \\  \\___ \\ / _ \\| | | | / __|\n" +
                    " | | \\ \\ (_) | (_| | |_| |  __/  ____) | (_) | |_| | \\__ \\\n" +
                    " |_|  \\_\\___/ \\__, |\\__,_|\\___| |_____/ \\___/ \\__,_|_|___/\n" +
                    "               __/ |                                      \n" +
                    "              |___/                                       \n\n", Attribute.BOLD(), Colors.RED.textApply() );
            actions.add(new MenuAction("New Game", state -> {
                state.setState(State.ClASS_SELECTION_MENU);
                state.setMenu(new Menu (state.getState()));
            }));
            actions.add(new MenuAction("Load Game", state -> System.out.println("Coming Soon")));
            actions.add(new MenuAction("Exit", state -> state.setState(State.END)));
        }
        else if (gameState == State.ClASS_SELECTION_MENU) {
                headMenu = "" +
                        "           .-----.            / \\\n" +
                        " \\ ' /   _/    )/             | |\n" +
                        "- ( ) -('---''--)             |.|\n" +
                        " / . \\((()\\^_^/)()            |.|\n" +
                        "  \\\\_\\ (()_)-((()()           |:|      __\n" +
                        "   '- \\ )/\\._./(()          ,_|:|_,   /  )                           /^\\     .\n" +
                        "     '/\\/( X   ) \\            (Oo    / _I_                      /\\   \"V\"\n" +
                        "     (___)|___/ ) \\            +\\ \\  || __|                    /__\\   I      O  o\n" +
                        "          |.#_|(___)              \\ \\||___|                   //..\\\\  I     . \n" +
                        "         /\\    \\ ( (_               \\ /.:.\\-\\                 \\].`[/  I\n" +
                        "         \\/\\/\\/\\) \\\\                 |.:. /-----\\             /l\\/j\\  (]    .  O\n" +
                        "         | / \\ |                     |___|::oOo::|           /. ~~ ,\\/I          .\n" +
                        "         |(   \\|                     /   |:<_T_>:|           \\\\L__j^\\/I       o\n" +
                        "        _|_)__|_\\_                  |_____\\ ::: /             \\/--v}  I     o   .\n" +
                        "        )...()...(                   | |  \\ \\:/               |    |  I   _________\n" +
                        "         | (   \\ |                   | |   | |                |    |  I c(`       ')o\n" +
                        "      .-'__,)  (  \\                  \\ /   | \\___             |    l  I   \\.     ,/\n" +
                        "                '\\_-,                / |   \\_____\\          _/j  L l\\_!  _//^---^\\\\_  \n" +
                        "                                     `-'\n" +
                        "Choose your class Hero :\n";
                actions.add(new MenuAction("Ranger", state -> {
                    String name = StartMenu.getName();
                    Position initialPosition = state.getDungeon().getRoom(0).getCenter();
                    setClassesPicked(InGameClasses.RANGER);
                    System.out.println(getClassesPicked());
                    state.setPlayer(new Player(initialPosition, 100, 100, name, getClassesPicked(), 1));
                    state.setState(State.NORMAL);
                    RoomFactory.addMerchant(state, state.getDungeon());
                }));
                actions.add(new MenuAction("Warrior", state -> {
                    String name = StartMenu.getName();
                    Position initialPosition = state.getDungeon().getRoom(0).getCenter();
                    setClassesPicked(InGameClasses.WARRIOR);
                    System.out.println(getClassesPicked());
                    state.setPlayer(new Player(initialPosition, 100, 100, name, getClassesPicked(), 1));
                    state.setState(State.NORMAL);
                    RoomFactory.addMerchant(state, state.getDungeon());
                }));
                actions.add(new MenuAction("Mage", state -> {
                    String name = StartMenu.getName();
                    Position initialPosition = state.getDungeon().getRoom(0).getCenter();
                    setClassesPicked(InGameClasses.MAGE);
                    System.out.println(getClassesPicked());
                    state.setPlayer(new Player(initialPosition, 100, 100, name, getClassesPicked(), 1));
                    state.setState(State.NORMAL);
                    RoomFactory.addMerchant(state, state.getDungeon());
                }));
        }
        else {
            headMenu = "Pause Menu : ";
            actions.add(new MenuAction("Resume game", state -> {
                state.setState(State.NORMAL);
                state.isThereMonstersInventory();
            }));
            actions.add(new MenuAction("Restart game", state -> System.out.println("Restarting game not implemented")));
            actions.add(new MenuAction("Exit game", state -> state.setState(State.END)));
        }
        selectedAction = actions.get(0);
    }

    public void selectAction(GameState gameState) {
        selectedAction.doAction(gameState);
    }

    public void previousSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index+1)% size);
    }
    public void nextSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index + size -1)% size);
    }

    public String displayMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append(headMenu).append(" \n");
        for (MenuAction action : actions) {
            if (action.equals(selectedAction)) {
                sb.append(" -> ");
            }
            else {
                sb.append("    ");
            }
            sb.append(action.getName()).append("\n");
        }
        return sb.toString();
    }

    public void setClassesPicked(InGameClasses classesPicked) {
        this.classesPicked = classesPicked;
    }

    public InGameClasses getClassesPicked() {
        return classesPicked;
    }
}
