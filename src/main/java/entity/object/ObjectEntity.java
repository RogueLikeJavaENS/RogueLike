package entity.object;

import entity.AbstractEntity;
import utils.Position;

public class ObjectEntity extends AbstractEntity {
    
    public ObjectEntity(Position position) {
        super(position);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
