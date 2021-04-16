package entity.living.monster;

import utils.Position;

import java.util.ArrayList;

public class Skeleton extends AbstractMonster {
    private final static int basicHP = 5;
    private final static int basicMP = 1;


    public Skeleton(Position position, int level) {
        super(position, basicHP*level,basicMP*level, "Skeleton", level);
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add("_#_");
        sprites.add("/ \\");
        setSprites(sprites);
    }

    @Override
    public String toString() {
        return "+++";
    }
}
