package entity.living;

import entity.AbstractEntity;
import utils.Check;
import utils.Direction;
import utils.Position;

/**
 * This class describe all the living entity that could exist, it could be the player or the monster or possibly a merchant, etc...
 *  A living entity is describe by his name, level, his maximum number of PV and PM, his current number of PV and PM
 *
 * @author Juliette
 * */



public class LivingEntity extends AbstractEntity {
    private AbstractStats stats;
    private final String name;
    private int moneyCount;
    private int speed;
    private Direction direction;
    private String display;

    public LivingEntity(Position position, String name) throws IllegalArgumentException{
        super(position, false); // false because a living entity is never accessible.
        this.name = name;
        setDirection(Direction.SOUTH);
        this.moneyCount = 0;
        this.speed = 1;
    }

    public String getName() { return name; }
    public String getDisplay() { return display; }
    public Direction getDirection() { return direction; }
    public int getMoneyCount() { return moneyCount; }
    public int getSpeed() { return speed; }


    public void setDisplay(String display) { this.display = display; }
    public void setSpeed(int speed) { this.speed = speed; }

    public void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case NORTH:
                setDisplay("^ ^");
                break;
            case WEST:
                setDisplay("< <");
                break;
            case SOUTH:
                //setDisplay("o o");
                setDisplay("v v");
                break;
            case EAST:
                setDisplay("> >");
        }
    }

    public void setMoneyCount (int moneyCount) throws IllegalArgumentException { this.moneyCount = Check.checkPositivity(moneyCount); }
}
