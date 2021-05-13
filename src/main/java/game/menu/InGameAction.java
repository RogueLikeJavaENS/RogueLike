package game.menu;

import game.elements.GameState;

/**
 * An InGameAction takes the GameState to perform the functional action.
 * @author Antoine
 */
public class InGameAction {
    private String name;
    private Action action;

    public InGameAction(String name, Action action) {
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
