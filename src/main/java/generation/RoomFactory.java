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
        switch (roomType) {
            case START:
                currentAvailablePositions.remove(room.getCenter());
                addHoleAndSpike(room);
                addChest(room,true);
                break;
            case BOSS:
                addBoss(room);
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
        return room;
    }



    public static void addMerchant(Dungeon dungeon, InGameClasses playerClasse) {
        for (Room room : dungeon.getRoomList()) {
            if (room.getRoomType() == RoomType.REST) {
                List<Position> availablePositions = room.getAvailablePositions();
                GeneralMerchant generalMerchant = new GeneralMerchant(availablePositions.remove(0));
                EquipmentFactory equipmentFactory = new EquipmentFactory(playerClasse);
                GameRule gm = new GameRule();

                Inventory merchantInventory = generalMerchant.getMerchantInventory();

                for (int i = 0; i < gm.getNumberOfEquipMerchantShop(); i++) {
                    int level = 1;
                    EquipmentRarity equipmentRarity = gm.getRarityEquipmentInMerchantShop();
                    EquipmentType equipmentType = gm.getEquipmentTypeInMerchantShop();
                    merchantInventory.addItem(equipmentFactory.getEquipment(level, equipmentType, equipmentRarity));
                }

                for (int i = 0; i < 5; i++) {
                    merchantInventory.addItem(new PotionHealth());
                    merchantInventory.addItem(new Elixir());
                    if (i < 2) {
                        merchantInventory.addItem(new XpBottle());
                    }
                }
                merchantInventory.addItem(new GoldKey());
                merchantInventory.addItem(new MapDungeon());
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.HELMET, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.BOOT, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.PANT, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.GLOVE, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.SHIELD, EquipmentRarity.E));
                merchantInventory.addItem(equipmentFactory.getEquipment(1, EquipmentType.WEAPON, EquipmentRarity.E));


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
        for (int i = 0; i < monsterCount; i++) {
            type = gameRule.getMonsterType();
            room.addEntity(monsterFactory.getMonster(type, currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
        for (int i = 0; i < monsterCount; i++) {
            room.addEntity(new Coins(currentAvailablePositions.get(0)));
            currentAvailablePositions.remove(0);
        }
    }

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
                room.addEntity((Entity) potionFactory.getPotionEntity(gameRule.getItemType(), currentAvailablePositions.get(0)));
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
                room.addEntity(new Chest(currentAvailablePositions.get(0),true, gameRule.isChestMimic()));
                currentAvailablePositions.remove(0);
            }
            else{
                room.addEntity(new Chest(currentAvailablePositions.get(0),false, false));
                currentAvailablePositions.remove(0);
            }

        }
    }

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
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
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
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
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
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
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
     *
     * @param hasDoor boolean, true if there are a door, false either
     * @param contents array of tiles' id
     *
     * The method fills the content's array. If hasDoor is true, then the methods creates the correct walls, door..
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