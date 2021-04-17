package entity.living.npc.monster;

import entity.living.player.Player;
import monsterStrategy.*;
import utils.Position;

import java.util.Random;


public class MonsterFactory {
    private int floor;
    private final static int AGRO = 5;

    public final Condition isMidlife = (Monster monster, Player player) -> monster.getMonsterStats().getLifePointActual() <= monster.getMonsterStats().getLifePointTotal()/2;
    public final Condition alwaysTrue = (Monster monster, Player player) -> true;
    public final Condition isFarFromPlayer = (Monster monster, Player player) -> StrategyUtils.getDistance(monster,player) > AGRO;

    public MonsterFactory(int floor){
        this.floor = floor;
    }

    private final Strategy goblinStrategy = new IdleStrategy(isFarFromPlayer, new EscapeStrategy(isMidlife, new ApproachStrategy(alwaysTrue, new AttackStrategy(null))));
    private final Strategy skeletonStrategy = new IdleStrategy(isFarFromPlayer, new ApproachStrategy(alwaysTrue, new AttackStrategy(null)));

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
