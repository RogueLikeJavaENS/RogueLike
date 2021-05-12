package utils;

import game.stuff.Stuff;

import java.util.Objects;

public class CoupleStuff {
    private int count;
    private Stuff stuff;

    public CoupleStuff(Stuff stuff) {
        this.stuff = stuff;
        count = 1;
    }

    public void addStuff() {
        count++;
    }

    public boolean removeStuff() {
        count --;
        return !(count > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Stuff) {
            Stuff o1 = (Stuff) o;
            return o1 == this.stuff;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(stuff);
    }

    public int getCount() {
        return count;
    }

    public Stuff getStuff() {
        return stuff;
    }
}
