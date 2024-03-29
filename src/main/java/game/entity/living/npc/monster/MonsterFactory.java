package game.entity.living.npc.monster;

import game.entity.living.npc.monster.monsters.*;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.monsterStrategy.*;
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
    public final Condition isVeryLowLife = (Monster monster, Player player) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointNatural()/5;
    public final Condition isMidlife = (Monster monster, Player player) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointNatural()/2;
    public final Condition alwaysTrue = (Monster monster, Player player) -> true;
    public final Condition isFarFromPlayer = (Monster monster, Player player) -> StrategyUtils.getDistance(monster,player) > AGRO;

    // Create a Strategy
    // private final Strategy nameStrategy = new ...Strategy(Condition, new Strategy(...))
    private final Strategy goblinStrategy = new IdleStrategy(isFarFromPlayer, new EscapeStrategy(isMidlife, new ApproachStrategy(alwaysTrue, new AttackStrategy(null))));
    private final Strategy classicAttackStrategy = new IdleStrategy(isFarFromPlayer, new ApproachStrategy(alwaysTrue, new AttackStrategy(null)));
    //private final Strategy wizardStrategy = new IdleStrategy(isFarFromPlayer, new EscapeStrategy(isVeryLowLife, new ApproachStrategy(alwaysTrue,new AttackStrategy(null))));

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
            return new Skeleton(position,"Skeleton", getLevel(), classicAttackStrategy);
        }
        else if (monsterType == MonsterType.GOBLIN.ordinal()){
            return new Goblin(position, "Goblin", getLevel(), goblinStrategy);
        }
        else if (monsterType == MonsterType.BAT.ordinal()){
            return new Bat(position, "Bat", getLevel(), classicAttackStrategy);
        }
        else if(monsterType == MonsterType.ZOMBIE.ordinal()){
            return new Zombie(position, "Zombie", getLevel(), classicAttackStrategy);
        }
        else if(monsterType == MonsterType.ORC.ordinal()){
            return new Orc(position, "Orc", getLevel(), classicAttackStrategy);
        }
        else if(monsterType == MonsterType.VAMPIRE.ordinal()) {
            return new Vampire(position, "Vampire", getLevel(), classicAttackStrategy);
        }
        else if (monsterType == MonsterType.WIZARD.ordinal()){
            return new Wizard(position, "Wizard", getLevel(), classicAttackStrategy);
        }
        else if (monsterType == MonsterType.MIMIC.ordinal()) {
            return new Mimic(position, "Mimic", getLevel(), classicAttackStrategy);

        } else if (monsterType == MonsterType.MERCHANT.ordinal()) {
            return  new MerchantMonster(position, "Angry Merchant", getLevel(), classicAttackStrategy);
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
        return GEN.nextInt(2) + (3*floor)-2;
    }
}
