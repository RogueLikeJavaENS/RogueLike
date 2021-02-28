package display;

/**
 * A display.Tile contains its type (as an int), its sprite (how it will be displayed)
 * and whether it is accessible or not.
 *
 * @author Raphael
 */

public class Tile {
    private int typeID;
    private String sprite;
    private boolean isAccessible;

    public Tile(int typeID, String sprite, boolean isAccessible) {
        this.typeID = typeID;
        this.sprite = sprite;
        this.isAccessible = isAccessible;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    /**
     * @return The sprite that will be displayed to represent the tile.
     */
    public String toString() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }
}
