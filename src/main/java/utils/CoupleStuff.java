package utils;

import game.stuff.Stuff;

import java.util.Objects;

/**
 * Couple of a stuff and a count of the stuff.
 */
public class CoupleStuff {
    private int count;
    private Stuff stuff;

    public CoupleStuff(Stuff stuff) {
        this.stuff = stuff;
        count = 1;
    }

    /**
     * Adds to the couple stuff the count of the stuff.
     */
    public void addStuff() {
        count++;
    }

    /**
     * Decrements the count of the stuff in the Couple.
     * @return if the stuff count equals 0 return false, and the CoupleStuff has to be destroyed
     */
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
