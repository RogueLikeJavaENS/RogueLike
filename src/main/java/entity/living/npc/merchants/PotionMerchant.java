package entity.living.npc.merchants;
import entity.living.npc.NPCStats;
import entity.living.player.Player;
import items.Item;
import items.potion.*;
import gameElement.GameState;
import utils.*;
import java.util.HashMap;


public class PotionMerchant extends AbstractMerchant {

    public PotionMerchant(Position position) {
        super(position, "Jean-Charle", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        super.setShop(initShop());
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
    }

    /**
     * Not use in the first version
     * @return a shop containing potions.
     */
    private HashMap<Item, Integer> initShop() {
        HashMap<Item, Integer> shop = new HashMap<>();
        PotionFactory potionFactory = new PotionFactory();
        shop.put((Item) potionFactory.getItemPotion(0), 10);
        shop.put((Item) potionFactory.getItemPotion(1), 10);
        shop.put((Item) potionFactory.getItemPotion(2), 20);

        return shop;
    }

    @Override
    public void doInteraction(GameState gameState) {
        Player player = gameState.getPlayer();
        PotionFactory potionFactory = new PotionFactory();
        if (player.getPlayerStats().getMoneyCount() >= 10) {
            player.getPlayerStats().spendMoney(10);
            Potion potion = potionFactory.getItemPotion(gameState.getGameRule().getPotionType());
            player.pickupPotion(potion);
            gameState.getDescriptor().updateDescriptor(String.format("%s bought a %s for %d BTC",player.getName(),potion.getPotionName(),10));
        } else {
            gameState.getDescriptor().updateDescriptor(String.format("%s has not enough money to buy potion !", player.getName()));
            // not enough money.
        }
    }
}
