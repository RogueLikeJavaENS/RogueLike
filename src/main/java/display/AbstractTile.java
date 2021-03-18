package display;

public abstract class AbstractTile implements Tile {
    private final String sprite;
    private boolean isAccessible;

    public AbstractTile(String sprite, boolean isAccessible) {
        this.sprite = sprite;
        this.isAccessible = isAccessible;
    }

    public String toString() {
        return sprite;
    }

    @Override
    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }
}
