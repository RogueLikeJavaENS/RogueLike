package generation;

import display.GridMap;
import display.tiles.Tile;
import entity.Entity;
import entity.living.npc.merchants.GeneralMerchant;
import entity.living.npc.monster.MonsterFactory;
import entity.living.npc.monster.boss.Boss;
import entity.living.npc.monster.boss.BossFactory;
import entity.living.npc.monster.boss.BossPart;
import entity.living.npc.monster.boss.Bosses;
import entity.object.*;
import entity.object.potions.PotionEntityFactory;
import gameElement.Dungeon;
import gameElement.GameRule;
import gameElement.GameState;
import gameElement.Room;
import utils.Direction;
import stuff.Stuff;
import stuff.equipment.EquipmentFactory;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;
import stuff.item.ItemFactory;
import stuff.item.keys.GoldKey;
import stuff.item.potions.Elixir;
import stuff.item.potions.PotionHealth;
import stuff.item.potions.XpBottle;
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

    /**
     * Create a room factory
     * @param width the width of a room
     * @param height the height of a room
     * @param empty empty ??
     * @param floor floor ??
     */
    public RoomFactory(int width, int height, int empty, int floor) {
        this.width = width;
        this.height = height;
        this.space = empty;
        this.floor = floor;
    }


    public Room getRoom(Seed seed, RoomType roomType, int current, int[] nextList) {
        Room room = createRoom(current, nextList, roomType);
        this.currentAvailablePositions = room.getAvailablePositions();
        GridMap gridMap = new GridMap(room);
        switch (roomType) {
            case START:
               currentAvailablePositions.remove(room.getCenter());
                addHoleAndSpike(room);
                addChest(room,true);
                break;
            case BOSS:
                BossFactory bossFactory = new BossFactory(floor);
                Boss rabbitBoss = bossFactory.getBoss(Bosses.KILLER_RABBIT, room.getCenter());
                List<BossPart> bossParts = rabbitBoss.getBossPartList();
                room.addEntity(bossParts.get(0));
                room.addEntity(bossParts.get(1));
                room.addEntity(bossParts.get(2));
                room.addEntity(bossParts.get(3));
                room.addEntity(rabbitBoss);
                break;
            case END:
                addStairs(room);    // Go to the next floor
                break;
            case NORMAL:
                // nothing special for now.
                addHoleAndSpike(room);
                break;
            case MONSTER:
                addHoleAndSpike(room);
                addMonsters(room);
                if (gameRule.presenceOfClassicChestOnMonsterRoom()){
                    addChest(room,true);
                }
                break;
            case REST:
                addHoleAndSpike(room);
                addCoins(room, 5);
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

    /**
     * Add a stair at the middle of the room
     *
     * @param room the room where we add the stair
     */
    private void addStairs(Room room) {
        room.addEntity(new Stair(room.getCenter()));
        currentAvailablePositions.remove(room.getCenter());
    }

    /**
     * Add multiple monster (according to the GameRule) in the room
     *
     * @param room the room
     */
    private void addMonsters(Room room) {
        MonsterFactory monsterFactory = new MonsterFactory(floor);
        int monsterCount = GEN.nextInt(10) % 2 + 2;
        int type;
        for (int i = 0; i < monsterCount; i++) {
            type = GEN.nextInt(2);
            room.addEntity(monsterFactory.getMonster(type, currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
        for (int i = 0; i < monsterCount; i++) {
            room.addEntity(new Coins(currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
    }

    /**
     * Add multiple potions (type of the potion according to the GameRule) in the room
     *
     * @param room the room
     * @param numberOfPotion the number of potion to add
     */
    private void addPotions(Room room, int numberOfPotion) {
        PotionEntityFactory potionFactory = new PotionEntityFactory();
        for (int i = 0; i < numberOfPotion; i++) {
            if (currentAvailablePositions.size() != 0) {
                room.addEntity((Entity) potionFactory.getPotionEntity(gameRule.getPotionType(), currentAvailablePositions.get(0)));
                currentAvailablePositions.remove(0);
            }
        }
    }

    /**
     * Add Coins on the Room
     *
     * @param room the room
     * @param numberOfGold the number of Coins to add
     */
    private void addCoins(Room room, int numberOfGold) {
        for (int i = 0; i < numberOfGold; i++) {
            if (currentAvailablePositions.size() != 0) {
                room.addEntity(new Coins(currentAvailablePositions.remove(0)));
            }
        }
    }

    /**
     * Add a chest (classic or golden) in the room
     *
     * @param room the room
     * @param isClassic true if the chest is classic, false if the chest is golden
     */
    private void addChest(Room room, boolean isClassic){
        if (currentAvailablePositions.size() != 0){
            if (isClassic){
                room.addEntity(new Chest(currentAvailablePositions.get(0),true));
                currentAvailablePositions.remove(0);
            }
            else{
                room.addEntity(new Chest(currentAvailablePositions.get(0),false));
                currentAvailablePositions.remove(0);
            }

        }
    }

    public static void addMerchant(GameState gameState, Dungeon dungeon) {
        for (Room room : dungeon.getRoomList()) {
            if (room.getRoomType() == RoomType.REST) {
                List<Position> availablePositions = room.getAvailablePositions();
                GeneralMerchant generalMerchant = new GeneralMerchant(availablePositions.remove(0));
                EquipmentFactory equipmentFactory = new EquipmentFactory(gameState);
                GameRule gm = new GameRule();
                List<Stuff> merchantInventory = new ArrayList<>();

                for (int i = 0; i < gm.getNumberOfEquipMerchantShop(); i++) {
                    int level = 1;
                    EquipmentRarity equipmentRarity = gm.getRarityEquipmentInMerchantShop();
                    EquipmentType equipmentType = gm.getEquipmentTypeInMerchantShop();
                    merchantInventory.add(equipmentFactory.getEquipment(level, equipmentType, equipmentRarity));
                }

                for (int i = 0; i < 5; i++) {
                    merchantInventory.add(new PotionHealth());
                    merchantInventory.add(new Elixir());
                    if (i < 2) {
                        merchantInventory.add(new XpBottle());
                    }
                }
                merchantInventory.add(new GoldKey());

                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.HELMET, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.BOOT, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.PANT, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.GLOVE, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.SHIELD, EquipmentRarity.E));
                merchantInventory.add(equipmentFactory.getEquipment(1, EquipmentType.WEAPON, EquipmentRarity.E));

                generalMerchant.getMerchantInventory().setMerchantInventory(merchantInventory);
                Position position =  availablePositions.remove(0);
                room.addEntity(new GeneralMerchant(position));
                dungeon.getGridMap(room).update(generalMerchant, true);
            }
        }
    }
//    private void addMerchant(Room room) {
//        room.addEntity(new PotionMerchant(currentAvailablePositions.remove(0)));
//    }

    /**
     * Add holes and spikes in the room (number of holes and spikes according to the GameRule)
     * @param room the room
     */
    private void addHoleAndSpike(Room room){
        int nbHole = gameRule.numberOfHole();
        int nbSpike = gameRule.numberOfSpike();

        for(int i = 0; i< nbSpike; i++){
            room.addEntity(new Spike(currentAvailablePositions.remove(0)));
        }
        for(int i = 0; i <nbHole; i++){
            room.addEntity(new Hole(currentAvailablePositions.remove(0)));
        }

    }

    /**
     * Create a path of hole
     * @param room the room
     */
    private void createPathOfHole(Room room){
        int sizeHole = gameRule.sizeOfPathHole();
        Collections.shuffle(currentAvailablePositions);
        Position currentPosition = currentAvailablePositions.get(0);
        currentAvailablePositions.remove(0);
        room.addEntity(new Hole(currentPosition));
        sizeHole --;
        for (int i = 0; i<sizeHole; i++){
            List<Position> accessiblePos = getAccessibleDirectionFromPosition(currentPosition,room);
            if (accessiblePos.size() == 0) {
                break;
            }
            else {
                Collections.shuffle(accessiblePos);
                currentPosition = accessiblePos.get(0);
                currentAvailablePositions.remove(currentPosition);
                room.addEntity(new Hole(currentPosition));
            }
        }
    }

    /**
     * Create a path of spike
     * @param room the room
     */
    private void createPathOfSpike(Room room){
        int sizeSpike = gameRule.sizeOfPathSpike();
        Collections.shuffle(currentAvailablePositions);
        Position currentPosition = currentAvailablePositions.remove(0);
        room.addEntity(new Spike(currentPosition));
        sizeSpike --;
        for (int i = 0; i<sizeSpike; i++){
            List<Position> accessiblePos = getAccessibleDirectionFromPosition(currentPosition,room);
            if (accessiblePos.size() == 0) {
                break;
            }
            else {
                Collections.shuffle(accessiblePos);
                currentPosition = accessiblePos.get(0);
                currentAvailablePositions.remove(currentPosition);
                room.addEntity(new Spike(currentPosition));
            }
        }
    }

    /**
     * Return a list of position which are accessible from the position "positio"
     * @param position the position
     * @param room the room
     * @return the list of accessible position around the position
     */
    private List<Position> getAccessibleDirectionFromPosition(Position position, Room room){
        List<Position> listAccessiblePosition = new ArrayList<>();
        for (int i = 0; i<4;i++){
            if(room.getAvailablePositions().contains(position.getPosInFront(Direction.intToDirection(i)))){
                listAccessiblePosition.add(position.getPosInFront(Direction.intToDirection(i)));
            }
        }
        return listAccessiblePosition;
    }

    /**
     * Create a room
     *
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