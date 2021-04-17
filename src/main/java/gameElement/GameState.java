package gameElement;

import display.Descriptor;
import display.GridMap;
import entity.Entity;
import entity.living.LivingEntity;
import entity.living.npc.merchants.Merchant;
import entity.living.player.Player;
import entity.living.npc.monster.Monster;
import entity.object.potion.Potion;
import entity.object.potion.PotionFactory;
import spells.Range;
import spells.Spell;
import utils.Colors;
import utils.Position;
import utils.State;
import java.util.ArrayList;
import java.util.List;
import static com.diogonunes.jcolor.Ansi.colorize;
/**
 * This class represents the State of the Game at any moment. Each action will pass by this class.
 * It determines rather or not if the action is permitted.

 *
 * @author Antoine
 */

public class GameState {
    private State state;
    private Room currentRoom;
    private final Player player;
    private Dungeon dungeon;
    private GridMap gridMap;
    private Fighting fighting;
    private boolean help;
    private int floor;
    private MiniMap miniMap;
    private Range range;
    private final GameRule gameRule;
    private final Descriptor descriptor;

    public GameState(Player player, Dungeon dungeon) {
        this.dungeon = dungeon;
        this.player = player;
        this.currentRoom = dungeon.getRoomList().get(0);
        this.gridMap = dungeon.getGridMap(currentRoom);
        this.gameRule = new GameRule();
        this.help = false;
        this.miniMap = new MiniMap(dungeon, this);
        this.descriptor = new Descriptor();
        player.setPosition(currentRoom.getCenter());
        state = State.NORMAL;
        gridMap.update(player, true);
        isThereMonsters();
    }

    public void updateRange() {
        Spell spell = player.getSelectedSpell();
        spell.setRange(player.getPosition(), player.getDirection());
        range = spell.getRange();
        gridMap.updateRangeList(range);
    }

    /**
     * Use to change the current room.
     * @param room The new room
     */
    public void updateChangingRoom(Room room) {
        gridMap.update(player, false);      // remove the player from the previous room
        setCurrentRoom(room);                   // set the current room with the new room
        setGridMap(dungeon.getGridMap(room));   // take the gridmap that represent the new room
        gridMap.update(player, true);       // add the ^player to the new room
        changeRoomFight();                       // check if the Room contains monster
        isOnEntity();
    }

