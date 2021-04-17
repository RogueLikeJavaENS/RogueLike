package monsterStrategy;

import display.GridMap;
import display.Tile;
import display.tiles.DoorTile;
import entity.Entity;
import entity.living.monster.Monster;
import entity.living.Player;
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

    public static Direction goToPlayerDir(Monster monster, Player player, GridMap gridMap){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();

        Direction absDir;
        Direction ordDir;

        List<Direction> possibleDir = foundAccessibleDirection(monsterPos,gridMap);
        Collections.shuffle(possibleDir);
        List<Direction> resList = new ArrayList<>(possibleDir);
        for(Direction dir : possibleDir) {
            switch (dir) {
                case NORTH:
                case SOUTH:
                    if (monsterPos.getOrd() > playerPos.getOrd()) {
                        resList.remove(Direction.SOUTH);
                    } else if (monsterPos.getOrd() < playerPos.getOrd()) {
                        resList.remove(Direction.NORTH);
                    }
                    break;
                case EAST:
                case WEST:
                    if (monsterPos.getAbs() > playerPos.getAbs()) {
                        resList.remove(Direction.EAST);
                    } else if (monsterPos.getAbs() < playerPos.getAbs()) {
                        resList.remove(Direction.WEST);
                    }
                    break;
            }
        }
        if (resList.size() == 0){
            resList.add(Direction.NONE);
        }

        return resList.get(0);
    }

    public static Direction escapePlayerDir(Monster monster, Player player, GridMap gridMap){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();

        Direction absDir;
        Direction ordDir;

        List<Direction> possibleDir = foundAccessibleDirection(monsterPos,gridMap);
        Collections.shuffle(possibleDir);
        List<Direction> resList = new ArrayList<>(possibleDir);

        for(Direction dir : possibleDir) {
            switch (dir) {
                case NORTH:
                case SOUTH:
                    if (monsterPos.getOrd() > playerPos.getOrd()) {
                        resList.remove(Direction.NORTH);
                    } else if (monsterPos.getOrd() < playerPos.getOrd()) {
                        resList.remove(Direction.SOUTH);
                    }
                    break;
                case EAST:
                case WEST:
                    if (monsterPos.getAbs() > playerPos.getAbs()) {
                        resList.remove(Direction.WEST);
                    } else if (monsterPos.getAbs() < playerPos.getAbs()) {
                        resList.remove(Direction.EAST);
                    }
                    break;
            }
        }
        if (resList.size() == 0){
            resList.add(Direction.NONE);
        }
        return resList.get(0);
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
                (entityList.size() == 0 || entityList.get(entityList.size()-1).getIsAccessible())){
            accessibleDirection.add(Direction.EAST);
        }
        entityList = gridMap.getEntitiesAt(abs-1,ord);
        tile = gridMap.getTileAt(abs-1,ord);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(entityList.size()-1).getIsAccessible())){
            accessibleDirection.add(Direction.WEST);
        }
        entityList = gridMap.getEntitiesAt(abs,ord+1);
        tile = gridMap.getTileAt(abs,ord+1);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(entityList.size()-1).getIsAccessible())){
            accessibleDirection.add(Direction.SOUTH);
        }
        entityList = gridMap.getEntitiesAt(abs,ord-1);
        tile = gridMap.getTileAt(abs,ord-1);
        if (tile.isAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(entityList.size()-1).getIsAccessible())){
            accessibleDirection.add(Direction.NORTH);
        }

        return accessibleDirection;
    }
}
