package entity.living.npc.merchants;

import entity.living.npc.NPCStats;
import entity.living.player.Player;
import stuff.Stuff;
import stuff.item.Item;
import stuff.item.ItemFactory;
import gameElement.GameState;
import utils.*;


public class PotionMerchant extends AbstractMerchant {

    public PotionMerchant(Position position) {
        super(position, "Jean-Charles", Colors.WHITE, new NPCStats(100,100,5, 1, 1 ,1 ,1 ,1));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
    }


    @Override
    public void doInteraction(GameState gameState) {
        Player player = gameState.getPlayer();
        ItemFactory itemFactory = new ItemFactory();
        if (player.getPlayerStats().getMoneyCount() >= 10) {
            player.getPlayerStats().spendMoney(10);
            Item potion = itemFactory.getItem(gameState.getGameRule().getPotionType());
            player.getInventory().addItem((Stuff) potion);
            gameState.getDescriptor().updateDescriptor(String.format("%s bought a %s for %d BTC",player.getName(),potion,10));
        } else {
            gameState.getDescriptor().updateDescriptor(String.format("%s has not enough money to buy potion !", player.getName()));
            // not enough money.
        }
    }
}
