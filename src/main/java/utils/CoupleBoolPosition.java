package utils;

public class CoupleBoolPosition {
    private boolean bool;
    private Position pos;

    public CoupleBoolPosition(boolean bool, Position pos){
        this.bool = bool;
        this.pos = pos;
    }

    public Position getPos() { return pos; }
    public boolean getBool(){ return bool;}

    @Override
    public String toString() {
        return "CoupleBoolPosition{" +
                "bool=" + bool +
                ", pos=" + pos +
                "}\n";
    }
}
