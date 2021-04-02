package entity.living.monster;

import utils.Position;

public class MonsterFactory {

    public Monster getMonster(int monsterType, Position position, String name, int level){
        if (monsterType == 1){
            return new Skeleton( position, name, level);
        }

        ///// in order to create a monster you have to write
        // else if (monsterType == "typeOfMonster")
        // { return new ConstructMonster(); }

        return null;
    }

}
