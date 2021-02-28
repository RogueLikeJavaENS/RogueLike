/**
 * This class describe all the living entity that could exist, it could be the player or the monster or possibly a merchant, etc...
 *  A living entity is describe by his name, level, his maximum number of PV and PM, his current number of PV and PM
 *
 * @author Juliette
 * */



public class LivingEntity extends AbstractEntity {

    private String name;
    private int level;
    private int pv;
    private int maxPv;
    private int pm;
    private int maxPm;

    public LivingEntity(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException{
        super(position);
        this.level = Check.checkPositivity(level);
        this.pv = Check.checkPositivity(pv);
        this.maxPv = Check.checkPositivity(pv);
        this.pm = Check.checkPositivity(pm);
        this.maxPm = Check.checkPositivity(pm);
        this.name = name;
    }

    public String getName() { return name; }
    @Override
    public Position getPosition() { return super.getPosition(); }
    public int getLevel() { return level; }
    public int getMaxPm() { return maxPm; }
    public int getMaxPv() { return maxPv; }
    public int getPm() { return pm; }
    public int getPv() { return pv; }

    @Override
    public void setPosition(Position position) { super.setPosition(position); }

    public void setLevel(int level) throws IllegalArgumentException{ this.level = Check.checkPositivity(level); }
    public void setMaxPm(int maxPm) throws IllegalArgumentException{ this.maxPm = Check.checkPositivity(maxPm); }
    public void setMaxPv(int maxPv) throws IllegalArgumentException{ this.maxPv = Check.checkPositivity(maxPv); }
    public void setPm(int pm) throws IllegalArgumentException { this.pm = Check.checkPositivity(pm); }
    public void setPv(int pv) throws IllegalArgumentException { this.pv = Check.checkPositivity(pv); }

}
