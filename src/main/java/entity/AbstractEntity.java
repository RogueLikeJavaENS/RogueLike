package entity;

import gameElement.GameState;
import utils.Colors;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represent an entity, which is an object or something alive who/which can be on the map
 * The entity has a position on the space, could be accessible or not and has sprites and color
 *
 * @author Juliette
 * */

public abstract class AbstractEntity implements Entity {
    private Position position;
    private final boolean isAccessible;
    private List<String> basicSprites; // sprite without color
    private Colors basicColor; // color of the Entity if it's not in a specific state
    private List<String> spritesToPrint; // the sprite to print on the console

    /**
     * Create an abstractEntity
     *
     * @param position of the abstractEntity
     * @param color of the abstractEntity
     * @param isAccessible true if another entity could be on the same tile else false
     */
    public AbstractEntity(Position position, Colors color, boolean isAccessible){
        this.position = position;
        this.isAccessible = isAccessible;
        basicSprites = new ArrayList<>();
        basicSprites.add("");
        basicSprites.add("");
        this.basicColor = color;
        this.spritesToPrint = new ArrayList<>();
        this.spritesToPrint.add(colorize(basicSprites.get(0),basicColor.textApply()));
        this.spritesToPrint.add(colorize(basicSprites.get(1),basicColor.textApply()));
    }

    /**
     * Return the position of the entity
     * @return the position
     */
    @Override
    public Position getPosition() { return position; }

    /***
     *Return the boolean which describe the accessibility of the entity
     * @return true or false
     */
    @Override
    public boolean getIsAccessible() { return isAccessible; }

    /**
     *Return the basic color of the entity
     * @return a color
     */
    public Colors getBasicColor() { return basicColor; }

    /**
     *Return the sprite tp print of the entity
     * @param i 0 for the up sprites and 1 for the down sprites
     * @return a String
     *
     */
    public String getSprites(int i) { return spritesToPrint.get(i); }

    /**
     *Return the basic sprite of the entity
     * @param i 0 for the up sprites and 1 for the down sprites
     * @return a String
     */
    public String getBasicSprites(int i) { return basicSprites.get(i); }

    /**
     *Set the basic color of the entity
     * @param basicColor the new color
     */
    public void setBasicColor(Colors basicColor) { this.basicColor = basicColor; }

    /**
     *Set the sprites to print
     * @param sprites the new Sprites
     */
    public void setSprites(List<String> sprites) {
        this.spritesToPrint = sprites;
    }

    /**
     *Set the basic sprites
     * @param sprites the new basic sprites
     */
    public void setBasicSprites(List<String> sprites){
        this.basicSprites = sprites;
    }

    /**
     *Set only the up sprites of the sprites to print
     * @param s the new String (3 char max)
     */
    public void setUpSprites(String s) {
        spritesToPrint.remove(0);
        spritesToPrint.add(0, s);
    }

    /**
     * Set the position of the entity
     * @param position new Position
     */
    public void setPosition(Position position) { this.position = position; }

}