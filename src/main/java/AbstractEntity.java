public class AbstractEntity implements Entity {

     Position position;

    public AbstractEntity(Position position){
        this.position = position;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}

