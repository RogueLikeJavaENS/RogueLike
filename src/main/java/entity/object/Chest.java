package entity.object;

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
import utils.Colors;
import utils.Position;

public class Chest extends ObjectEntity {

    private final boolean isClassic;
    private boolean opened;

    public Chest(Position position, boolean isClassic) {
        super(position, Colors.BROWN, false);
        this.isClassic = isClassic;
        if (isClassic) {
            setSprites(" _ ", "[¤]", Colors.BROWN);
        }
        else{
            setSprites(" _ ", "[¤]", Colors.YELLOW);
        }
        this.opened = false;
    }

    private void fillChest(boolean isClassic, GameState gameState){
        GameRule gr = new GameRule();
        Player player = gameState.getPlayer();
        int nbEquipment = gr.getNumberOfEquipmentInChest();

        int nbPotion = gr.getNumberOfPotionInChest(isClassic);
        int nbElixir = 0;
        int nbHealthPotion = 0;
        int nbXPBottle = 0;
        ItemFactory itemFactory = new ItemFactory();
        for (int i=0; i<nbPotion; i++){
            Item itemToAdd = itemFactory.getItem(gr.getPotionType());
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
        if (nbElixir > 0){
            gameState.getDescriptor().updateDescriptor(String.format("You found %d %s in the chest",nbElixir,ItemType.ELIXIR));
        }
        if (nbHealthPotion > 0){
            gameState.getDescriptor().updateDescriptor(String.format("You found %d %s in the chest",nbHealthPotion,ItemType.HEALTH_POTION));
        }
        if(nbXPBottle > 0){
            gameState.getDescriptor().updateDescriptor(String.format("You found %d %s in the chest",nbXPBottle,ItemType.XP_BOTTLE));
        }


        EquipmentFactory equipmentFactory = new EquipmentFactory();
        for(int i=0; i<nbEquipment; i++){
            EquipmentType equipmentType = gr.getEquipmentType();
            EquipmentRarity equipmentRarity = gr.getEquipmentRarity(isClassic);
            Equipment equipment = equipmentFactory.getEquipment(player.getPlayerStats().getLevel(),equipmentType,equipmentRarity);
            player.getInventory().addItem(equipment);
            gameState.getDescriptor().updateDescriptor(String.format("You found a %s %s in the chest",equipment.getName(),equipment.getRarity()));
        }

        //Gold
        int nbGold = gr.getNumberOfGoldInChest(isClassic);
        player.getPlayerStats().gainMoney(nbGold);
        gameState.getDescriptor().updateDescriptor(String.format("You found %d gold in the chest",nbGold));
    }

    @Override
    public void doInteraction(GameState gameState) {
        if (!opened) {
            opened = true;
            setSprites("/  ", "[¤]", Colors.BROWN);
            fillChest(isClassic,gameState);
        } else {
            // already opened !
        }
    }
}
