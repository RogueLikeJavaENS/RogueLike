package generation;

import game.entity.living.player.classeSystem.InGameClasses;
import game.stuff.item.MapDungeon;
import game.tile.TileEnum;
import game.elements.Dungeon;
import game.elements.GameRule;
import game.elements.Room;
import game.entity.Entity;
import game.entity.living.inventory.Inventory;
import game.entity.living.npc.merchants.GeneralMerchant;
import game.entity.living.npc.monster.MonsterFactory;
import game.entity.living.npc.monster.boss.*;
import game.entity.object.elements.Chest;
import game.entity.object.elements.Coins;
import game.entity.object.elements.Stair;
import game.entity.object.potion.PotionEntityFactory;
import game.entity.object.traps.Hole;
import game.entity.object.traps.Spike;
import utils.Direction;
import game.stuff.equipment.*;
import game.stuff.item.keys.GoldKey;
import game.stuff.item.potions.*;
import utils.Position;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class RoomFactory {
    private final int width; // 15
    private final int height; // 11
    private final int space; // 2
    private final int floor;
    private final static Random GEN = new Random();
    private List<Position> currentAvailablePositions;       // list of the position where we can put entities

    /**
     * Create a room factory
     * @param width the width of a room
     * @param height the height of a room
     * @param empty the space to remove on the border is there is no door
     * @param floor the number of the floor
     */
    public RoomFactory(int width, int height, int empty, int floor) {
        this.width = width;
        this.height = height;
        this.space = empty;
        this.floor = floor;
    }

    /**
     * Create a room with the information given on parameters
     *
     * @param seed the seed used to generate the dungeon
     * @param roomType the type of the room we want
     * @param current the current number of the room
     * @param nextList list of the number of the neighbour room
     * @return the room created
     */
    public Room getRoom(Seed seed, RoomType roomType, int current, int[] nextList) {
        Room room = createRoom(current, nextList, roomType); // create the room
        this.currentAvailablePositions = room.getAvailablePositions();
        switch (roomType) { // put the needed entity in each type of room
            case START:
                currentAvailablePositions.remove(room.getCenter()); // remove the center for the available position because it's where the player spawn
                addHoleAndSpike(room);
                addChest(room,true, true);
                break;
            case BOSS:
                addBoss(room);      // put a boss
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
                if (GameRule.presenceOfClassicChestOnMonsterRoom()){    // accordint to the GameRule put a chest or not
                    addChest(room,true, false);
                }
                break;
            case REST:
                addHoleAndSpike(room);
                addCoins(room, 5);
                break;
            case TREASURE:
                addChest(room,false, false);
                addCoins(room, GameRule.getNumberOfGoldInTreasureRoom());
                addPotions(room, GameRule.getNumberOfPotionInTreasureRoom());
                break;
        }
        return room;
    }

    /**
     * Adding a merchant in the room
     *
     * @param dungeon the dungeon of the room
     * @param playerClasse the class of the player
     */
    public static void addMerchant(Dungeon dungeon, InGameClasses playerClasse) {
        for (Room room : dungeon.getRoomList()) {
            if (room.getRoomType() == RoomType.REST) { // if the room is of type REST
                List<Position> availablePositions = room.getAvailablePositions();
                GeneralMerchant generalMerchant = new GeneralMerchant(availablePositions.remove(0)); // create the merchant
                EquipmentFactory equipmentFactory = new EquipmentFactory(playerClasse);

                Inventory merchantInventory = generalMerchant.getMerchantInventory();

                // Add a number of equipment in the merchant inventory (according to the GameRule)
                for (int i = 0; i < GameRule.getNumberOfEquipMerchantShop(); i++) {
                    int level = 1;
                    EquipmentRarity equipmentRarity = GameRule.getRarityEquipmentInMerchantShop();
                    EquipmentType equipmentType = GameRule.getEquipmentTypeInMerchantShop();
                    merchantInventory.addItem(equipmentFactory.getEquipment(level, equipmentType, equipmentRarity));
                }

                // Add 5 PotionHealth, 5 Elixir and 2 XpBottle in the merchant inventory
                for (int i = 0; i < 5; i++) {
                    merchantInventory.addItem(new PotionHealth());
                    merchantInventory.addItem(new Elixir());
                    if (i < 2) {
                        merchantInventory.addItem(new XpBottle());
                    }
                }
                // Add a Map of the dungeon and a GoldKey in the merchant inventory
                merchantInventory.addItem(new GoldKey());
                merchantInventory.addItem(new MapDungeon());

                // Add some Basic equipment in the merchant inventory
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.HELMET, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.BOOT, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.PANT, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.GLOVE, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.SHIELD, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.WEAPON, EquipmentRarity.E));

                // Put the merchant in the room and update the gridmap
                Position position =  availablePositions.remove(0);
                room.addEntity(new GeneralMerchant(position));
                dungeon.getGridMap(room).update(generalMerchant, true);
            }
        }
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
        for (int i = 0; i < monsterCount; i++) {        // create monsterCount monster with a type chosen by the gameRule and put it in the list of entity of the room
            type = GameRule.getMonsterType();
            room.addEntity(monsterFactory.getMonster(type, currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
        for (int i = 0; i < monsterCount; i++) {          // also adding the same number of coins in the room
            room.addEntity(new Coins(currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
    }

    /**
     * Add a boss in the room
     *
     * @param room the room
     */
    private void addBoss(Room room) {
        BossFactory bossFactory = new BossFactory(floor);
        Boss boss = bossFactory.getBoss(Bosses.KILLER_RABBIT, room.getCenter());
        List<BossPart> bossParts = boss.getBossPartList();
        room.addEntity(bossParts.get(0));
        room.addEntity(bossParts.get(1));
        room.addEntity(bossParts.get(2));
        room.addEntity(bossParts.get(3));
        room.addEntity(boss);
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
                room.addEntity((Entity) potionFactory.getPotionEntity(GameRule.getPotionType(), currentAvailablePositions.get(0)));
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
    private void addChest(Room room, boolean isClassic, boolean isStart){
        if (currentAvailablePositions.size() != 0){
            if (isStart) {
                room.addEntity(new Chest(currentAvailablePositions.remove(0),true, GameRule.isChestMimic(), true));
            } else {
                if (isClassic){
                    room.addEntity(new Chest(currentAvailablePositions.remove(0),true, GameRule.isChestMimic(), false));
                }
                else{
                    room.addEntity(new Chest(currentAvailablePositions.remove(0),false, false, false));

                }
            }
        }
    }


    /**
     * Add holes and spikes in the room (number of holes and spikes according to the GameRule)
     * @param room the room
     */
    private void addHoleAndSpike(Room room){
        int nbHole = GameRule.numberOfHoleAtGeneration();
        int nbSpike = GameRule.numberOfSpikeAtGeneration();

        for(int i = 0; i< nbSpike; i++){
            room.addEntity(new Spike(currentAvailablePositions.remove(0)));
        }
        // Add the holes after
        for(int i = 0; i <nbHole; i++){
            room.addEntity(new Hole(currentAvailablePositions.remove(0)));
        }

    }

    /**
     * Return a list of position which are accessible from the position "position"
     *
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

        TileEnum emptyTileEnum = TileEnum.EMPTY;
        TileEnum floor = TileEnum.FLOOR;

        fillSquare(0, 0, space, space, emptyTileEnum.getId(), contents); // fill void NORTH-WEST
        fillSquare(width - space, 0, width -1, space, emptyTileEnum.getId(), contents); // NORTH-EAST
        fillSquare(0, height - space, space, height -1 , emptyTileEnum.getId(),contents); // SOUTH-WEST
        fillSquare(width - space, height - space, width -1 , height -1, emptyTileEnum.getId(), contents);
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
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     */
    private void fillNorth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(space, 0, width -(space +1), space, TileEnum.WALL.getId(), contents); //Northern wall
            fillSquare(space +1, 1, width -(space +2), space, TileEnum.FLOOR.getId(), contents); // Floor
            contents[0][width /2] = TileEnum.DOOR.getId(); // Door
        } else {
            fillSquare(space, 0, width -(space +1), space -1, TileEnum.EMPTY.getId(), contents);
            fillSquare(space +1, space, width -(space +2), space, TileEnum.WALL.getId(), contents); // Northern wall
        }
    }

    /**
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     */
    private void fillSouth(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(space, height -(space +1), width -(space +1), height -1,
                    TileEnum.WALL.getId(), contents);
            fillSquare(space +1, height -(space +1), width -(space +2), height -(space),
                    TileEnum.FLOOR.getId(), contents);
            contents[height -1][width /2] = TileEnum.DOOR.getId();
        } else {
            fillSquare(space, height -(space), width -(space +1), height -1,
                    TileEnum.EMPTY.getId(), contents);
            fillSquare(space +1, height -(space +1), width -(space +2), height -(space +1),
                    TileEnum.WALL.getId(), contents);
        }
    }

    /**
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     */
    private void fillEast(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(width -(space +1), space, width -1, height -(space +1),
                    TileEnum.WALL.getId(), contents);
            fillSquare(width -(space +1), space +1 , width -2, height -(space +2),
                    TileEnum.FLOOR.getId(), contents);
            contents[height /2][width -1] = TileEnum.DOOR.getId();
        } else {
            fillSquare(width -(space), space, width -1, height -(space +1),
                    TileEnum.EMPTY.getId(), contents);
            fillSquare(width -(space +1), space, width -(space +1), height -(space +1),
                    TileEnum.WALL.getId(), contents);
        }
    }

    /**
     *The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     */
    private void fillWest(boolean hasDoor, int[][] contents) {
        if (hasDoor) {
            fillSquare(0, space, space, height -(space +1),
                    TileEnum.WALL.getId(), contents);
            fillSquare(1, space +1, space, height -(space +2),
                    TileEnum.FLOOR.getId(), contents);
            contents[height /2][0] = TileEnum.DOOR.getId();
        } else {
            fillSquare(0, space, space -1, height -(space +1),
                    TileEnum.EMPTY.getId(), contents);
            fillSquare(space, space, space, height -(space +1),
                    TileEnum.WALL.getId(), contents);
        }
    }

    /**
     *The method fills the contents' array with the given tile id in the square's position
     *
     * @param abs1 int abs position from the upper-left position
     * @param ord1 int ord position from the upper-left position
     * @param abs2 int abs position from the up-right position
     * @param ord2 int ord position from the up-right position
     * @param tile int id of the tile
     * @param contents array of tiles' id
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