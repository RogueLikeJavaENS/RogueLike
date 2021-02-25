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

    //fait office de toString
    public String getSprite() {
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
