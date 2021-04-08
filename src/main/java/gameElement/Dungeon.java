package gameElement;

import display.GridMap;
import entity.Entity;
import entity.monster.Monster;
import entity.monster.MonsterFactory;
import entity.object.Coins;
import entity.object.Door;
import utils.Direction;
import utils.Position;

import java.util.*;

/**
 * This class is graph representation of the dungeon.
 * 1 room can be connected by min 1 room and max 4 rooms.
 *
 * @author Antoine
 */

public class Dungeon {
    private List<Room> roomList;
    private int width; // width -> max numbers of rooms in a column
    private int height;  // height -> max numbers of rooms in a row
    private GraphDungeon graph;
    private List<GridMap> gridMapList; // list of gridMap, index by room number.

    /**
     * List of List of roomNumber.
     * Index 0 -> North,
     *       1 -> East,
     *       2 -> South,
     *       3 -> West.
     */

    public Dungeon(List<Room> roomList, int width, int height, GraphDungeon graph) {
        this.roomList = roomList;
        this.graph = graph;
        this.width = width;
        this.height = height;
        placeTestCoins();
        createMonster();
        createAllDoor();
        setAllNextDoor();
        initGridMapList();
    }

    private void initGridMapList(){
        this.gridMapList = new ArrayList<>();
        for (Room room : roomList) {
            gridMapList.add(new GridMap(room));
        }
    }

    private void placeTestCoins(){
        for (Room room : roomList){
            room.addEntity(new Coins(new Position(3, 3)));
        }
    }

    private void createMonster(){
        Random GEN = new Random(); // Use the seed
        for (Room room : roomList){
            int nbMonster = GEN.nextInt(5); // A determiner
            for (int i=0; i<nbMonster; i++){
                int monsterType = GEN.nextInt(2); // Use the seed
                int abs = GEN.nextInt(room.getWidth()-1)+1; // Use the seed
                int ord = GEN.nextInt(room.getHeight()-1)+1; // Use the seed
                Position monsterPos = new Position(abs,ord);
                String name = "Skeleton";
                int level = 1;
                Monster monster = MonsterFactory.getMonster(monsterType,monsterPos,name, level);
                room.addEntity(monster);
            }
        }
    }

    private void createAllDoor(){
        for (Room room : roomList) {
            int[] nearRoom = room.getNearRoom();
            for (int j = 0; j < nearRoom.length; j++) {
                if (nearRoom[j] != -1) {
                    Door door = new Door(getDoorPosition(j, room), getRoom(nearRoom[j]), Direction.intToDirection(j));
                    room.addEntity(door);
                }
            }
        }
    }

    private void setAllNextDoor() {
        for (Room room : roomList) {
            int[] nearRoom = room.getNearRoom();
            for (int j = 0; j < nearRoom.length; j++) {
                if (nearRoom[j] != -1) {
                    Position posDoor = getDoorPosition(j,room);
                    Door door = getDoorAt(posDoor,room);
                    Room nextRoom = getRoom(nearRoom[j]);
                    Position posNextDoor = getDoorPosition((j+2)%4,nextRoom);
                    Door nextDoor = getDoorAt(posNextDoor, nextRoom);
                    assert door != null;
                    door.setNext(nextDoor);
                }
            }
        }
    }

    private Door getDoorAt(Position position, Room room){
        List<Entity> entityList = room.getEntities();
        for (Entity entity: entityList) {
            if (entity.getPosition().equals(position)){
                return (Door) entity;
            }
        }
        return null;
    }

    private Position getDoorPosition(int dir, Room room){
        int[][] roomContent = room.getContents();
        Position pos = new Position(0,0);
        switch (dir){
            case 0:
                for (int i = 0; i < room.getWidth(); i++){
                    if (roomContent[0][i] == 3){
                        pos.setAbs(i);
                        pos.setOrd(0);
                        break;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < room.getHeight(); i++){
                    if (roomContent[i][room.getWidth()-1] == 3){
                        pos.setAbs(room.getWidth()-1);
                        pos.setOrd(i);
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < room.getWidth(); i++){
                    if (roomContent[room.getHeight()-1][i] == 3){
                        pos.setAbs(i);
                        pos.setOrd(room.getHeight()-1);
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < room.getHeight(); i++){
                    if (roomContent[i][0] == 3){
                        pos.setAbs(0);
                        pos.setOrd(i);
                        break;
                    }
                }
                break;
        }
        return pos;
    }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public Room getRoom(int roomNum) {
        return roomList.get(roomNum);
    }

    public GridMap getGridMap(Room room) {
        return gridMapList.get(room.getRoomNum());
    }

    /**
     * Get the dungeon size.
     * @return int
     */
    public int getDungeonSize(){
        return roomList.size();
    }

    public List<Room> getRoomList() {
        return roomList;
    }
    public GraphDungeon getGraph() {
        return graph;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        if ((this.roomList.size() == dungeon.roomList.size()) &&
                (roomList.equals(dungeon.roomList))) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomList);
    }
}
