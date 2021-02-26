/**
 * This class represent an entity, which is an object or something alive who/which can be on the map
 * The entity has a position on the space
 *
 * @author Juliette
 * */

public class AbstractEntity implements Entity {

    private Position position;

    public AbstractEntity(Position position){
        this.position = position;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}
