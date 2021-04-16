package entity.living.monster;

import display.GridMap;
import entity.living.LivingEntity;
import entity.living.Player;
import monsterStrategy.*;
import utils.Direction;
import utils.Position;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;


public class MonsterFactory {
    int floor;

    public final Condition isMidlife = (Monster monster) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointTotal()/2;

    public MonsterFactory(int floor){
        this.floor = floor;
    }


    public Monster getMonster(int monsterType, Position position){
        if (monsterType == 0){
            return new Skeleton( position, getLevel());
    private final Strategy goblinStrategy = new EscapeStrategy(isMidlife, new ApproachStrategy( new AttackStrategy(null)));
    private final Strategy skeletonStrategy = new ApproachStrategy(new AttackStrategy(null));


    public MonsterFactory(int level){ }
    public Monster getMonster(MonsterType monsterType, Position position, String name, int level){

        if (monsterType == MonsterType.SKELETON){

            return new Skeleton(position, name, level,skeletonStrategy);
        }
        else if (monsterType == MonsterType.GOBLIN){
            return new Goblin(position,name,level, goblinStrategy);
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
