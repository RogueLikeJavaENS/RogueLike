package generation;

import display.GridMap;
import entity.Entity;
import entity.living.npc.merchants.Merchant;
import entity.living.npc.monster.Monster;
import entity.object.Chest;
import entity.object.Door;
import entity.object.Hole;
import entity.object.Spike;
import gameElement.Dungeon;
import gameElement.Room;
import monsterStrategy.StrategyUtils;
import utils.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerificationRoom {

    public static void verificationGenerationRoom(Room room, Dungeon dungeon){
        List<Entity> listRoomEntity = room.getEntities();
        List<Position> positionToVerify = new ArrayList<>();
        GridMap gridMap = dungeon.getGridMap(room);

        // Get position of the door
        List<Door> doorList = room.getDoors();
        List<Position> positionDoor = new ArrayList<>();
        for (Door door : doorList){
            positionDoor.add(door.getPosition());
        }
        List<Position> positionFrontDoor = positionFrontDoor(positionDoor,room);
        Position basePosition = positionFrontDoor.get(0);

        // Set position to verify
        positionToVerify.addAll(positionFrontDoor);
        positionToVerify.remove(basePosition);
        positionToVerify.addAll(positionOfNeededEntity(room));
        for (Position pos : positionToVerify){
            if (gridMap.getEntitiesAt(pos.getAbs(),pos.getOrd()).size() == 0){break;}
            boolean isOkay = false;
            while (isOkay == false){
                Position position = StrategyUtils.aStarAlgorithm(basePosition,pos,gridMap);
                if (position == null){
                    Entity entityToRemove = removeSpikeOrHole(room);
                    room.getEntities().remove(entityToRemove);
                    Position posEntityToRemove = entityToRemove.getPosition();
                    gridMap.getEntitiesAt(posEntityToRemove.getAbs(), posEntityToRemove.getOrd()).remove(entityToRemove);
                }
                else {
                    isOkay = true;
                }
            }
        }
    }

    private static Entity removeSpikeOrHole(Room room){
        List<Entity> roomEntity = room.getEntities();
        Collections.shuffle(roomEntity);
        while (!(roomEntity.get(0) instanceof Spike) && !(roomEntity.get(0) instanceof Hole)){
            Collections.shuffle(roomEntity);
        }
        Entity entityToRemove = roomEntity.get(0);
        return entityToRemove;
    }

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

    private static List<Position> positionOfNeededEntity(Room room){
        List<Entity> entityList = room.getEntities();
        List<Position> entityPositions = new ArrayList<>();
        for (Entity entity : entityList){
            if (entity instanceof Monster || entity instanceof Merchant || entity instanceof Chest){
                entityPositions.add(entity.getPosition());
            }
        }
        return entityPositions;
    }
}
