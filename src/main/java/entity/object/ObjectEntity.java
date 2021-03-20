package entity.object;

import entity.AbstractEntity;
import utils.Position;

public class ObjectEntity extends AbstractEntity {
    
    public ObjectEntity(Position position, boolean isAccessible) {
        super(position, isAccessible);
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
