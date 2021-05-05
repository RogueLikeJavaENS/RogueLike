package gameElement.menu;

import classeSystem.InGameClasses;
import entity.living.player.Player;
import gameElement.Dungeon;
import gameElement.GameState;
import utils.Position;
import utils.StartMenu;
import utils.State;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuAction> actions;
    private MenuAction selectedAction;
    private String headMenu;
    private InGameClasses classesPicked;

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
        else if (gameState == State.START_MENU) {
            headMenu = "Welcome to RogueSouls";
            actions.add(new MenuAction("New Game", state -> {
                Position initialPosition = state.getDungeon().getRoom(0).getCenter();
                String name = StartMenu.getName();
                state.setState(State.ClASS_SELECTION_MENU);
                state.setMenu(new Menu (state.getState()));
                state.setPlayer(new Player(initialPosition, 100, 100, name, 1));
            }));
            actions.add(new MenuAction("Load Game", state -> {
                System.out.println("Coming Soon");
            }));
            actions.add(new MenuAction("Exit", state -> {
                state.setState(State.END);
            }));
        }
        else if (gameState == State.ClASS_SELECTION_MENU) {
                headMenu = "Pick a class hero";
                actions.add(new MenuAction("Ranger", state -> {
                    classesPicked = InGameClasses.RANGER;
                    state.setState(State.NORMAL);
                }));
                actions.add(new MenuAction("Warrior", state -> {
                    classesPicked = InGameClasses.WARRIOR;
                    state.setState(State.NORMAL);
                }));
                actions.add(new MenuAction("Mage", state -> {
                    classesPicked = InGameClasses.MAGE;
                    state.setState(State.NORMAL);
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
