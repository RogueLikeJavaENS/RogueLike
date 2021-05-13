package game.entity.living;

import game.entity.AbstractEntity;
import utils.Colors;
import utils.Direction;
import utils.Position;


/**
 * This class describe all the living game.entity that could exist, it could be the player or the monster or possibly a merchant, etc...
 * A living entity is describe by his name, level, his maximum number of PV and PM, his current number of PV and PM
 *
 */
public class LivingEntity extends AbstractEntity {

    private final String name;
    protected AbstractStats stats;
    private Direction direction;

    /**
     * Create a Living Entity
     *
     * @param position the position of the living entity
     * @param name the name of the living entity
     * @param color the color of the living entity
     * @param stats the stats of the living entity
     * @throws IllegalArgumentException
     */
    public LivingEntity(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, color,false,false); // false because a living game.entity is never accessible.
        this.name = name;
        this.stats = stats;
    }

    /**
     * Create a Living Entity with a special boolean to describe the is NPCAccessible
     * @param position the position of the living entity
     * @param name the name of the living entity
     * @param color the color of the living entity
     * @param stats the stats of the living entity
     * @param isNPCAccessible boolean which describe the special accessibility for the NPC
     * @throws IllegalArgumentException
     */
    public LivingEntity(Position position, String name, Colors color, AbstractStats stats, boolean isNPCAccessible) throws IllegalArgumentException {
        super(position, color,false, isNPCAccessible); // false because a living game.entity is never accessible.
        this.name = name;
        this.stats = stats;
    }

    // Getters
    public AbstractStats getStats() {
        return stats;
    }
    public String getName() {
        return name;
    }
    @Override
    public Position getPosition() {
        return super.getPosition();
    }
    public Direction getDirection() {
        return direction;
    }

    // Setters
    @Override
    public void setPosition(Position position) { super.setPosition(position); }
    public void setDirection(Direction direction) {
        this.direction = direction; // Modify the sprite of the player according to where he look
        switch (direction) {
            case NORTH:
                setUpSprites("^ ^");
                break;
            case WEST:
                setUpSprites("< <");
                break;
            case SOUTH:
                setUpSprites("v v");
                break;
            case EAST:
                setUpSprites("> >");
        }
    }
}
