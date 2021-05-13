package game.entity.living.npc.monster.monsterStrategy;

import display.GridMap;
import game.tile.Tile;
import game.tile.tiles.DoorTile;
import game.entity.Entity;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.boss.Boss;
import game.entity.living.player.Player;
import utils.Check;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains different methods used for the strategies.
 */
public final class StrategyUtils {
    private StrategyUtils(){}

    /**
     * Returns the distance bewteen a Monster and the Player.
     *
     * @param monster the Monster
     * @param player the Player
     * @return the distance between the two parameters.
     */
    public static double getDistance(Monster monster, Player player){
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();
        return getDistance(monsterPos, playerPos);
    }

    /**
     * Returns wether a boss and the player are next to each other on the grid.
     *
     * @param boss the Boss
     * @param player the Player
     * @return true if the two parameters are next to each other, false otherwise.
     */
    public static boolean isNextToBoss(Boss boss, Player player) {
        Position bossPos = boss.getPosition();
        Position playerPos = player.getPosition();
        int absP = playerPos.getAbs();
        int ordP = playerPos.getOrd();
        int absB = bossPos.getAbs();
        int ordB = bossPos.getOrd();
        if (absP == absB || absP == absB+1) {
            return ordP >= ordB - 1 && ordP <= ordB + 2;
        }
        if (ordP == ordB || ordP == ordB+1) {
            return absP >= absB - 1 && absP <= absB + 2;
        }
        return false;
    }

    /**
     * Returns the distance between two given positions, using Math.hypot().
     *
     * @param firstPos the first position
     * @param secondPos the second position.
     * @return the distance between the two parameters.
     */
    public static double getDistance(Position firstPos, Position secondPos){
        int dx = firstPos.getAbs() - secondPos.getAbs();
        int dy = firstPos.getOrd() - secondPos.getOrd();
        return Math.hypot(dx,dy);
    }

    /**
     * Defines where a fleeing will go, and updates its position.
     *
     * @param monster the Monster that is running away
     * @param player the Player from which the Monster is running
     * @param gridMap the GridMap which contains the needed information to decide where to go
     */
    public static void moveAwayFromPlayer(Monster monster, Player player, GridMap gridMap){
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
        Random gen = new Random();
        Direction direction = resList.get(gen.nextInt(resList.size()));
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
        monster.setPosition(monsterPos);
    }

    /**
     * Decides where a Monster that is approaching a player must go next.
     * It calculates the shortest path if it exists using the A* algorithm, going from the Monster's position to the Player's.
     * If a path exists, updates the Monster's position to the first Node of the deduced path. If not, the monster will not move.
     *
     * @param monster the Monster from which the algorithm will start
     * @param player the Player to which the algorithm will try to go
     * @param gridMap the GridMap containing the information needed for the different calculations.
     */
    public static void aStarAlgorithmMonster(Monster monster, Player player, GridMap gridMap) {
        Position monsterPos = monster.getPosition();
        Position playerPos = player.getPosition();

        List<Node> openNodes = new ArrayList<>(); //liste des nodes qui sont candidates en tant que Node du chemin
        List<Node> closedNodes = new ArrayList<>(); //liste des nodes qu'on a déjà vérifié
        //List<Node> path = new ArrayList<>(); //liste des nodes qui constituent le chemin
        openNodes.add(new Node(monsterPos, playerPos)); //on ajoute la Node actuelle pour démarrer

        //tant qu'on a des Node à vérifier (donc un potentiel chemin)
        while (openNodes.size() > 0) {
            Node currentNode = getMostRelevantNode(openNodes); //on récupère celle qui semble être le meilleur choix
            openNodes.remove(currentNode);
            closedNodes.add(currentNode);

            //s'il s'agît d'une Node à moins de 1 de distance avec le joueur, c'est là qu'on doit aller
            if (getDistance(currentNode.getNodePos(), playerPos) <= 1) {
                //on remonte le chemin en passant par les parents
                while (!currentNode.getParentNode().getNodePos().equals(monsterPos)) {
                    //path.add(currentNode);
                    currentNode = currentNode.getParentNode();
                }
                monster.setPosition(currentNode.getNodePos()); //et on donne au monstre la position de la première node du chemin
                break;
            }

            //on récupère les directions possibles depuis la Node courante, donc les non-accessibles ne seront pas un problème
            List<Direction> possibleDir = foundAccessibleDirection(currentNode.getNodePos(), gridMap);

            Position newNodePos;
            for (Direction direction : possibleDir) {
                switch (direction) {
                    case NORTH:
                        try {
                            newNodePos = new Position(currentNode.getNodePos().getAbs(), Check.checkPositivity(currentNode.getNodePos().getOrd()-1));
                        } catch (IllegalArgumentException illegalArgumentException) { //si on sort de la map
                            newNodePos = currentNode.getNodePos();
                        }
                        break;
                    case WEST:
                        try {
                            newNodePos = new Position(Check.checkPositivity(currentNode.getNodePos().getAbs()-1), currentNode.getNodePos().getOrd());
                        } catch (IllegalArgumentException illegalArgumentException) { //si on sort de la map
                            newNodePos = currentNode.getNodePos();
                        }
                        break;
                    case SOUTH:
                        newNodePos = new Position(currentNode.getNodePos().getAbs(), currentNode.getNodePos().getOrd()+1);
                        break;
                    case EAST:
                        newNodePos = new Position(currentNode.getNodePos().getAbs()+1, currentNode.getNodePos().getOrd());
                        break;
                    default:
                        newNodePos = currentNode.getNodePos();
                }
                Node nextNode = new Node(newNodePos, playerPos, monsterPos, currentNode);
                if (containsNodeHere(closedNodes, newNodePos) != -1) {
                    continue;
                }
                int indexIfExists = containsNodeHere(openNodes, newNodePos);
                if (indexIfExists != -1) {
                    openNodes.get(indexIfExists).setParentNode(currentNode);
                } else {
                    openNodes.add(nextNode);
                }
            }
        }
    }

