package generation;

import display.GridMap;
import entity.Entity;
import entity.object.Door;
import gameElement.Dungeon;
import gameElement.Room;
import monsterStrategy.StrategyUtils;
import utils.CoupleBoolPosition;
import utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerificationRoom {

    public static boolean verificationGenerationRoom(Room room, Dungeon dungeon){
        List<Entity> listRoomEntity = room.getEntities();
        GridMap gridMap = dungeon.getGridMap(room);
        List<Door> doorList = room.getDoors();
        List<Position> positionDoor = new ArrayList<>();
        for (Door door : doorList){
            positionDoor.add(door.getPosition());
        }
        List<Position> positionFrontDoor = positionFrontDoor(positionDoor,room);

        for(Position pos1 : positionFrontDoor){
            for (Position pos2 : positionFrontDoor){
                if (!pos1.equals(pos2)){
                    Position position = StrategyUtils.aStarAlgorithm(pos1,pos2,gridMap);
                    if (position == null){
                        Collections.shuffle(listRoomEntity);
                        Entity entityToRemove = listRoomEntity.remove(0);
                        room.getEntities().remove(entityToRemove);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<Position> positionFrontDoor(List<Position> doorList, Room room){
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

}
