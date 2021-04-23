package stuff;

public abstract class AbstractStuff implements Stuff { ;
    String name;
    String description;
    boolean isUsable;
    boolean isEquipable;

    public AbstractStuff(String name, boolean isUsable, boolean isEquipable){
        this.isUsable = isUsable;
        this.isEquipable = isEquipable;
        this.name = name;
    }

    @Override
    public boolean isEquipable() {
        return isEquipable;
    }

    @Override
    public boolean isUsable() {
        return isUsable;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Coucou ceci est un stuff";
    }
}
