package game.elements;

import display.*;
import game.entity.living.npc.monster.monsterStrategy.StrategyUtils;
import game.tile.Tile;
import game.entity.Entity;
import game.entity.living.LivingEntity;
import game.entity.living.npc.merchants.Merchant;
import game.entity.living.npc.monster.boss.Boss;
import game.entity.living.npc.monster.boss.BossPart;
import game.entity.living.player.Player;
import game.entity.living.npc.monster.Monster;
import game.entity.object.elements.Grave;
import game.entity.living.player.spell.*;
import utils.*;

import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class represents the State of the Game at any moment. Each action will pass by this class.
 * It determines rather or not if the action is permitted.
 */

public class GameState {
    private State state;
    private Room currentRoom;
    private Player player;
    private Dungeon dungeon;
    private GridMap gridMap;
    private Fighting fighting;
    private boolean help;
    private MiniMap miniMap;
    private final Descriptor descriptor;
    private HUD hud;
    private int currentFightExp;
    public Merchant merchant;
    private final ScanPanel sp;
    private final MusicStuff musicStuff;

    public GameState(Player player, Dungeon dungeon, HUD hud, ScanPanel sp, MusicStuff musicStuff) {
        this.musicStuff = musicStuff;
        this.dungeon = dungeon;
        this.player = player;
        this.sp = sp;
        this.currentRoom = dungeon.getRoomList().get(0); //dungeon.getRoomList().size()-2
        this.gridMap = dungeon.getGridMap(currentRoom);
        this.help = false;
        this.miniMap = new MiniMap(dungeon, this);
        this.descriptor = new Descriptor();
        this.hud = hud;
        player.setPosition(currentRoom.getCenter());
        state = State.NORMAL;
        gridMap.update(player, true);
        currentFightExp = 0;
        isThereMonsters();
    }

    /**
     * Get the selected spell, update the range according to the spell's range.
     * If the spell is not zoning, the rangeList in the GridMap is updated.
     */
    public void updateRange() {
        Spell spell = player.getPlayerStats().getSelectedSpell();
        spell.setRange(player.getPosition(), player.getDirection());
        Range range = spell.getRange();
        gridMap.updateRangeList(range);
         if (spell.isMovement()) {
             sortRangeList(gridMap.getRangeList());
         }
        if (!spell.isZoning()) {
            sortRangeList(gridMap.getRangeList());
            noZoningRange(gridMap.getRangeList());
        }
    }

    /**
     * Use to change the current room.
     * @param room The new room
     */
    public void updateChangingRoom(Room room) {
        if (!room.getWasVisited()){
            room.setWasVisited(true);
            room.setNearRoomBossEndVisited();
            miniMap.updateMap();
        }
        setState(State.NORMAL);
        gridMap.update(player, false);      // remove the player from the previous room
        setCurrentRoom(room);                   // set the current room with the new room
        setGridMap(dungeon.getGridMap(room));   // take the gridMap that represent the new room
        gridMap.update(player, true);       // add the player to the new room
        changeRoomFight();                       // check if the Room contains monster
        isOnEntity();
    }

    /**
     * Checks if the player can access the tile at his position + x and y, and changes its position if so.
     *
     * @param x Abscissa of the tile to check, using the player's position as a base.
     * @param y Ordinate of the tile to check, using the player's position as a base.
     *
     * @author Raphael
     * @return a boolean if the has moved or not.
     */
    public boolean movePlayer(int x, int y) {
        boolean acted = false;                  // boolean used in RogueLike to see if the player consumed his action.
        int abs = player.getPosition().getAbs();
        int ord = player.getPosition().getOrd();
        Tile tile = gridMap.getTileAt(abs + x, ord + y);
        if (tile.isPlayerAccessible()) {  // check if the wanted direction is accessible.
            boolean accessibilityEntity = true;
            List<Entity> entitiesAt = gridMap.getEntitiesAt(abs + x, ord + y);
            for (Entity entity : entitiesAt){   // check if there are no Entity that prevent the player to move on.
                if (!entity.getIsPlayerAccessible()){
                    accessibilityEntity = false;
                    break;
                }
            }
            if (accessibilityEntity){
                player.getPosition().updatePos(x, y);
                acted = true;
            }
        }
        return acted;
    }

