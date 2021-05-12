package game.entity.living.spell;

import utils.Position;
import utils.Direction;

public abstract class AbstractSpell implements Spell {
    protected String name;
    protected int damage;
    protected double damageMult;
    protected Range range;
    protected int manaCost;

    public AbstractSpell(String name, double damageMult, int damage, Range range, int manaCost) {
        this.name = name;
        this.damageMult = damageMult;
        this.damage = damage;
        this.range = range;
        this.manaCost = manaCost;
    }

    @Override
    public int getDamage() {
        return (damage);
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

    public void setRange(Position entityPos, Direction direction) {
        setBottomRightCorner(entityPos, direction);
        setTopLeftCorner(entityPos, direction);
    }
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        range.setTopLeftCorner(entityPos);
    }

    public void setBottomRightCorner(Position entitiyPos, Direction direction) {
        range.setBottomRightCorner(entitiyPos);
    }
}
