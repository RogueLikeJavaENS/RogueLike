package entity.living;
import utils.Position;


public class NPC extends LivingEntity {

    public NPC(Position position, String name) throws IllegalArgumentException {
        super(position, name);
    }
}