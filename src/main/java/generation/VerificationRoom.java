package generation;

import display.GridMap;
import game.tile.TileEnum;
import game.entity.Entity;
import game.entity.object.elements.Coins;
import game.elements.Room;
import utils.Dijksrta;
import utils.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerificationRoom {

    /**
     * Verify if there is a path between all the entities use in the game
     *
     * @param gridMap gridMap to verify
     */
    public static boolean verificationGenerationRoom(GridMap gridMap){
        Dijksrta dij = new Dijksrta(gridMap);
        Room room = gridMap.getRoom();

        List<Position> positionOfEntityList = positionOfNeededEntity(room);
        Position startDoorPos = room.getDoors().get(0).getPosition();
        positionOfEntityList.remove(startDoorPos);
        Position basePosition = positionAround(startDoorPos, room).get(0);
        positionOfEntityList.add(room.getCenter());
        List<Position> positionAroundList;

        int debug = 0;
        for (Position entityPos: positionOfEntityList) {
            positionAroundList = positionAround(entityPos,room);
            boolean isOkay = dij.isThereAPath(basePosition, positionAroundList);

            while (!isOkay){
                debug++;
                Entity entityToRemove = removeSpikeOrHole(room);
                if (entityToRemove != null){
                    room.removeEntity(entityToRemove);
                    gridMap.update(entityToRemove,false);
                    dij.update(gridMap, entityToRemove.getPosition());
                }
                isOkay = dij.isThereAPath(basePosition, positionAroundList);
                if (debug> 150){  // génération buggé
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Return a Spike or a Hole to remove
     *
     * @param room room where we need to remove a Spike or a Hole
     * @return the object to remove
     */
    private static Entity removeSpikeOrHole(Room room){
        List<Entity> roomEntity = room.getEntities();
        Collections.shuffle(roomEntity);
        Entity entityToRemove = null;
        for (Entity entity : roomEntity){
            if (entity.isTrap()){
                entityToRemove = entity;
                break;
            }
        }
        return entityToRemove;
    }

    /**
     * Give the list of position in front of the doors
     *
     * @param doorList the list of the door
     * @param room the room where are the doors
     * @return the list of position of the front
     */
    private static List<Position> positionFrontDoor(List<Position> doorList, Room room){
        List<Position> positionList= new ArrayList<>();
        for (Position pos : doorList){
            if (pos.getAbs() == room.getWidth()/2){
                if (pos.getOrd() == 0){
                    positionList.add(new Position(pos.getAbs(),1));
                }
                else if (pos.getOrd() == room.getHeight()-1){
                    positionList.add(new Position(pos.getAbs(), pos.getOrd()-1));
                }
            }
            else if(pos.getAbs() == 0){
                positionList.add(new Position(1, pos.getOrd()));
            }
            else if (pos.getAbs() == room.getWidth()-1){
                positionList.add(new Position(pos.getAbs()-1,pos.getOrd()));
            }
        }
        return positionList;
    }

    private static List<Position> positionAround(Position position, Room room){
        List<Position> positionAround = new ArrayList<>();
        int abs = position.getAbs();
        int ord = position.getOrd();
        if (abs !=0 && room.getContents()[ord][abs-1] == TileEnum.FLOOR.getId()){
            positionAround.add(new Position(abs-1, ord));
        }
        if (abs !=room.getWidth()-1 && room.getContents()[ord][abs+1] == TileEnum.FLOOR.getId()){
            positionAround.add(new Position(abs+1, ord));
        }
        if (ord !=0 && room.getContents()[ord-1][abs] == TileEnum.FLOOR.getId()){
            positionAround.add(new Position(abs, ord-1));
        }
        if (ord !=room.getHeight()-1 && room.getContents()[ord+1][abs] == TileEnum.FLOOR.getId()){
            positionAround.add(new Position(abs, ord+1));
        }
        return positionAround;
    }

    /**
     * Return the list of positions all the game.entity which need to be accessible
     *
     * @param room the where are the game.entity
     * @return the list of the positions
     */
    private static List<Position> positionOfNeededEntity(Room room){
        List<Entity> entityList = room.getEntities();
        List<Position> entityPositions = new ArrayList<>();
        for (Entity entity : entityList){
            if (!entity.isTrap() && !(entity instanceof Coins)){
                entityPositions.add(entity.getPosition());
            }
        }
        return entityPositions;
    }
}
