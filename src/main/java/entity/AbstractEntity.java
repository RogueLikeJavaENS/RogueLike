package entity;

import gameElement.GameState;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent an entity, which is an object or something alive who/which can be on the map
 * The entity has a position on the space
 *
 * @author Juliette
 * */

public abstract class AbstractEntity implements Entity {

    private Position position;
    private boolean isAccessible;
    private List<String> basicSprites;
    private Colors basicColor;
    private List<String> spritesToPrint;

    public AbstractEntity(Position position, boolean isAccessible){
        this.position = position;
        this.isAccessible = isAccessible;
        List<String> sprites = new ArrayList<>();
        sprites.add("");
        sprites.add("");
        this.basicSprites = sprites;
        this.spritesToPrint = sprites;
        this.basicColor = Colors.WHITE;
    }

    @Override
    public Position getPosition() { return position; }
    @Override
    public boolean getIsAccessible() { return isAccessible; }

    public Colors getBasicColor() { return basicColor; }
    public void setBasicColor(Colors basicColor) { this.basicColor = basicColor; }

    public String getSprites(int i) { return spritesToPrint.get(i); }
    public String getBasicSprites(int i) { return basicSprites.get(i); }

    @Override
    public void doAction(GameState gameState) {
        // nothing
    }

    public void setSprites(List<String> sprites) {
        this.spritesToPrint = sprites;
    }
    public void setBasicSprites(List<String> sprites){
        this.basicSprites = sprites;
    }

    public void setUpSprites(String s) {
        spritesToPrint.remove(0);
        spritesToPrint.add(0, s);
    }

    public void setPosition(Position position) { this.position = position; }

}