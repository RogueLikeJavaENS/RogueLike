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

    private final String name;
    private int level;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int moneyCount;
    private Direction direction;
    private String display;

    public LivingEntity(Position position, int hp, int mp, String name, int level) throws IllegalArgumentException{
        super(position, false); // false because a living entity is never accessible.
        this.level = Check.checkPositivity(level);
        this.hp = Check.checkPositivity(hp);
        this.maxHp = Check.checkPositivity(hp);
        this.mp = Check.checkPositivity(mp);
        this.maxMp = Check.checkPositivity(mp);
        this.name = name;
        setDirection(Direction.SOUTH);
        this.moneyCount = 0;
    }

    public String getName() { return name; }
    @Override
    public Position getPosition() { return super.getPosition(); }
    public int getLevel() { return level; }
    public int getMaxMP() { return maxMp; }
    public int getMaxHP() { return maxHp; }
    public int getCurrentMP() { return mp; }
    public int getCurrentHP() { return hp; }
    public String getDisplay() { return display; }
    public Direction getDirection() { return direction; }
    public int getMoneyCount() { return moneyCount; }

    @Override
    public void setPosition(Position position) { super.setPosition(position); }

    public void setLevel(int level) throws IllegalArgumentException{ this.level = Check.checkPositivity(level); }
    public void setMaxMP(int maxMp) throws IllegalArgumentException{ this.maxMp = Check.checkPositivity(maxMp); }
    public void setMaxHP(int maxHp) throws IllegalArgumentException{ this.maxHp = Check.checkPositivity(maxHp); }
    public void setMP(int mp) throws IllegalArgumentException { this.mp = Check.checkPositivity(mp); }
    public void setHP(int hp) throws IllegalArgumentException { this.hp = Check.checkPositivity(hp); }
    public void setDisplay(String display) { this.display = display; }

    public void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case NORTH:
                setDisplay("^^");
                break;
            case WEST:
                setDisplay("<<");
                break;
            case SOUTH:
                setDisplay("oo");
                break;
            case EAST:
                setDisplay(">>");
        }
    }
    public void setMoneyCount (int moneyCount) throws IllegalArgumentException { this.moneyCount = Check.checkPositivity(moneyCount); }
}

