package entity.living;
import utils.Position;



public class NPC extends LivingEntity {

    public NPC(Position position, String name, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, stats);
    }
}