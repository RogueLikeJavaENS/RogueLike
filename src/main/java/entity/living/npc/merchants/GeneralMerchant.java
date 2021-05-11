package entity.living.npc.merchants;

import entity.living.npc.NPCStats;
import gameElement.GameState;
import gameElement.menu.InGameMenu;
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
        merchantInventory.openSellingShop(gameState);
        gameState.setState(State.SHOP_MENU);
        new InGameMenu(gameState);
        gameState.setMerchant(this);
    }

    public MerchantInventory getMerchantInventory() {
        return merchantInventory;
    }
}
