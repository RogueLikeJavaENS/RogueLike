package entity.living;

import entity.AbstractEntity;
import utils.Check;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.List;


/**
 * This class describe all the living entity that could exist, it could be the player or the monster or possibly a merchant, etc...
 * A living entity is describe by his name, level, his maximum number of PV and PM, his current number of PV and PM
 *
 * @author Juliette
 */


public class LivingEntity extends AbstractEntity {

    private final String name;
    protected AbstractStats stats;
    private Direction direction;

    private String display;

    public LivingEntity(Position position, String name, AbstractStats stats) throws IllegalArgumentException {
        super(position, false); // false because a living entity is never accessible.
        this.name = name;
        setDirection(Direction.SOUTH);
        this.stats = stats;

    }

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

    public String getDisplay() {
        return display;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setPosition(Position position) { super.setPosition(position); }
    public void setLevel(int level) throws IllegalArgumentException{ this.level = Check.checkPositivity(level); }
    public void setMaxMP(int maxMp) throws IllegalArgumentException{ this.maxMp = Check.checkPositivity(maxMp); }
    public void setMaxHP(int maxHp) throws IllegalArgumentException{ this.maxHp = Check.checkPositivity(maxHp); }
    public void setMP(int mp) throws IllegalArgumentException { this.mp = Check.checkPositivity(mp); }
    public void setHP(int hp) throws IllegalArgumentException { this.hp = Check.checkPositivity(hp); }

    public void setSpeed(int speed) { this.speed = speed; }

    public void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case NORTH:
                setUpSprites("^ ^");
                break;
            case WEST:
                setUpSprites("< <");
                break;
            case SOUTH:
                //setDisplay("o o");
                setUpSprites("v v");
                break;
            case EAST:
                setUpSprites("> >");
        }
    }
}
