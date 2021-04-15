package entity.living;
import utils.Position;


public class NPC extends LivingEntity {

    public NPC(Position position, String name) throws IllegalArgumentException {
        super(position, name);
        this.stats=new NPCStats(100, 100, 1, 1, 1, 1, 0, 1);
    }
}