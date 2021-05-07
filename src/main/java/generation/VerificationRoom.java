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


    /**
     * Verify if there is a path between all the entities use in the game
     *
     * @param room room to verify
     * @param dungeon dungeon to verify
     */
    public static void verificationGenerationRoom( Dungeon dungeon, GridMap gridMap){


        List<Position> positionToVerify = new ArrayList<>();

        //GridMap gridMap = dungeon.getGridMap(room);
        Room room = gridMap.getRoom();
        System.out.println("bonjour "+room.getRoomType()+" "+room.getDoors().size());

        // Get position of the doors
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
        System.out.println("Base Position"+basePosition);

        positionToVerify.addAll(positionOfNeededEntity(room));
        System.out.println("Pos to verify : "+positionToVerify);
        if (positionToVerify.size() == 0) return;

        for (Position pos : positionToVerify){
            //if (gridMap.getEntitiesAt(pos.getAbs(),pos.getOrd()).size() == 0){break;}
            System.out.println("Current Pos : "+pos);
            boolean isOkay = false;
            while (true){ // while there is no path continue to remove
                Position position = StrategyUtils.aStarAlgorithm(basePosition,pos,gridMap);
                System.out.println("Position : "+position);
                if (position == null){ // No path between the entities
                    System.out.println("pos non available");
                    Entity entityToRemove = removeSpikeOrHole(room);
                    break;
                    //if (entityToRemove == null){ // No spike or Hole to remove, stop the verification
                        //System.out.println("quit");
                        //return;
                    //}
                    //else { // remove the chosen entity
                        //room.removeEntity(entityToRemove);
                        //System.out.println("Entity List"+room.getEntities());
                        //gridMap.update(entityToRemove,false);
                        //System.out.println("GridMap List"+gridMap.getEntities());
                    //}
                }
                else { // path between the entities
                    //isOkay = true;
                    break;
                }
            }
        }

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
            if (entity instanceof Spike || entity instanceof Hole){
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

    /**
     * Return the list of positions all the entity which need to be accessible
     *
     * @param room the where are the entity
     * @return the list of the positions
     */
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