    /**
     * Picks a random position among those that are available around a Monster, and update the Monster's position.
     *
     * @param monster the Monster
     * @param gridMap the GridMap containing the information needed for the different calculations.
     */
    public static void moveRandomly(Monster monster, GridMap gridMap){
        Position monsterPos = monster.getPosition();
        Random gen = new Random();
        List<Direction> accessibleDirection = foundAccessibleDirection(monsterPos, gridMap);
        if (accessibleDirection.size() != 0){
            Direction direction = accessibleDirection.get(gen.nextInt(accessibleDirection.size()));
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
            monster.setPosition(monsterPos);
        }
    }

    /**
     * Determines which Directions are available from a given Position.
     * That means if the Position above the given one on the GridMap is obstructed, the NORTH Direction will not be available.
     *
     * @param position the Position from which the Directions are checked.
     * @param gridMap the GridMap containing the information needed for the different calculations.
     * @return the List of available Directions from the given Position.
     */
    public static List<Direction> foundAccessibleDirection(Position position,GridMap gridMap){
        List<Direction> accessibleDirection = new ArrayList<>();
        List<Entity> entityList;
        Tile tile;

        int abs = position.getAbs();
        int ord = position.getOrd();

        entityList = gridMap.getEntitiesAt(abs+1,ord);
        tile = gridMap.getTileAt(abs+1,ord);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            accessibleDirection.add(Direction.EAST);
        }
        entityList = gridMap.getEntitiesAt(abs-1,ord);
        tile = gridMap.getTileAt(abs-1,ord);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            accessibleDirection.add(Direction.WEST);
        }
        entityList = gridMap.getEntitiesAt(abs,ord+1);
        tile = gridMap.getTileAt(abs,ord+1);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            accessibleDirection.add(Direction.SOUTH);
        }
        entityList = gridMap.getEntitiesAt(abs,ord-1);
        tile = gridMap.getTileAt(abs,ord-1);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            accessibleDirection.add(Direction.NORTH);
        }

        return accessibleDirection;
    }

    /**
     * Opposite to the foundAccessibleDirection method. This one determines Directions that are NOT available from a given Position.
     *
     * @param position the Position from which the Directions are checked.
     * @param gridMap the GridMap containing the information needed for the different calculations.
     * @return the List of unavailable Directions from the given Position.
     */
    public static List<Direction> foundInaccessibleDirection(Position position,GridMap gridMap){
        List<Direction> inaccessibleDirection = new ArrayList<>();
        inaccessibleDirection.add(Direction.NORTH);
        inaccessibleDirection.add(Direction.EAST);
        inaccessibleDirection.add(Direction.SOUTH);
        inaccessibleDirection.add(Direction.WEST);
        List<Entity> entityList;
        Tile tile;

        int abs = position.getAbs();
        int ord = position.getOrd();

        entityList = gridMap.getEntitiesAt(abs+1,ord);
        tile = gridMap.getTileAt(abs+1,ord);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            inaccessibleDirection.remove(Direction.EAST);
        }
        entityList = gridMap.getEntitiesAt(abs-1,ord);
        tile = gridMap.getTileAt(abs-1,ord);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            inaccessibleDirection.remove(Direction.WEST);
        }
        entityList = gridMap.getEntitiesAt(abs,ord+1);
        tile = gridMap.getTileAt(abs,ord+1);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            inaccessibleDirection.remove(Direction.SOUTH);
        }
        entityList = gridMap.getEntitiesAt(abs,ord-1);
        tile = gridMap.getTileAt(abs,ord-1);
        if (tile.isNPCAccessible() && !(tile instanceof DoorTile) &&
                (entityList.size() == 0 || entityList.get(0).getIsNPCAccessible())){
            inaccessibleDirection.remove(Direction.NORTH);
        }

        return inaccessibleDirection;
    }

    /**
     * Used for the A* algorithm.
     * Determines which Node is the most relevant to pick to continue the algorithm.
     *
     * @param list List of Nodes from which the most relevant Node will be picked
     * @return the most relevant Node from the given List.
     */
    private static Node getMostRelevantNode(List<Node> list) {
        Node bestNode = list.get(0);
        for (Node currentNode : list) {
            if (currentNode.getRelevance() < bestNode.getRelevance()) {
                bestNode = currentNode;
            }
        }
        return bestNode;
    }

    /**
     * Checks if the given list contains a Node with the same position as the given one.
     *
     * @param nodeList list of Nodes to check
     * @param position position we're looking at
     * @return the index of the Node in the list if there's one, -1 otherwise
     *
     * @author Raphael
     */
    private static int containsNodeHere(List<Node> nodeList, Position position) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node currentNode = nodeList.get(i);
            if (position.equals(currentNode.getNodePos())) {
                return i;
            }
        }
        return -1;
    }
}
