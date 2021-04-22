package entity.living.npc.merchants;
import entity.living.npc.NPCStats;
import entity.living.player.Player;
import items.AbstractItemFactory;
import items.Item;
import items.ItemType;
import items.object.Object;
import items.object.ObjectFactory;
import items.potion.*;
import gameElement.GameState;
import utils.*;
import java.util.HashMap;


public class PotionMerchant extends AbstractMerchant {

    public PotionMerchant(Position position) {
        super(position, "Jean-Charle", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
    }


    @Override
    public void doInteraction(GameState gameState) {
        Player player = gameState.getPlayer();
        ObjectFactory potionFactory = new ObjectFactory();
        if (player.getPlayerStats().getMoneyCount() >= 10) {
            player.getPlayerStats().spendMoney(10);
            Object potion = potionFactory.getObject(gameState.getGameRule().getPotionType());
            player.pickupPotion(potion);
            gameState.getDescriptor().updateDescriptor(String.format("%s bought a %s for %d BTC",player.getName(),potion.getName(),10));
        } else {
            gameState.getDescriptor().updateDescriptor(String.format("%s has not enough money to buy potion !", player.getName()));
            // not enough money.
        }
    }
}
