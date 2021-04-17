package utils;

import java.util.Objects;

/**
 * This class permit to have an access and create a position for something.
 * The position composed by the abscissa and the ordinate of a point.
 * An abscissa and an ordinate are always positive
 *
 * @author Juliette
 * */


public class Position {

    private int abs;
    private int ord;

    /**
     * This constructor create a utils.Position with :
     * @param abs : value of the abscissa
     * @param ord : value of the ordinate
     * @throws IllegalArgumentException : if the abscissa or the ordinate is negative
     */
    public Position(int abs, int ord) throws IllegalArgumentException {
            this.abs = posCheck(abs);
            this.ord = posCheck(ord);
    }

    public int getAbs() { return abs; }
    public int getOrd() { return ord; }

    public void updatePos(int abs, int ord) {
        setAbs(this.abs + abs);
        setOrd(this.ord + ord);
    }

    public Position getPosInFront(Direction dir) {
        switch (dir) {
            case NORTH:
                return new Position(abs, ord-1);
            case SOUTH:
                return new Position(abs, ord+1);
            case EAST:
                return new Position(abs+1, ord);
            case WEST:
                return new Position(abs-1, ord);
            default:
                return new Position(0,0);
        }
    }

    /**
     * This private method permits to check if an argument (abscissa or ordinate) is valid (a positive int)
     */
    private int posCheck(int i){
        if (i < 0){
            Exception e = new IllegalArgumentException();
            e.printStackTrace();
            return 0;
        }
        else {return i;}
    }
    public void setOrd(int ord) throws IllegalArgumentException{
        try {
            this.ord = posCheck(ord);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
    public void setAbs(int abs) throws IllegalArgumentException{
        try {
            this.abs = posCheck(abs);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    public boolean equals(int abs, int ord) {
        return (abs == this.abs && ord == this.ord);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return abs == position.abs &&
                ord == position.ord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abs, ord);
    }

    @Override
    public String toString() {
        return "Position{" +
                "abs=" + abs +
                ", ord=" + ord +
                '}';
    }
}
