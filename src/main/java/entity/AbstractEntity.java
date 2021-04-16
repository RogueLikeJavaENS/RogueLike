package entity;

import gameElement.GameState;
import utils.Position;

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
    private List<String> sprites;

    public AbstractEntity(Position position, boolean isAccessible){
        this.position = position;
        this.isAccessible = isAccessible;
    }

    @Override
    public Position getPosition() { return position; }
    @Override
    public boolean getIsAccessible() { return isAccessible; }

    public String getSprites(int i) {
        return sprites.get(i);
    }

    @Override
    public void doAction(GameState gameState) {
        // nothing
    }

    public void setSprites(List<String> sprites) {
        this.sprites = sprites;
    }

    public void setUpSprites(String s) {
        sprites.remove(0);
        sprites.add(0, s);
    }

    public void setPosition(Position position) { this.position = position; }

}