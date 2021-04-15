package monsterStrategy;

import entity.living.monster.Monster;
import entity.living.Player;
import utils.Direction;
import utils.Position;

import java.util.Random;

public final class StrategyUtils {
    private StrategyUtils(){}

    public static double getDistance(Monster monster, Player player){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();
        int dx = monsterPos.getAbs() - playerPos.getAbs();
        int dy = monsterPos.getOrd() - playerPos.getOrd();
        return Math.hypot(dx,dy);
    }

    public static void updatePos(Monster monster, Direction direction) {
        Position monsterPos = monster.getPosition();
        switch (direction){
            case EAST:
                monsterPos.updatePos(1,0);
            case WEST:
                monsterPos.updatePos(-1,0);
            case NORTH:
                monsterPos.updatePos(0,-1);
            case SOUTH:
                monsterPos.updatePos(0,1);
        }
    }

    public static Direction goToPlayerDir(Monster monster, Player player){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();
        Direction absDir;
        Direction ordDir;
        if (monsterPos.getOrd() > playerPos.getOrd()){ ordDir = Direction.NORTH; }
        else if (monsterPos.getOrd() == playerPos.getOrd()) {ordDir = null;}
        else { ordDir = Direction.SOUTH; }
        if (monsterPos.getAbs() > playerPos.getAbs()){absDir = Direction.EAST;}
        else if (monsterPos.getAbs() == monsterPos.getAbs()) {absDir = null;}
        else {absDir = Direction.WEST;}

        Direction res = null;
        if (absDir == null) {res = ordDir;}
        else if (ordDir == null) {res = absDir;}
        else {
            Random gen = new Random();
            boolean choice = gen.nextBoolean();
            if (choice == true) {res = ordDir;}
            else { res = absDir;}
        }
        return res;
    }

}
