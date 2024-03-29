package game.tile;

public enum TileEnum {
    WALL(1), FLOOR(2), DOOR(3), EMPTY(4), SPIKE(5);

    private final int id;

    TileEnum(int i) {
        id = i;
    }

    public int getId() {
        return id;
    }

}
