package entity.object;

import entity.AbstractEntity;
import utils.Colors;
import utils.Position;

public class ObjectEntity extends AbstractEntity {
    
    public ObjectEntity(Position position,Colors color, boolean isAccessible) {
        super(position, color, isAccessible,true);
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
