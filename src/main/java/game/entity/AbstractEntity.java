package game.entity;

import game.elements.GameState;
import utils.Colors;
import utils.Position;
import static com.diogonunes.jcolor.Ansi.colorize;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an entity, which is an object or a living being which can be on the map.
 * The entity has a position, can be either accessible or not and has sprites and (a) color(s)
 *
 * @author Juliette
 * */

public abstract class AbstractEntity implements Entity {
    private Position position;
    private boolean isPlayerAccessible;
    private boolean isNPCAccessible;
    private List<String> basicSprites; // sprite without color
    private final Colors basicColor; // color of the Entity if it's not in a specific state
    private List<String> spritesToPrint; // the sprite to print on the console

    /**
     * Creates an abstractEntity
     *
     * @param position position of the abstractEntity
     * @param color color of the abstractEntity
     * @param isPlayerAccessible true if another entity can be on the same position, false otherwise
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

    @Override
    public Position getPosition() { return position; }

    @Override
    public boolean getIsPlayerAccessible() { return isPlayerAccessible; }

    /**
     * Sets the boolean which determines the accessibility of the entity.
     *
     * @param isPlayerAccessible True if you want it to become accessible, false otherwise.
     */
    public void setIsPlayerAccessible(boolean isPlayerAccessible){
        this.isPlayerAccessible = isPlayerAccessible;
    }

    public boolean getIsNPCAccessible(){
        return isNPCAccessible;
    }
    public void setIsNPCAccessible(boolean isNPCAccessible){
        this.isNPCAccessible = isNPCAccessible;
    }

    /**
     * Returns the entity's basic color.
     *
     * @return the original entity's color
     */
    public Colors getBasicColor() { return basicColor; }

    /**
     * Returns the entity's sprite to print.
     *
     * @param i 0 for the upper sprites and 1 for the bottom sprites
     * @return the corresponding part of the sprite
     *
     */
    public String getSprites(int i) { return spritesToPrint.get(i); }

    /**
     * Returns a part of the entity's basic sprite.
     *
     * @param i 0 for the upper sprites and 1 for the bottom sprites
     * @return the corresponding part of the entity's original sprite
     */
    public String getBasicSprites(int i) { return basicSprites.get(i); }

    /**
     * Sets the entity's sprites with different colors (or not, but a method exists if you only have one color).
     *
     * @param color1 color of the upper sprite
     * @param color2 color of the bottom Sprite
     * @param sprite1 Upper sprite
     * @param sprite2 Bottom Sprite
     */
    public void setSprites(String sprite1, String sprite2, Colors color1, Colors color2) {
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize(sprite1, color1.textApply()));
        sprites.add(colorize(sprite2, color2.textApply()));
        this.spritesToPrint = sprites;
    }

    /**
     * Sets the entity's sprites with a unique color.
     *
     * @param sprite1 Upper sprite
     * @param sprite2 Bottom Sprite
     * @param color Color of both sprites.
     */
    public void setSprites(String sprite1, String sprite2, Colors color) {
        setSprites(sprite1, sprite2, color, color);
    }

    /**
     * Sets the entity's sprites.
     *
     * @param sprite1 Upper sprite
     * @param sprite2 Bottom sprite
     */
    public void setSprites(String sprite1, String sprite2) {
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        this.spritesToPrint = sprites;
    }

    /**
     * Sets the entity's basic sprites.
     * @param sprite1 Upper sprite
     * @param sprite2 Bottom sprite
     */
    public void setBasicSprites(String sprite1, String sprite2){
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        this.basicSprites = sprites;
    }

    /**
     * Sets the entity's upper sprites only.
     *
     * @param s the new upper sprite (3 char max)
     */
    public void setUpSprites(String s) {
        spritesToPrint.remove(0);
        spritesToPrint.add(0, s);
    }

    /**
     * Sets the entity's bottom sprites only.
     *
     * @param s the new bottom sprite (3 char max)
     */
    public void setBottomSprites(String s) {
        spritesToPrint.remove(1);
        spritesToPrint.add(1, s);
    }

    /**
     * Sets the position of the entity.
     *
     * @param position the entity's new Position
     */
    public void setPosition(Position position) { this.position = position; }

}