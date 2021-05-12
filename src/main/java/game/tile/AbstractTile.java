package game.tile;

import game.element.GameState;

public abstract class AbstractTile implements Tile {
    private final String sprite;
    private boolean isPlayerAccessible;
    private boolean isNPCAccessible;
    public AbstractTile(String sprite, boolean isPlayerAccessible, boolean isNPCAccessible) {
        this.sprite = sprite;
        this.isPlayerAccessible = isPlayerAccessible;
        this.isNPCAccessible = isNPCAccessible;
    }

    public String toString() {
        return sprite;
    }

    @Override
    public boolean isPlayerAccessible() {
        return isPlayerAccessible;
    }

    @Override
    public void setPlayerAccessible(boolean isPlayerAccessible) {
        this.isPlayerAccessible = isPlayerAccessible;
    }

    @Override
    public boolean isNPCAccessible() { return isNPCAccessible; }

    @Override
    public void setNPCAccessible(boolean NPCAccessible) { isNPCAccessible = NPCAccessible; }

    @Override
    public void doAction(GameState gameState){
        // Do nothing
    }


}
