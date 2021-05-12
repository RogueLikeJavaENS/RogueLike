package game.entity.object;

import game.entity.AbstractEntity;
import utils.Colors;
import utils.Position;

public class ObjectEntity extends AbstractEntity {
    
    public ObjectEntity(Position position,Colors color, boolean isPlayerAccessible, boolean isNPCAccessible) {
        super(position, color, isPlayerAccessible,isNPCAccessible);
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
