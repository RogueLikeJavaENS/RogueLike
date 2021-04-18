package monsterStrategy;

import display.GridMap;
import display.Tile;
import display.tiles.DoorTile;
import entity.Entity;
import entity.living.npc.monster.Monster;
import entity.living.player.Player;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

                break;
            case WEST:
                monsterPos.updatePos(-1,0);
                break;
            case NORTH:
                monsterPos.updatePos(0,-1);
                break;
            case SOUTH:
                monsterPos.updatePos(0,1);
                break;
            default:
                break;
        }

    }

    public static Direction moveAroundPlayer(boolean approach, Monster monster, Player player, GridMap gridMap){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();

        List<Direction> possibleDir = foundAccessibleDirection(monsterPos,gridMap);
        Collections.shuffle(possibleDir);
        List<Direction> resList = new ArrayList<>(possibleDir);

        for(Direction dir : possibleDir) {
            switch (dir) {
                case NORTH:
                case SOUTH:
                    if (monsterPos.getOrd() > playerPos.getOrd()) {
                        if (approach) {
                            resList.remove(Direction.SOUTH);
                        }
                        else {
                            resList.remove(Direction.NORTH);
                        }
                    } else if (monsterPos.getOrd() < playerPos.getOrd()) {
                        if (approach){
                            resList.remove(Direction.NORTH);
                        }
                        else {
                            resList.remove(Direction.SOUTH);
                        }
                    } else {
                        if (approach){
                            resList.remove(Direction.SOUTH);
                            resList.remove(Direction.NORTH);
                        }
                    }
                    break;
                case EAST:
                case WEST:
                    if (monsterPos.getAbs() > playerPos.getAbs()) {
                        if (approach) {
                            resList.remove(Direction.EAST);
                        }
                        else {
                            resList.remove(Direction.WEST);
                        }
                    } else if (monsterPos.getAbs() < playerPos.getAbs()) {
                        if (approach){
                            resList.remove(Direction.WEST);
                        }
                        else {
                            resList.remove(Direction.EAST);
                        }
                    } else {
                        if (approach){
                            resList.remove(Direction.WEST);
                            resList.remove(Direction.EAST);
                        }
                    }
                    break;
            }
        }
        if (resList.size() == 0){
            resList.add(Direction.NONE);
        }
        Random gen = new Random();
        return resList.get(gen.nextInt(resList.size()));
    }

    public static Direction moveRandomly(Monster monster, GridMap gridMap){
        Random gen = new Random();
        List<Direction> accessibleDirection = foundAccessibleDirection(monster.getPosition(), gridMap);
        return accessibleDirection.get(gen.nextInt(accessibleDirection.size()));
    }


    private static int absDistance(Position pos1, Position pos2){
        return Math.abs(pos1.getAbs()-pos2.getAbs());
    }

    private static int ordDistance(Position pos1, Position pos2){
        return Math.abs(pos1.getOrd()-pos2.getOrd());
    }

    public static List<Direction> foundAccessibleDirection(Position position,GridMap gridMap){
        List<Direction> accessibleDirection = new ArrayList<>();
        List<Entity> entityList;
        Tile tile;

        int abs = position.getAbs();
        int ord = position.getOrd();

        entityList = gridMap.getEntitiesAt(abs+1,ord);
        tile = gridMap.getTileAt(abs+1,ord);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsAccessible())){
            accessibleDirection.add(Direction.EAST);
        }
        entityList = gridMap.getEntitiesAt(abs-1,ord);
        tile = gridMap.getTileAt(abs-1,ord);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsAccessible())){
            accessibleDirection.add(Direction.WEST);
        }
        entityList = gridMap.getEntitiesAt(abs,ord+1);
        tile = gridMap.getTileAt(abs,ord+1);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsAccessible())){
            accessibleDirection.add(Direction.SOUTH);
        }
        entityList = gridMap.getEntitiesAt(abs,ord-1);
        tile = gridMap.getTileAt(abs,ord-1);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsAccessible())){
            accessibleDirection.add(Direction.NORTH);
        }

        return accessibleDirection;
    }
}
