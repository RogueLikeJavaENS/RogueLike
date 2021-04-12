package entity.living.monster;

import utils.Position;

public class MonsterFactory {
    int level;

    public MonsterFactory(int level){
        this.level = level;
    }
    public Monster getMonster(int monsterType, Position position, String name){
        if (monsterType == 0){
            return new Skeleton( position, name, level);
        }
        else if (monsterType == 1){
            return new Goblin(position,name,level);
        }

        ///// in order to create a monster you have to write
        // else if (monsterType == "typeOfMonster")
        // { return new ConstructMonster(); }

        return null;
    }

}