    /**
     * Interact with chests... merchants... and others.
     * @return boolean if the player has interacted or not.
     */
    public boolean interact() {
        Position toInteractPos = player.getPosition().getPosInFront(player.getDirection());
        List<Entity> entities = gridMap.getEntitiesAt(toInteractPos.getAbs(), toInteractPos.getOrd());
        if (entities.size() != 0) {
            for (Entity entity: entities) {
                entity.doInteraction(this);
            }
            return true;
        } else {
            descriptor.updateDescriptor("There is nothing to interact with !");
            return false;
        }
    }


    /**
     * Check if the player moved on Entity.
     * The methods throw the action related to the entity.
     */
    public void isOnEntity() {
        int livingEntityAbs = player.getPosition().getAbs();
        int livingEntityOrd = player.getPosition().getOrd();
        List<Entity> entitiesAt = gridMap.getEntitiesAt(livingEntityAbs, livingEntityOrd);
        for(Entity entity : entitiesAt) {
            if (entity != player) {
                entity.doAction(this);
            }
        }
        isThereMonsters();
    }

    /**
     * If the monster is on an entity, check if the entity is a trap. If it is a trap,
     * do the entity action on the monster.
     *
     * @param monster the monster to check
     */
    public void isMonsterOnEntity(LivingEntity monster) {
        int livingEntityAbs = monster.getPosition().getAbs();
        int livingEntityOrd = monster.getPosition().getOrd();
        List<Entity> entitiesAt = gridMap.getEntitiesAt(livingEntityAbs, livingEntityOrd);
        for(Entity entity : entitiesAt) {
            if (entity != monster && entity.isTrap()) {
                entity.doAction(this);
            }
        }
        isThereMonsters();
    }

    /**
     * The methods check if there is any monsters in the current Room.
     * set the state NORMAL with no Monsters or FIGHT with monsters.
     */
    public void isThereMonsters() {
        if (state != State.SHOP && state != State.PAUSE_MENU && state != State.SHOP_MENU) {
            List<LivingEntity> monsters = getGridMap().getMonsters();
            if (monsters.size() > 0) {  // if there is no monsters in the current map
                if (state == State.NORMAL) {    // if the state was at Normal, the fight is initialized.
                    initFight(monsters);
                }
                state = State.FIGHT;
                updateRange();
            }
            else {
                state = State.NORMAL;
                gridMap.clearRangeList();
            }
        }
    }

    /**
     * Use to return in the game if exiting the inventory or the pause menu.
     * Check if there was monster in the room.
     * @return if the player acted or not.
     */
    public boolean isThereMonstersInventory() {
        boolean acted = false;
        List<LivingEntity> monsters = getGridMap().getMonsters();
        if (!monsters.isEmpty()) {
            state = State.FIGHT;
            updateRange();
            acted = true;
        }
        return acted;
    }

    /**
     * Check if the player is alive after a moves or a monster attack.
     */
    public void isPlayerAlive() {
        if (player.getPlayerStats().getLifePointActual() == 0) {
            getMusicStuff().playDieFX();
            setState(State.LOSE);
        }
    }

    /**
     * Use to check if a monster is alive. If not, gives XP and potions to the player.
     * @param monster the monster to check if he's alive.
     */
    public void isMonsterAlive(Monster monster) {
        if (monster.getMonsterStats().getLifePointActual() == 0) {
            musicStuff.playDieFX();
            currentFightExp += monster.getMonsterStats().getXpWorth();
            fighting.removeMonster((LivingEntity) monster);
            gridMap.update(monster, false);
            if (monster instanceof Boss) {
                Boss boss = (Boss) monster;
                for (BossPart currentPart : boss.getBossPartList()) {
                    gridMap.update(currentPart, false);
                }
            }
            player.getPlayerStats().incrementeKillCounter();
            monster.doActionOnDeath(this);
            //isThereMonster est appelée à chaque déplacement donc je dois faire le check à la mort des monstres
            if (gridMap.getMonsters().size() == 0) {
                List<String> descriptionLevelUp = player.getPlayerStats().grantXP(currentFightExp,this);
                descriptor.updateDescriptor(String.format("You took down all the monsters and earned %d exp points!", currentFightExp));
                if (descriptionLevelUp.size() != 0){
                    for (String str : descriptionLevelUp){
                        descriptor.updateDescriptor(String.format("%s"+str,getPlayer().getName()));
                    }
                }
                currentFightExp = 0;
                musicStuff.playNormalMusic();
            }
        }
    }

    /**
     * Initialize or not the Fight when the player change the room.
     */
    public void changeRoomFight() {
        isThereMonsters();
        if (state == State.NORMAL) {
            musicStuff.playNormalMusic();
        }
        if (state == State.FIGHT) {
            upMonsters();
        }
    }

