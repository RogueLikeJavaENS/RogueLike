package game.entity.living.npc.merchants;

import game.entity.living.inventory.Inventory;
import game.entity.living.inventory.MerchantInventory;
import game.entity.living.npc.NPCStats;
import game.element.GameState;
import game.menu.InGameMenu;
import utils.*;


public class GeneralMerchant extends AbstractMerchant {

    private MerchantInventory merchantInventory;

    public GeneralMerchant(Position position) {
        super(position, "Jean-Charles", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
        this.merchantInventory = new MerchantInventory();
    }

    @Override
    public void doInteraction(GameState gameState) {
        gameState.setState(State.SHOP_MENU);
        gameState.setMerchant(this);
        new InGameMenu(gameState);

    }

    public Inventory getMerchantInventory() {
        return merchantInventory;
    }
}
