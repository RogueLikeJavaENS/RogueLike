package entity.living.npc.monster;

import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;


public class Skeleton extends AbstractMonster {
    private final static int basicHP = 50;
    private final static int basicMP = 10;
    private final static int hpModifier = 5;
    private final static int mpModifier = 1;


    public Skeleton(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.WHITE, strategy, new MonsterStats(basicHP+(hpModifier*level),basicMP+(mpModifier*level),1,2,(4+level),(4+level),5,level,(5*level)));
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add("_#_");
        sprites.add("/ \\");
        setBasicSprites(sprites);
        setSprites(sprites);
    }

    @Override
    public String toString() {
        return "+++";
    }



}