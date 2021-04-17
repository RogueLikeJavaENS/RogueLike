package gameElement;

import display.GridMap;
import entity.Entity;
import entity.object.Coins;
import entity.object.Door;
import entity.object.potion.PotionFactory;
import generation.GraphDungeon;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is graph representation of the dungeon.
 * 1 room can be connected by min 1 room and max 4 rooms.
 *
 * @author Antoine
 */

public class Dungeon {
    private final int maxRoomHeight;
    private final int maxRoomWidth;
    private final List<Room> roomList;
    private final int width; // width -> max numbers of rooms in a column
    private final int height;  // height -> max numbers of rooms in a row
    private final GraphDungeon graph;
    private List<GridMap> gridMapList; // list of gridMap, index by room number.

    /**
     * List of List of roomNumber.
     * Index 0 -> North,
     *       1 -> East,
     *       2 -> South,
     *       3 -> West.
     */

    public Dungeon(List<Room> roomList, int width, int height, GraphDungeon graph, int maxRoomHeight, int maxRoomWidth) {
        this.roomList = roomList;
        this.graph = graph;
        this.width = width;
        this.height = height;
        this.maxRoomHeight = maxRoomHeight;
        this.maxRoomWidth = maxRoomWidth;
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

    private void placeTestPotions(){
        PotionFactory testFactory = new PotionFactory();
        for (Room room : roomList){
            room.addEntity(testFactory.getPotion(0, new Position(4, 3)));
            room.addEntity(testFactory.getPotion(1, new Position(4, 5)));
            room.addEntity(testFactory.getPotion(2, new Position(5, 4)));
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
    public Room getRoom(int roomNum) { return roomList.get(roomNum); }
    public GridMap getGridMap(Room room) { return gridMapList.get(room.getRoomNum()); }
    public int getMaxRoomHeight() { return maxRoomHeight; }
    public int getMaxRoomWidth() { return maxRoomWidth; }
    public int getDungeonSize(){ return roomList.size(); }
    public List<Room> getRoomList() { return roomList; }
    public GraphDungeon getGraph() { return graph; }

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
