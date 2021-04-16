package entity.living.monster;

import utils.Position;

import java.util.Random;

public class MonsterFactory {
    int floor;

    public MonsterFactory(int floor){
        this.floor = floor;
    }


    public Monster getMonster(int monsterType, Position position){
        if (monsterType == 0){
            return new Skeleton( position, getLevel());
        }
        else if (monsterType == 1){
            return new Goblin(position, getLevel());
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
