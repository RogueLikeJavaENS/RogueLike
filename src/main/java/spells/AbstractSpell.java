package spells;

import utils.Position;
import utils.Direction;

public abstract class AbstractSpell implements Spell {
    String name;
    double damageMult;
    Range range;
    int manaCost;

    public AbstractSpell(String name, double damageMult, Range range, int manaCost) {
        this.name = name;
        this.damageMult = damageMult;
        this.range = range;
        this.manaCost = manaCost;
    }

    @Override
    public String toString() {
        return name;
    }

    public double getDamageMult() {
        return damageMult;
    }

    public Range getRange() {
        return range;
    }

    public int getManaCost() {
        return manaCost;
    }
}
