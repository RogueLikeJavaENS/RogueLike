package display;

/**
 * A Tile contains its type (as an int), its sprite (how it will be displayed)
 * and whether it is accessible or not.
 *
 * @author Raphael
 */

public interface Tile {

    String toString();
    boolean isAccessible();
    void setAccessible(boolean accessible);
}
