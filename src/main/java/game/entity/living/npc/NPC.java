package game.entity.living.npc;
import game.entity.living.AbstractStats;
import game.entity.living.LivingEntity;
import utils.Colors;
import utils.Position;


/**
 * This class describe a NPC
 * AN NPC is a character which is not the player, it can be a monster or a merchant for example
 */
public class NPC extends LivingEntity {

    /**
     * Create an NPC
     *
     * @param position of the NPC
     * @param name of the NPC
     * @param color of the NPC on the console
     * @param stats of the NPC
     * @throws IllegalArgumentException Position can't be negative.
     */
    public NPC(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
    }
}