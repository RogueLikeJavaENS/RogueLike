package entity.living.monster;

import monsterStrategy.ApproachStrategy;
import monsterStrategy.AttackStrategy;
import monsterStrategy.EscapeStrategy;
import monsterStrategy.Strategy;
import utils.Position;

public class MonsterFactory {
    private int level;


    private static boolean midLifeOrLess = (Monster monster) -> monster.getMonsterStats().getLifePoint() < monster.getMonsterStats().getMaxLifePoint()/2;



    private static final Strategy skeletonStrategy =
            new ApproachStrategy(
                    new AttackStrategy()
            );

    private static final Strategy goblinStrategy =
            new EscapeStrategy(midLifeOrLess, new ApproachStrategy( new AttackStrategy()));


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
