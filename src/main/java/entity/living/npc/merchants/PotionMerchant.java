package entity.living.npc.merchants;
import entity.living.npc.NPCStats;
import entity.living.player.Player;
import entity.object.potion.*;
import gameElement.GameState;
import utils.*;
import java.util.ArrayList;
import java.util.HashMap;
import static com.diogonunes.jcolor.Ansi.colorize;

public class PotionMerchant extends AbstractMerchant {

    public PotionMerchant(Position position) {
        super(position, "Jean-Charle", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        super.setShop(initShop());
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add(colorize ("~.~", Colors.YELLOW.textApply()));
        sprites.add(colorize("|_|",Colors.YELLOW.textApply()));
        setSprites(sprites);
    }

    /**
     * Not use in the first version
     * @return a shop containing potions.
     */
    private HashMap<Potion, Integer> initShop() {
        HashMap<Potion, Integer> shop = new HashMap<>();
        PotionFactory potionFactory = new PotionFactory();
        shop.put(potionFactory.getPotion(0), 10);
        shop.put(potionFactory.getPotion(1), 10);
        shop.put(potionFactory.getPotion(2), 20);

        return shop;
    }

    @Override
    public void doInteraction(GameState gameState) {
        Player player = gameState.getPlayer();
        PotionFactory potionFactory = new PotionFactory();
        if (player.getPlayerStats().getMoneyCount() >= 10) {
            player.getPlayerStats().spendMoney(10);
            player.pickupPotion(potionFactory.getPotion(gameState.getGameRule().getPotionType()));
        } else {
            // not enough money.
        }
    }
}
