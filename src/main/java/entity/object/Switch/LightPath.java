package entity.object.Switch;

import entity.object.ObjectEntity;
import utils.Colors;
import utils.Position;

public class LightPath extends ObjectEntity {
    public LightPath(Position position, Colors color, boolean isPlayerAccessible, boolean isNPCAccessible) {
        super(position, color, isPlayerAccessible, isNPCAccessible);
    }
}
