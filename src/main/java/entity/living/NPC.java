package entity.living;
import utils.Position;


public class NPC extends LivingEntity {

    public NPC(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
    }
}