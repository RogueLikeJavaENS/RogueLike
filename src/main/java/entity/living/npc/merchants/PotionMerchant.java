package entity.living.npc.merchants;

import entity.living.npc.NPCStats;
import gameElement.GameState;
import gameElement.menu.Menu;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;
import stuff.equipment.equipments.*;
import stuff.item.potions.Elixir;
import stuff.item.potions.PotionHealth;
import stuff.item.potions.XpBottle;
import utils.*;


public class PotionMerchant extends AbstractMerchant {

    private MerchantInventory merchantInventory;

    public PotionMerchant(Position position) {
        super(position, "Jean-Charles", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
        this.merchantInventory = new MerchantInventory();
    }

    @Override
    public void doInteraction(GameState gameState) {
        //merchantInventory.openSellingSHop(gameState);
        gameState.setState(State.SHOP_MENU);
        gameState.setMenu(new Menu(gameState.getState()));
        gameState.setMerchant(this);
    }

    public MerchantInventory getMerchantInventory() {
        return merchantInventory;
    }
}
