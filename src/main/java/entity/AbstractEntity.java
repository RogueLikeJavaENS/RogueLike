package entity;

import gameElement.GameState;
import utils.Position;

/**
 * This class represent an entity, which is an object or something alive who/which can be on the map
 * The entity has a position on the space
 *
 * @author Juliette
 * */

public class AbstractEntity implements Entity {

    private Position position;
    private boolean isAccessible;

    public AbstractEntity(Position position, boolean isAccessible){
        this.position = position;
        this.isAccessible = isAccessible;
    }

    public Position getPosition() { return position; }

    public void doAction(GameState gameState) {
        // nothing
    }

    public void setPosition(Position position) { this.position = position; }

}
