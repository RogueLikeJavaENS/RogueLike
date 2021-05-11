package entity;

import gameElement.GameState;
import utils.Colors;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;

import java.awt.*;
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
    private boolean isPlayerAccessible;
    private boolean isNPCAccessible;
    private List<String> basicSprites; // sprite without color
    private Colors basicColor; // color of the Entity if it's not in a specific state
    private List<String> spritesToPrint; // the sprite to print on the console

    /**
     * Create an abstractEntity
     *
     * @param position of the abstractEntity
     * @param color of the abstractEntity
     * @param isPlayerAccessible true if another entity could be on the same tile else false
     */
    public AbstractEntity(Position position, Colors color, boolean isPlayerAccessible, boolean isNPCAccessible){
        this.position = position;
        this.isPlayerAccessible = isPlayerAccessible;
        this.isNPCAccessible = isNPCAccessible;
        basicSprites = new ArrayList<>();
        basicSprites.add("");
        basicSprites.add("");
        this.basicColor = color;
        this.spritesToPrint = new ArrayList<>();
        this.spritesToPrint.add(colorize(basicSprites.get(0),basicColor.textApply()));
        this.spritesToPrint.add(colorize(basicSprites.get(1),basicColor.textApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        // do nothing
    }

    @Override
    public void doInteraction(GameState gameState) {
        // do nothing
    }

    public boolean isMonster() {
        return false;
    }

    public boolean isTrap(){
        return false;
    }

    public boolean isDestroyable() {
        return false;
    }

    public boolean isBossPart() {
        return false;
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
    public boolean getIsPlayerAccessible() { return isPlayerAccessible; }

    /**
     * Set the boolean which describe the accessibility of the entity
     * @param isPlayerAccessible true or false
     */
    public void setIsPlayerAccessible(boolean isPlayerAccessible){
        this.isPlayerAccessible = isPlayerAccessible;
    }
    /**
     *Return the basic color of the entity
     * @return a color
     */

    public boolean getIsNPCAccessible(){
        return isNPCAccessible;
    }
    public void setIsNPCAccessible(boolean isNPCAccessible){
        this.isNPCAccessible = isNPCAccessible;
    }

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
     * Set the sprites to print
     * @param color1 color of the Up sprite
     * @param color2 color of the Down Sprite
     * @param sprite1 Up sprite
     * @param sprite2 Down Sprite
     */
    public void setSprites(String sprite1, String sprite2, Colors color1, Colors color2) {
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize(sprite1, color1.textApply()));
        sprites.add(colorize(sprite2, color2.textApply()));
        this.spritesToPrint = sprites;
    }

    /**
     * Set the sprites to print with a unique color
     * @param sprite1 Up sprite
     * @param sprite2 Down Sprite
     * @param color Color of both up and down Sprite
     */
    public void setSprites(String sprite1, String sprite2, Colors color) {
        setSprites(sprite1, sprite2, color, color);
    }

    /**
     * Set the sprites to print already colored.
     * @param sprite1 Up sprite
     * @param sprite2 Down sprite
     */
    public void setSprites(String sprite1, String sprite2) {
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        this.spritesToPrint = sprites;
    }

    /**
     * Set the basic sprites
     * @param sprite1 Up sprite
     * @param sprite2 Down sprite
     */
    public void setBasicSprites(String sprite1, String sprite2){
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
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
     *Set only the bottom sprites of the sprites to print
     * @param s the new String (3 char max)
     */
    public void setBottomSprites(String s) {
        spritesToPrint.remove(1);
        spritesToPrint.add(1, s);
    }

    /**
     * Set the position of the entity
     * @param position new Position
     */
    public void setPosition(Position position) { this.position = position; }

}