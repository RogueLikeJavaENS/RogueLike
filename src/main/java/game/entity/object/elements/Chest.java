package game.entity.object.elements;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterFactory;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameRule;
import game.elements.GameState;
import game.stuff.equipment.Equipment;
import game.stuff.equipment.EquipmentFactory;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;
import game.stuff.item.Item;
import game.stuff.item.ItemFactory;
import game.stuff.item.ItemType;
import game.stuff.item.keys.GoldKey;
import game.stuff.item.potions.Elixir;
import game.stuff.item.potions.PotionHealth;
import game.stuff.item.potions.XpBottle;
import utils.Colors;
import utils.Position;

import java.util.Random;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class represent a Chest which could contains Equipment, Gold, Potion and Golden Key.
 * A chest could be classic of golden.
 * A chest could be open or close.
 *
 */
public class Chest extends ObjectEntity {

    private final boolean isClassic;
    private boolean opened;
    private final boolean isMimic;
    private final boolean isStart;

    /**
     * Create a new Chest golden or classic
     *
     * @param position position of the chest
     * @param isClassic true if the chest is classic false if the chest is golden
     */
    public Chest(Position position, boolean isClassic, boolean isMimic, boolean isStart) {
        super(position, Colors.WHITE, Colors.WHITE,false,false);
        this.isClassic = isClassic;
        this.isMimic = isMimic;
        this.isStart = isStart;
        if (isClassic) {
            setSprites(" _ ", "[¤]", Colors.BROWN);
        }
        else{
            setSprites(" _ ", "[¤]", Colors.YELLOW);
        }
        this.opened = false;
    }

    /**
     * Give to the player (directly in his inventory) what contains the chest (rule by GameRule)
     *
     * @param gameState gameState of the game
     */
    private void fillChest(GameState gameState){
        Player player = gameState.getPlayer();
        /// Fill the potions
        int nbElixir = 0;
        int nbHealthPotion = 0;
        int nbXPBottle = 0;
        if (!isStart) {
            int nbPotion = GameRule.getNumberOfPotionInChest(isClassic);
            for (int i=0; i<nbPotion; i++) {
                Item itemToAdd = ItemFactory.getItem(GameRule.getItemType(), player.getPlayerStats().getLevel());
                if (itemToAdd.getType() == ItemType.ELIXIR) {
                    nbElixir++;
                } else if (itemToAdd.getType() == ItemType.XP_BOTTLE) {
                    nbXPBottle++;
                } else if (itemToAdd.getType() == ItemType.HEALTH_POTION) {
                    nbHealthPotion++;
                }
            }
        } else {
            Random gen = new Random();
            nbHealthPotion = gen.nextInt(2)+2;
            nbElixir = gen.nextInt(1)+2;
        }

        for (int i=0; i<nbElixir; i++){
            player.getInventory().addItem(new Elixir());
        }
        for (int i = 0; i < nbHealthPotion; i++) {
            player.getInventory().addItem(new PotionHealth());
        }
        for (int i = 0; i < nbXPBottle; i++) {
            player.getInventory().addItem(new XpBottle());
        }

        if (nbElixir > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbElixir, colorize(ItemType.ELIXIR.toString(), Attribute.BOLD(), Colors.BLUE.textApply()))); }
        if (nbHealthPotion > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbHealthPotion, colorize(ItemType.HEALTH_POTION.toString(), Attribute.BOLD(), Colors.RED.textApply()))); }
        if(nbXPBottle > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbXPBottle, colorize(ItemType.XP_BOTTLE.toString(), Attribute.BOLD(), Colors.GREEN.textApply()))); }

        /// Fill the equipment
        int nbEquipment;
        if (isStart) {
            nbEquipment = GameRule.getNumberOfEquipmentInStartChest();
        } else {
            nbEquipment = GameRule.getNumberOfEquipmentInChest();
        }

        EquipmentFactory equipmentFactory = new EquipmentFactory(gameState.getPlayer().getClasse());
        if (nbEquipment > 0) {
            StringBuilder description = new StringBuilder();
            description.append("You found : ");
            for(int i=0; i<nbEquipment; i++){
                EquipmentRarity equipmentRarity;
                if (isStart) {
                    equipmentRarity = GameRule.getEquipmentRarityDropedStartChest();
                } else {
                    equipmentRarity = GameRule.getEquipmentRarityDroped(isClassic);
                }
                EquipmentType equipmentType = GameRule.getEquipmentTypeInChest();
                Equipment equipment = equipmentFactory.getEquipment(player.getPlayerStats().getLevel(),equipmentType,equipmentRarity);
                player.getInventory().addItem(equipment);
                description.append(String.format(
                        "%s",colorize(equipment.getName(), Attribute.BOLD(), EquipmentRarity.getColor(equipmentRarity).textApply())));
                if (i == nbEquipment-1) {
                    description.append(".");
                } else {
                    description.append(", ");
                }
            }
            gameState.getDescriptor().updateDescriptor(description.toString());
        }


        /// Fill the Gold
        int nbGold = GameRule.getNumberOfGoldInChest(isClassic);
        player.getPlayerStats().gainMoney(nbGold);
        gameState.getDescriptor().updateDescriptor(String.format(
                "You found %s BTC in the chest", colorize(String.valueOf(nbGold), Attribute.BOLD(), Colors.YELLOW.textApply())));

        /// Fill the golden key
        if (isClassic){
            if(GameRule.presenceOfGoldenKeyInClassicChest()){
                player.getInventory().addItem(new GoldKey());
                gameState.getDescriptor().updateDescriptor(colorize(
                        "You found a GoldKey in the chest !", Attribute.BOLD(), Colors.YELLOW.textApply()));
            }
        }
    }

    /**
     * Make the interaction (open the chest and give what is inside to the player) with the chest if it's not open yet and change it's sprite
     * @param gameState the gameState of the game
     */
    @Override
    public void doInteraction(GameState gameState) {
        if (isMimic) {
            Position pos = getPosition();
            gameState.getGridMap().update(this, false);
            MonsterFactory mf = new MonsterFactory(gameState.getDungeon().getFloor());
            Monster mimic = mf.getMonster(MonsterType.MIMIC.ordinal(), pos);
            gameState.getGridMap().update(mimic, true);
            gameState.isThereMonsters();
            gameState.getDescriptor().updateDescriptor("The chest was a mimic ! Prepare yourself !");
            return;
        }

        if(!opened){
            if (isClassic){
                gameState.getMusicStuff().playChestFX();
                opened = true;
                setSprites("/  ", "[¤]", Colors.BROWN);
                fillChest(gameState);
            }
            else{
                if(gameState.getPlayer().getInventory().useItem(ItemType.GOLD_KEY, gameState)){
                    gameState.getMusicStuff().playChestFX();
                    opened =true;
                    setSprites("/  ", "[¤]", Colors.YELLOW);
                    fillChest(gameState);

                }
                else{
                    gameState.getDescriptor().updateDescriptor("You don't have the gold key, you can't open this golden chest.");
                }
            }
        }
        else {
            gameState.getDescriptor().updateDescriptor("The chest is already open, there is nothing inside.");
        }
    }
}
