package gameElement.menu;

import gameElement.GameState;
import utils.State;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuAction> actions;
    private MenuAction selectedAction;
    private String headMenu;

    public Menu(State gameState) {
        headMenu = "";
        actions = new ArrayList<>();
        if (gameState == State.SHOP_MENU) {
            headMenu = "Shop Menu";
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
        else {
            headMenu = "Pause Menu";
            actions.add(new MenuAction("Resume game", state -> {
                state.setState(State.NORMAL);
                state.isThereMonstersInventory();
            }));
            actions.add(new MenuAction("Restart game", state -> {
                System.out.println("Restarting game not implemented");
            }));
            actions.add(new MenuAction("Exit game", state -> {
                state.setState(State.END);
            }));
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
        sb.append(headMenu).append(" :\n");
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
}
