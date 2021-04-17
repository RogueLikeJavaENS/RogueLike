package entity.living;
import utils.Colors;
import utils.Position;



public class NPC extends LivingEntity {

    public NPC(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
    }
}