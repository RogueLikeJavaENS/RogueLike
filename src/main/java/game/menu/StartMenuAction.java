package game.menu;

public class StartMenuAction {
    private String name;
    private StartAction action;

    public StartMenuAction(String name, StartAction action) {
        this.name = name;
        this.action = action;
    }

    public void doAction() {
        action.doAction();
    }

    public String getName() {
        return name;
    }
}