    private void upMonsters() {
        List<LivingEntity> monsters = gridMap.getMonsters();
        Random gen = new Random();
        for (LivingEntity entity : monsters) {
            if (entity.isMonster()) {
                Monster monster = (Monster) entity;
                int playerLevel = player.getPlayerStats().getLevel();
                int monsterLevel = monster.getMonsterStats().getLevel();
                int dif = monsterLevel - playerLevel;
                if (dif < -1 || dif > 1) {
                    int level = playerLevel + gen.nextInt(3)-1;

                    monster.getMonsterStats().setLevel(level);
                    GameRule.setMonstersStats(monster, monster.getMonsterType());
                    getDescriptor().updateDescriptor("It looks like monsters have become "+colorize("stronger", Colors.RED.textApply())+"...");
                }
            }
        }
    }

    /**
     * Uses the player's selected spell and applies its effects to the area within its range.
     * If the player doesn't have enough mana, the spell fails.
     *
     * @return either spell has been successfully used or not.
     *
     * @author Raphael and Antoine
     */
    public boolean useSpell() {
        Spell spell = player.getPlayerStats().getSelectedSpell();
        return spell.useSpell(this);
    }

    /* GETTERS */
    public Dungeon getDungeon() { return dungeon; }
    public GridMap getGridMap() { return gridMap; }
    public Player getPlayer() { return player; }
    public Room getCurrentRoom() { return currentRoom; }
    public State getState() { return state; }
    public Fighting getFighting() { return fighting; }
    public MiniMap getMiniMap() { return miniMap; }
    public Descriptor getDescriptor() { return descriptor; }
    public HUD getHud() {
        return hud;
    }
    public ScanPanel getScanPanel() { return sp; }
    public Merchant getMerchant() { return merchant; }
    public MusicStuff getMusicStuff() { return musicStuff; }

    /* SETTERS */
    public void setState(State newState) {
        if (newState.equals(State.END)) {
            System.exit(0);
        }
        this.state = newState;
    }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
    public void setGridMap(GridMap gridMap) { this.gridMap = gridMap; }
    public boolean getHelp(){ return help;}
    public void setHelp(boolean help){ this.help = help; }
    public void setDungeon(Dungeon dungeon) { this.dungeon = dungeon; }
    public void setMiniMap(MiniMap miniMap) { this.miniMap = miniMap; }
    public void setHud(HUD hud) { this.hud = hud; }
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    /**
     * Update the range list according to the entity around the player.
     * The range list must be sorted before to call noZoningRange method.
     *
     * @param rangeList the rangelist to update
     */
    private void noZoningRange(List<Position> rangeList) {
        List<Position> newRangeList = new ArrayList<>();
        for (Position pos : rangeList) {
            List<Entity> entities = gridMap.getEntitiesAt(pos.getAbs(), pos.getOrd());
            for (Entity entity: entities) {
                if (entity.isMonster()) {
                    newRangeList.add(pos);
                    rangeList.clear();
                    rangeList.addAll(newRangeList);
                    return;
                }
            }
            newRangeList.add(pos);
        }
        rangeList.clear();
        rangeList.addAll(newRangeList);
    }

    /**
     * Sort the rangeList.
     * A TreeMap is created with Distance key and Position value.
     * The TreeMap is sorted according to the distance. Then the rangeList with a new ArrayList.
     *
     * @param rangeList to sort.
     */
    private void sortRangeList(List<Position> rangeList) {
        HashMap<Double, Position> ranges = new HashMap<>();
        List<Position> rangeSorted = new ArrayList<>();
        Position playerPos = player.getPosition();

        for (Position position : rangeList) {
            if (!position.equals(playerPos)) {
                double distance = StrategyUtils.getDistance(playerPos, position);
                ranges.put(distance, position);
            }
        }

        TreeMap<Double, Position> treeSorted = new TreeMap<>(ranges);

        for (Double key : treeSorted.keySet()) {
            rangeSorted.add(treeSorted.get(key));
        }

        rangeList.clear();
        rangeList.addAll(rangeSorted);
    }

    /**
     * Create the fight with the monsters encountered by the player.
     * @param monsters the monsters present in the current room.
     */
    private void initFight(List<LivingEntity> monsters) {
        musicStuff.playFightMusic();
        List<LivingEntity> fightList = new ArrayList<>(monsters);
        fightList.add(player);
        player.getPlayerStats().setSelectedSpell(player.getPlayerStats().getSpells().get(0));
        hud.spellSelectionString(0);
        fighting = new Fighting(fightList);
    }
}
