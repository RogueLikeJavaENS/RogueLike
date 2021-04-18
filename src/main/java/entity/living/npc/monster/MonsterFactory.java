package entity.living.npc.monster;

import entity.living.player.Player;
import monsterStrategy.*;
import utils.Position;

import java.util.Random;

/**
 * This class permit to create a Monster with a Strategy
 *
 */
public class MonsterFactory {
    private int floor;
    private final static int AGRO = 5; // Distance of AGRO

    // Create Condition to use for a strategy
    // public final Condition nameCondition = (Monster monster, Player player) -> a condition which return a boolean
    public final Condition isMidlife = (Monster monster, Player player) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointTotal()/2;
    public final Condition alwaysTrue = (Monster monster, Player player) -> true;
    public final Condition isFarFromPlayer = (Monster monster, Player player) -> StrategyUtils.getDistance(monster,player) > AGRO;

    // Create a Strategy
    // private final Strategy nameStrategy = new ...Strategy(Condition, new Strategy(...))
    private final Strategy goblinStrategy = new IdleStrategy(isFarFromPlayer, new EscapeStrategy(isMidlife, new ApproachStrategy(alwaysTrue, new AttackStrategy(null))));
    private final Strategy skeletonStrategy = new IdleStrategy(isFarFromPlayer, new ApproachStrategy(alwaysTrue, new AttackStrategy(null)));


    /**
     * Create a monster factory with the parameter floor
     * @param floor floor where the player is
     */
    public MonsterFactory(int floor){
        this.floor = floor;
    }

    /**
     * Create a monster with a int choose to describe the type of the monster and a position
     *
     * @param monsterType an int used to describe the monster
     * @param position the position where the monster is create
     * @return the monster created
     */
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

    /**
     * Return the level of the monster create randomly around the floor of the dungeon
     * @return an int, the level of the monster
     */
    private int getLevel() {
        Random GEN = new Random();
        return GEN.nextInt(floor+2) + 1;
    }
}
