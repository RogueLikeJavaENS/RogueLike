package entity.object;

import entity.living.player.Player;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.equipment.EquipmentFactory;
import stuff.item.Item;
import stuff.item.ItemFactory;
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
        ItemFactory itemFactory = new ItemFactory();
        for (int i=0; i<nbPotion; i++){
            Item itemToAdd = itemFactory.getItem(gr.getPotionType());
            // Add on the player inventory
        }
        EquipmentFactory equipmentFactory = new EquipmentFactory();
        for(int i=0; i<nbEquipment; i++){
            //Equipment equipment = equipmentFactory.getEquipment()
            // Add on the player inventory
        }

        //Gold
        int nbGold = gr.getNumberOfGoldInChest(isClassic);
        player.getPlayerStats().gainMoney(nbGold);
    }

    @Override
    public void doInteraction(GameState gameState) {
        if (!opened) {
            opened = true;
            // give items here
            setSprites("/  ", "[¤]", Colors.BROWN);
            System.out.println("add + items !");
        } else {
            // already opened !
        }
    }
}
