package entity.object;

import com.diogonunes.jcolor.Attribute;
import entity.living.player.Player;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.equipment.Equipment;
import stuff.equipment.EquipmentFactory;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;
import stuff.item.Item;
import stuff.item.ItemFactory;
import stuff.item.ItemType;
import stuff.item.keys.GoldKey;
import utils.Colors;
import utils.Position;

import java.awt.*;

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

    /**
     * Create a new Chest golden or classic
     *
     * @param position position of the chest
     * @param isClassic true if the chest is classic false if the chest is golden
     */
    public Chest(Position position, boolean isClassic) {
        super(position, Colors.WHITE, false,false);
        this.isClassic = isClassic;
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
     * @param isClassic boolean which indicate if the chest is classic or golden
     * @param gameState gameState of the game
     */
    private void fillChest(boolean isClassic, GameState gameState){
        GameRule gr = new GameRule();
        Player player = gameState.getPlayer();

        /// Fill the potions
        int nbPotion = gr.getNumberOfPotionInChest(isClassic);
        int nbElixir = 0;
        int nbHealthPotion = 0;
        int nbXPBottle = 0;
        ItemFactory itemFactory = new ItemFactory();
        for (int i=0; i<nbPotion; i++){
            System.out.println("in CHEST : " + gr.getPotionType());
            Item itemToAdd = itemFactory.getItem(gr.getPotionType(), player.getPlayerStats().getLevel());
            if (itemToAdd.getType() == ItemType.ELIXIR){
                nbElixir++;
            }
            else if (itemToAdd.getType() == ItemType.XP_BOTTLE){
                nbXPBottle++;
            }
            else if(itemToAdd.getType() == ItemType.HEALTH_POTION){
                nbHealthPotion++;
            }
            player.getInventory().addItem(itemToAdd);
        }
        if (nbElixir > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbElixir, colorize(ItemType.ELIXIR.toString(), Attribute.BOLD(), Colors.BLUE.textApply()))); }
        if (nbHealthPotion > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbHealthPotion, colorize(ItemType.HEALTH_POTION.toString(), Attribute.BOLD(), Colors.RED.textApply()))); }
        if(nbXPBottle > 0){ gameState.getDescriptor().updateDescriptor(String.format(
                "You found %d %s in the chest", nbXPBottle, colorize(ItemType.XP_BOTTLE.toString(), Attribute.BOLD(), Colors.GREEN.textApply()))); }

        /// Fill the equipment
        int nbEquipment = gr.getNumberOfEquipmentInChest();
        EquipmentFactory equipmentFactory = new EquipmentFactory(gameState.getPlayer().getClasse());
        if (nbEquipment > 0) {
            StringBuilder description = new StringBuilder();
            description.append("You found : ");
            for(int i=0; i<nbEquipment; i++){
                EquipmentType equipmentType = gr.getEquipmentType();
                EquipmentRarity equipmentRarity = gr.getEquipmentRarity(isClassic);
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
        int nbGold = gr.getNumberOfGoldInChest(isClassic);
        player.getPlayerStats().gainMoney(nbGold);
        gameState.getDescriptor().updateDescriptor(String.format(
                "You found %s BTC in the chest", colorize(String.valueOf(nbGold), Attribute.BOLD(), Colors.YELLOW.textApply())));

        /// Fill the golden key
        if (isClassic){
            if(gr.presenceOfGoldenKeyInClassicChest()){
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

        if(!opened){
            if (isClassic){
                opened = true;
                setSprites("/  ", "[¤]", Colors.BROWN);
                fillChest(true,gameState);
            }
            else{
                if(gameState.getPlayer().getInventory().containsItem(ItemType.GOLD_KEY)){
                    opened =true;
                    setSprites("/  ", "[¤]", Colors.YELLOW);
                    fillChest(false,gameState);
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
