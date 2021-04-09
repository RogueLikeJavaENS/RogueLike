package spells;

public abstract class AbstractSpell implements Spell {
    String name;
    double damageMult;
    int range;
    String ingameDisplay;

    public AbstractSpell(String name, double damageMult, int range, String ingameDisplay) {
        this.name = name;
        this.damageMult = damageMult;
        this.range = range;
        this.ingameDisplay = ingameDisplay;
    }

    @Override
    public String toString() {
        return name;
    }

    public double getDamageMult() {
        return damageMult;
    }

    public int getRange() {
        return range;
    }

    public String getIngameDisplay() {
        return ingameDisplay;
    }
}
