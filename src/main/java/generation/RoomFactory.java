package generation;

import display.tiles.Tile;
import entity.Entity;
import entity.living.npc.merchants.PotionMerchant;
import entity.living.npc.monster.MonsterFactory;
import entity.object.*;
import entity.object.potions.PotionEntityFactory;
import gameElement.GameRule;
import gameElement.Room;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoomFactory {
    private final int width; // 15
    private final int height; // 11
    private final int space; // 2
    private final int floor;
    private final static GameRule gameRule = new GameRule();
    private final static Random GEN = new Random();
    private List<Position> currentAvailablePositions;

    public RoomFactory(int width, int height, int empty, int floor) {
        this.width = width;
        this.height = height;
        this.space = empty;
        this.floor = floor;
    }

    public Room getRoom(Seed seed, RoomType roomType, int current, int[] nextList) {
        Room room = createRoom(current, nextList, roomType);
        this.currentAvailablePositions = room.getAvailablePositions();
        switch (roomType) {
            case START:
                addChest(room,true);    // Basic equipment to start a new adventure
                // add some stuffs that make clear it's the start room.
                break;
            case BOSS:
                break;
            case END:
                addStairs(room);    // Go to the next floor
                break;
            case NORMAL:
                // nothing special for now.
                break;
            case MONSTER:
                addMonsters(room);
                if (gameRule.presenceOfClassicChestOnMonsterRoom()){
                    addChest(room,true);
                }
                break;
            case REST:
                addCoins(room, 5);
                addMerchant(room);
                break;
            case TREASURE:
                addChest(room,false);
                addCoins(room, gameRule.getNumberOfGoldInTreasureRoom());
                addPotions(room, gameRule.getNumberOfPotionInTreasureRoom());

                break;

        }
        // add some traps, walls, holes...
        return room;
    }

    private void addStairs(Room room) {
        room.addEntity(new Stair(room.getCenter()));
    }

    private void addMonsters(Room room) {
        MonsterFactory monsterFactory = new MonsterFactory(floor);
        int monsterCount = GEN.nextInt(10) % 2 + 2;
        int type;
        for (int i = 0; i < monsterCount; i++) {
            type = GEN.nextInt(2);
            room.addEntity(monsterFactory.getMonster(type, currentAvailablePositions.remove(0)));
        }
        for (int i = 0; i < monsterCount; i++) {
            room.addEntity(new Coins(currentAvailablePositions.remove(0)));
        }
    }

    private void addPotions(Room room, int numberOfPotion) {
        PotionEntityFactory potionFactory = new PotionEntityFactory();
        for (int i = 0; i < numberOfPotion; i++) {
            if (currentAvailablePositions.size() != 0) {
                room.addEntity((Entity) potionFactory.getPotionEntity(gameRule.getPotionType(), currentAvailablePositions.remove(0)));
            }
        }
    }

    private void addCoins(Room room, int numberOfGold) {
        for (int i = 0; i < numberOfGold; i++) {
            if (currentAvailablePositions.size() != 0) {
                room.addEntity(new Coins(currentAvailablePositions.remove(0)));
            }
        }
    }

    private void addChest(Room room, boolean isClassic){
        if (currentAvailablePositions.size() != 0){
            if (isClassic){
                room.addEntity(new Chest(currentAvailablePositions.get(0),true));
            }
            else{
                room.addEntity(new Chest(currentAvailablePositions.get(0),false));
            }

        }
    }

    private void addMerchant(Room room) {
        room.addEntity(new PotionMerchant(currentAvailablePositions.remove(0)));
    }


    /**
     * @param current The current number of the room
     * @param nextList Array with the position and the number of the next Room
     * @return Room with the correct wall and doors
     */

    private Room createRoom(int current, int[] nextList, RoomType roomType) {

        Position roomPosition = new Position(nextList[5], nextList[4]); // using y x might need to reverse
        int[][] contents;
        contents = new int[height][width];

        Tile emptyTile = Tile.EMPTY;
        Tile floor = Tile.FLOOR;

        fillSquare(0, 0, space, space, emptyTile.getId(), contents); // fill void NORTH-WEST
        fillSquare(width - space, 0, width -1, space, emptyTile.getId(), contents); // NORTH-EAST
        fillSquare(0, height - space, space, height -1 ,emptyTile.getId(),contents); // SOUTH-WEST
        fillSquare(width - space, height - space, width -1 , height -1, emptyTile.getId(), contents);
        fillSquare(space +1, space +1, width -(space +2), height -(space +2),floor.getId(), contents);

        fillNorth(nextList[0] != -1, contents);
        fillEast(nextList[1] != -1, contents);
        fillSouth(nextList[2] != -1, contents);
        fillWest(nextList[3] != -1, contents);

        int[] nextRoom = new int[4];
        System.arraycopy(nextList, 0, nextRoom, 0, 4);
        return new Room(current, nextRoom, contents, roomPosition, width, height, roomType);
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private void fillNorth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(space, 0, width -(space +1), space, Tile.WALL.getId(), contents); //Northern wall
            fillSquare(space +1, 1, width -(space +2), space, Tile.FLOOR.getId(), contents); // Floor
            contents[0][width /2] = Tile.DOOR.getId(); // Door
        } else {
            fillSquare(space, 0, width -(space +1), space -1, Tile.EMPTY.getId(), contents);
            fillSquare(space +1, space, width -(space +2), space, Tile.WALL.getId(), contents); // Northern wall
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private void fillSouth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(space, height -(space +1), width -(space +1), height -1,
                    Tile.WALL.getId(), contents);
            fillSquare(space +1, height -(space +1), width -(space +2), height -(space),
                    Tile.FLOOR.getId(), contents);
            contents[height -1][width /2] = Tile.DOOR.getId();
        } else {
            fillSquare(space, height -(space), width -(space +1), height -1,
                    Tile.EMPTY.getId(), contents);
            fillSquare(space +1, height -(space +1), width -(space +2), height -(space +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private void fillEast(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(width -(space +1), space, width -1, height -(space +1),
                    Tile.WALL.getId(), contents);
            fillSquare(width -(space +1), space +1 , width -2, height -(space +2),
                    Tile.FLOOR.getId(), contents);
            contents[height /2][width -1] = Tile.DOOR.getId();
        } else {
            fillSquare(width -(space), space, width -1, height -(space +1),
                    Tile.EMPTY.getId(), contents);
            fillSquare(width -(space +1), space, width -(space +1), height -(space +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     */

    private void fillWest(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(0, space, space, height -(space +1),
                    Tile.WALL.getId(), contents);
            fillSquare(1, space +1, space, height -(space +2),
                    Tile.FLOOR.getId(), contents);
            contents[height /2][0] = Tile.DOOR.getId();
        } else {
            fillSquare(0, space, space -1, height -(space +1),
                    Tile.EMPTY.getId(), contents);
            fillSquare(space, space, space, height -(space +1),
                    Tile.WALL.getId(), contents);
        }
    }

    /**
     *
     * @param abs1 int abs position from the upper-left position
     * @param ord1 int ord position from the upper-left position
     * @param abs2 int abs position from the up-right position
     * @param ord2 int ord position from the up-right position
     * @param tile int id of the tile
     * @param contents array of tiles' id
     *
     * The method fills the contents' array with the given tile id in the square's position
     */

    private void fillSquare(int abs1, int ord1, int abs2, int ord2, int tile, int[][] contents) {
        if (abs1 > abs2 || ord1 > ord2) {
            int absTmp = abs1;
            int ordTmp = ord1;
            abs1 = abs2; ord1 = ord2;
            abs2 = absTmp; ord2 = ordTmp;
        }
        for (int ord = ord1; ord <= ord2; ord++) {
            for (int abs = abs1; abs <= abs2; abs++) {
                contents[ord][abs] = tile;
            }
        }
    }
}