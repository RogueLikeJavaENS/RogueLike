package gameElement.menu;

import com.diogonunes.jcolor.Attribute;
import display.RendererUI;
import gameElement.GameState;
import utils.Colors;
import utils.ScanPanel;
import utils.State;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;


public class InGameMenu {

    private final String pauseHead =
            colorize("  _____                           _____             _     \n" +
                    " |  __ \\                         / ____|           | |    \n" +
                    " | |__) |___   __ _ _   _  ___  | (___   ___  _   _| |___ \n" +
                    " |  _  // _ \\ / _` | | | |/ _ \\  \\___ \\ / _ \\| | | | / __|\n" +
                    " | | \\ \\ (_) | (_| | |_| |  __/  ____) | (_) | |_| | \\__ \\\n" +
                    " |_|  \\_\\___/ \\__, |\\__,_|\\___| |_____/ \\___/ \\__,_|_|___/\n" +
                    "               __/ |                                      \n" +
                    "              |___/                                       \n\n", Attribute.BOLD(), Colors.RED.textApply())
            + " ============================ PAUSE ============================\n";

    private final String shopHead =
            colorize("  .        _________________________    .\n" +
                    "           UUUUUUUUUUUUUUUUUUUUUUUUU         ()\n" +
                    "     .     UUUUUUUUUUUUUUUUUUUUUUUUU ()  () (()\n" +
                    "  _________UUUUUUUUUUUUUUUUUUUUUUUUU()())()))()\n" +
                    "  UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU))\n" +
                    "()UUUUUUUUUU|| ___ || ___ || ___ ||UUUUUUUUUU()\n" +
                    "(()|__\\S/__||| ~|~ || ~|~ || ~|~ ||___\\S/__|()(\n" +
                    "))(|-------||| \"\"\" || \"\"\" || \"\"\" ||  _____ |()(\n" +
                    "(()|-------||| ___ ||/_º_\\|| ___ ||  ~|~|~ |(()\n" +
                    ")))|-------||| ~|~ |||\"\"\"||| ~|~ ||  \"\"\"\"\" |)()\n" +
                    "@@@@¯¯¯¯¯¯¯@||@@@@@|||'  |||@@@@@||@@@@@@@@@@@@\n" +
                    "____  '`.  ___________| |______________________\n\n", Attribute.BOLD())
            + " ============================ SHOP  ============================\n";

    private List<InGameAction> actions;
    private InGameAction selectedAction;
    private String headMenu;
    private boolean endMenu = false;
    private final ScanPanel sp;


    public InGameMenu(GameState gameState) {
        actions = new ArrayList<>();
        this.sp = gameState.getScanPanel();
        initMenu(gameState.getState());
        try {
            loopMenu(gameState);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void loopMenu(GameState gameState) throws InterruptedException {
        boolean display = true;
        while (!endMenu) {
            if (display) {
                RendererUI.clearConsole();
                displayMenu();
            }
            sp.reset();
            int keyCode = 0;
            while(keyCode == 0) {
                keyCode = sp.getKeyPressed();
                Thread.sleep(1);  // Without that, Java deletes the loop
            }
            display = true;
            switch (keyCode) {
                case KeyEvent.VK_Z:
                case KeyEvent.VK_UP:
                    previousSelection();
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    nextSelection();
                    break;
                case KeyEvent.VK_E:
                case KeyEvent.VK_ENTER:
                    selectedAction.doAction(gameState);
                    endMenu = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameState.setState(State.NORMAL);
                    gameState.isThereMonstersInventory();
                    endMenu = true;
                    break;
                default:
                    display = false;
                    break;
            }
        }
    }

    public void initMenu(State stateMenu) {
        if (stateMenu.equals(State.SHOP_MENU)) {
            headMenu = shopHead;
            actions.add(new InGameAction("Buy", state -> {
                new OpenInventory(state, state.getMerchant().getMerchantInventory(), true);
            }));
            actions.add(new InGameAction("Sell", state -> {
                new OpenInventory(state, state.getMerchant().getMerchantInventory(), false);
            }));
            actions.add(new InGameAction("Exit Shop", state -> {
                state.setState(State.NORMAL);
                state.isThereMonsters();
            }));
        } else if (stateMenu.equals(State.PAUSE_MENU)) {
            headMenu = pauseHead;
            actions.add(new InGameAction("Resume game", state -> {
                state.setState(State.NORMAL);
                state.isThereMonstersInventory();
            }));
            actions.add(new InGameAction("Restart game", state -> System.out.println("Restarting game not implemented")));
            actions.add(new InGameAction("Exit game", state -> state.setState(State.END)));
        }
        selectedAction = actions.get(0);
    }

    public void nextSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index+1)% size);
    }
    public void previousSelection() {
        int index = actions.indexOf(selectedAction);
        int size = actions.size();
        selectedAction = actions.get((index + size -1)% size);
    }

    public void displayMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append(headMenu);
        for (InGameAction action : actions) {
            sb.append("|");
            if (action.equals(selectedAction)) {
                sb.append(colorize(" -> ", Attribute.BOLD(), Colors.RED.textApply()));
            }
            else {
                sb.append("    ");
            }
            sb.append(action.getName()).append(" ".repeat(59-action.getName().length())).append("|\n");
        }
        sb.append(" =============================================================== \n");
        System.out.println(sb);
    }
}
