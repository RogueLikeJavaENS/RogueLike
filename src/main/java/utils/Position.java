package utils;

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
     * @param abs : value of the absissa
     * @param ord : value of the ordinate
     * @throws IllegalArgumentException : if the absissa or the ordinate is negative
     */
    public Position(int abs, int ord) throws IllegalArgumentException {
            this.abs = posCheck(abs);
            this.ord = posCheck(ord);
    }

    public int getAbs() { return abs; }
    public int getOrd() { return ord; }

    public void setOrd(int ord) throws IllegalArgumentException{
        this.ord = posCheck(ord);
    }
    public void setAbs(int abs) throws IllegalArgumentException{
        this.abs = posCheck(abs);
    }

    /**
     * This private method permit to check if an argument (abscissa or ordinate) is valid (a positive int)
     * */
    private int posCheck(int i){
        if (i < 0){throw new IllegalArgumentException();}
        else {return i;}
    }
}
