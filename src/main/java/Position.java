public class Position {

    private int abs;
    private int ord;

    public Position(int abs, int ord){
        this.abs = abs;
        this.ord = ord;
    }

    public int getAbs() { return abs; }
    public int getOrd() { return ord; }

    public void setOrd(int ord) { this.ord = ord; }
    public void setAbs(int abs) { this.abs = abs; }
}
