package game.entity.living.npc.merchants;

import game.entity.living.inventory.Inventory;
import game.entity.living.inventory.MerchantInventory;
import game.entity.living.npc.NPCStats;
import game.elements.GameState;
import game.menu.InGameMenu;
import utils.*;

/**
 * class handling the details of the generalMerchant npc.
 */
public class GeneralMerchant extends AbstractMerchant {

    private MerchantInventory merchantInventory;

    /**
     * create a general merchant with the super of abstract merchant, and is sprite and inventory.
     * @param position the position where we want it to spawn.
     */
    public GeneralMerchant(Position position) {
        super(position, "Jean-Charles", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
        this.merchantInventory = new MerchantInventory();
    }

    /**
     * handle what happen at the interaction with the general merchant
     * @param gameState state of the game when called.
     */
    @Override
    public void doInteraction(GameState gameState) {
        gameState.getMusicStuff().playMerchantFX();
        gameState.setState(State.SHOP_MENU);
        gameState.setMerchant(this);
        new InGameMenu(gameState);

    }

    /**
     * return the merchantInventory.
     * @return merchantInventory (of the InventoryClass)
     */
    public Inventory getMerchantInventory() {
        return merchantInventory;
    }
}
