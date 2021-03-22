package utils;

public enum Direction {
    NORTH, EAST, SOUTH, WEST, NONE;

    /**
     * Returns the opposite direction of the given one.
     * @return the opposite cardinal direction of the one given as parameter.
     * @throws IllegalArgumentException if the argument isn't one of the four cardinal directions.
     */
    public Direction oppositeDirection() throws IllegalArgumentException {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case NONE:
                return NONE;
            default:
                throw new IllegalArgumentException("The argument has to be one of the 4 cardinal directions");
        }
    }

    /**
     * Alias for the ordinate() method.
     * @return the int value associated with the direction.
     */
    public int getValue() {
        return this.ordinal();
    }

    public static Direction intToDirection(int i) {
        return Direction.values()[i];
    }
}
