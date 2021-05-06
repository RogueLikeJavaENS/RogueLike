package gameElement;

import display.GridMap;
import entity.Entity;
import entity.object.Button;
import entity.object.Door;
import generation.GraphDungeon;
import generation.RoomFactory;
import generation.RoomType;
import generation.VerificationRoom;
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
    private final int maxRoomHeight;
    private final int maxRoomWidth;
    private final List<Room> roomList;
    private final int width; // width -> max numbers of rooms in a column
    private final int height;  // height -> max numbers of rooms in a row
    private final int floor;
    private final GraphDungeon graph;
    private List<GridMap> gridMapList; // list of gridMap, index by room number.
    private List<Button> buttons;

    public Dungeon(List<Room> roomList, int width, int height, GraphDungeon graph, int maxRoomHeight, int maxRoomWidth, int floor) {
        this.roomList = roomList;
        this.graph = graph;
        this.width = width;
        this.height = height;
        this.maxRoomHeight = maxRoomHeight;
        this.maxRoomWidth = maxRoomWidth;
        this.floor = floor;
        buttons = new ArrayList<>();
        createAllDoor();
        setAllNextDoor();
        closeDoorOfEndRoom();
        initGridMapList();
        placeAllButtons();
        setBossDoor();
        verifyALlRoom();
    }

    /**
     * Verify if all the room are good
     *
     */
    private void verifyALlRoom(){
        for (Room room : roomList){
            VerificationRoom.verificationGenerationRoom(room,this);
        }
    }

    public boolean isAllButtonsPressed(GameState gameState) {
        int nbButtonsNotClosed = buttons.size();
        for (Button button : buttons) {
            if (button.isPressed()) {
                nbButtonsNotClosed--;
            }
        }
        if (nbButtonsNotClosed == 0) {
            gameState.getDescriptor().updateDescriptor(String.format("%s pressed all the buttons... the door is opening.", gameState.getPlayer().getName()));
            return true;
        } else {
            gameState.getDescriptor().updateDescriptor(String.format("%d button(s) are not pressed yet.", nbButtonsNotClosed));
            return false;
        }
    }

    private void setBossDoor() {
        for (Room room : roomList) {
            if (room.getRoomType() == RoomType.BOSS) {
                Door door = room.getDoors().get(0);
                door.setBossDoor();
            }
        }
    }

    private void placeAllButtons() {
        Random GEN = new Random();
        ArrayList<Integer> idRoomList = new ArrayList<>();
        for (int i = 0; i < roomList.size(); i++) {
            idRoomList.add(i);
        }
        Collections.shuffle(idRoomList);
        int nbButtonPlaced = 0;
        while(nbButtonPlaced < 4) {
            Room room = roomList.get(idRoomList.remove(0));
            if (room.getRoomType() == RoomType.MONSTER) {

                List<Position> positionList = room.getWallAvailablePosition();
                Collections.shuffle(positionList);
                Position position = positionList.remove(0);
                Button button = new Button(position);
                room.addEntity(button);
                getGridMap(room).update(button, true);
                buttons.add(button);
                nbButtonPlaced++;
            }
        }
    }

    /**
     * Close the door of the final room (where the stair is)
     *
     */
    private void closeDoorOfEndRoom(){
        List<Door> doorList = roomList.get(roomList.size()-2).getDoors();
        for (Door door : doorList){
            door.closeRelyDoor();
        }    }

    /**
     * Initialize all the gridMap of the dungeon
     *
     */
    private void initGridMapList(){
        this.gridMapList = new ArrayList<>();
        for (Room room : roomList) {
            gridMapList.add(new GridMap(room));
        }
    }

    /**
     * Create all the doors of the dungeon
     *
     */
    private void createAllDoor(){
        for (Room room : roomList) {
            int[] nearRoom = room.getNearRoom();
            for (int j = 0; j < nearRoom.length; j++) {
                if (nearRoom[j] != -1) {
                    Door door = new Door(getDoorPosition(j, room), getRoom(nearRoom[j]), Direction.intToDirection(j),true);
                    room.addEntity(door);
                }
            }
        }
    }

    /**
     * Rely by peer all the door of the dungeon
     *
     */
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

    /**
     * Return the door of the room "room" at the position "position"
     *
     * @param position position of the door
     * @param room room where the door is
     * @return the door or null if there is no door
     */
    private Door getDoorAt(Position position, Room room){
        List<Entity> entityList = room.getEntities();
        for (Entity entity: entityList) {
            if (entity.getPosition().equals(position)){
                return (Door) entity;
            }
        }
        return null;
    }

    /**
     * Return the position of the door with it's direction an it's room
     *
     * @param dir direction of the door (N-S-E-W)
     * @param room room where whe search the door
     * @return the position of the door
     */
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

    public int getFloor() {
        return floor;
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
        return (this.roomList.size() == dungeon.roomList.size()) &&
                (roomList.equals(dungeon.roomList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomList);
    }
}
