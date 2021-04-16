package entity.living.monster;

import monsterStrategy.*;
import utils.Position;

import java.util.Random;


public class MonsterFactory {
    int floor;

    public final Condition isMidlife = (Monster monster) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointTotal()/2;

    public MonsterFactory(int floor){
        this.floor = floor;
    }

    private final Strategy goblinStrategy = new EscapeStrategy(isMidlife, new ApproachStrategy( new AttackStrategy(null)));
    private final Strategy skeletonStrategy = new ApproachStrategy(new AttackStrategy(null));

    public Monster getMonster(int monsterType, Position position){

        if (monsterType == MonsterType.SKELETON.ordinal()){

            return new Skeleton(position,"Skeleton", getLevel(), skeletonStrategy);
        }
        else if (monsterType == MonsterType.GOBLIN.ordinal()){
            return new Goblin(position, "Goblin", getLevel(), goblinStrategy);
        }

        ///// in order to create a monster you have to write
        // else if (monsterType == "typeOfMonster")
        // { return new ConstructMonster(); }

        return null;
    }

    private int getLevel() {
        Random GEN = new Random();
        return GEN.nextInt(floor+2) + 1;
    }
}