    /**
     * Checks if the player can access the tile at his position + x & y, and changes its position if so.
     *
     * @param x Abscissa of the tile to check, using the player's position as a base.
     * @param y Ordinate of the tile to check, using the player's position as a base.
     *
     * @author Raphael
     */
    public boolean movePlayer(int x, int y) {
        boolean acted = false;                  // boolean used in RogueLike to see if the player consumed his action.
        int abs = player.getPosition().getAbs();
        int ord = player.getPosition().getOrd();

        if (gridMap.getTileAt(abs + x, ord + y).isAccessible()) {  // check if the wanted direction is accessible.
            boolean accessibilityEntity = true;
            List<Entity> entitiesAt = gridMap.getEntitiesAt(abs + x, ord + y);
            for (Entity entity : entitiesAt){   // check if there are no Entity that prevent the player to move on.
                if (!entity.getIsAccessible()){
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
     */
    public boolean interact() {
        Position toInteractPos = player.getPosition().getPosInFront(player.getDirection());
        List<Entity> entities = gridMap.getEntitiesAt(toInteractPos.getAbs(), toInteractPos.getOrd());
        if (entities.size() != 0) {
            for (Entity entity: entities) {
                if (entity instanceof Merchant) {
                    ((Merchant) entity).doInteraction(this);
                }
            }
            return true;
        } else {
            System.out.println("There is nothing to interact with !");
            return false;
        }
    }


    /**
     * Check if the player moved on Entity.
     * The methods throw the action related to the entity.
     */
    public void isOnEntity() {
        int playerAbs = player.getPosition().getAbs();
        int playerOrd = player.getPosition().getOrd();
        List<Entity> entitiesAt = gridMap.getEntitiesAt(playerAbs, playerOrd);
        for(Entity entity : entitiesAt) {
            if (entity != player) {
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

    /**
     * Check if the player is alive after a moves or a monster attack.
     */
    public void isPlayerAlive() {
        if (player.getPlayerStats().getLifePointActual() == 0) {
            setState(State.LOSE);
        }
    }

    /**
     * Use to check if a monster is alive. If not, gives XP and potions to the player.
     * @param monster the monster to check if he's alive.
     */
    public void isMonsterAlive(Monster monster) {
        if (monster.getMonsterStats().getLifePointActual() == 0) {
            player.getPlayerStats().grantXP(monster.getMonsterStats().getXpWorth());
            int potionNumber = gameRule.getPotionNumber();
            int nbXpBottle = 0;
            int nbElixir = 0;
            int nbHpPotion = 0;
            PotionFactory potionFactory = new PotionFactory();
            for (int i = 0; i < potionNumber; i++) {
                Potion potion = potionFactory.getPotion(gameRule.getPotionType());
                player.pickupPotion(potion);
                if (potion.getPotionType() == 0){
                    nbHpPotion += 1;
                }
                else if (potion.getPotionType() == 1){
                    nbElixir +=1;
                }
                else {
                    nbXpBottle+=1;
                }
            }
            fighting.removeMonster(monster);
            this.getDescriptor().updateDescriptor(String.format("%s killed %s, picked up %d XP bottle(s), %d Elixir(s), %d Health Potion(s) and gained %d xp",
                    player.getName(), monster.getName(), nbXpBottle, nbElixir, nbHpPotion, monster.getMonsterStats().getXpWorth()));
            gridMap.update(monster, false);
        }
    }

    /**
     *
     */
    public void changeRoomFight() {
        setState(State.NORMAL);
        isThereMonsters();
    }

    /**
     * Create the fight with the monsters encountered by the player.
     * @param monsters the monsters present in the current room.
     */
    private void initFight(List<LivingEntity> monsters) {
        List<LivingEntity> fightList = new ArrayList<>(monsters);
        fightList.add(player);
        fighting = new Fighting(fightList);
    }

    public boolean useSpell() {
        Spell spell = player.getSelectedSpell();
        if (player.getPlayerStats().consumeMp(spell.getManaCost())) {
            for (Position pos : gridMap.getRangeList()) {
                List<Entity> entityList = gridMap.getEntitiesAt(pos.getAbs(), pos.getOrd());
                for (Entity currentEntity : entityList) {
                    if (pos.equals(currentEntity.getPosition()) && currentEntity instanceof Monster) {
                        Monster monster = (Monster) currentEntity;
                        int damages = (int)Math.ceil(spell.getDamageMult() * player.getPlayerStats().getDamageTotal());
                        monster.getMonsterStats().sufferDamage(damages);
                        descriptor.updateDescriptor(String.format("%s used %s for %s mana and inflicted %s damages to the %s !",
                                player.getName(),
                                spell,
                                colorize(Integer.toString(spell.getManaCost()), Colors.BLUE.textApply()),
                                colorize(Integer.toString(damages), Colors.ORANGE.textApply()),
                                monster.getName()));
                        isMonsterAlive(monster);
                    }
                }
            }
            return true;
        } else {
            descriptor.updateDescriptor("Not enough PM !");
            return false;
        }
    }

    /**
     * Exit the games by setting the state at END.
     */
    public void exitGame() {
        state = State.END;
    }

    /* GETTERS */
    public Dungeon getDungeon() { return dungeon; }
    public GridMap getGridMap() { return gridMap; }
    public Player getPlayer() { return player; }
    public Room getCurrentRoom() { return currentRoom; }
    public State getState() { return state; }
    public Fighting getFighting() { return fighting; }
    public int getFloor() { return floor; }
    public MiniMap getMiniMap() { return miniMap; }
    public GameRule getGameRule() { return gameRule; }
    public Descriptor getDescriptor() { return descriptor; }

    /* SETTERS */
    public void setState(State newState) {this.state = newState; }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
    public void setGridMap(GridMap gridMap) { this.gridMap = gridMap; }
    public boolean getHelp(){ return help;}
    public void setHelp(boolean help){ this.help = help; }
    public void setFloor(int floor) { this.floor = floor; }
    public void setDungeon(Dungeon dungeon) { this.dungeon = dungeon; }
    public void setMiniMap(MiniMap miniMap) { this.miniMap = miniMap; }
}
