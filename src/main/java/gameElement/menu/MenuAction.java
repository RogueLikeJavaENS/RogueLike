package gameElement.menu;

import gameElement.GameState;

/**
 *
 * @author Antoine
 */

public class MenuAction {
    private String name;
    private Action action;

    public MenuAction(String name, Action action) {
        this.name = name;
        this.action = action;
    }

    public void doAction(GameState state) {
        action.doAction(state);
    }

    public String getName() {
        return name;
    }
}
