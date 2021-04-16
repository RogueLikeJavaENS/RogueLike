package entity.living;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.List;


public class NPC extends LivingEntity {

    public NPC(Position position, String name, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, stats);
    }
}