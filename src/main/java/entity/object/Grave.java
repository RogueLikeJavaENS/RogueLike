package entity.object;

import entity.living.npc.monster.Monster;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.Inventory;
import stuff.Stuff;
import stuff.item.Item;
import stuff.item.ItemFactory;
import utils.Colors;
import utils.State;

public class Grave extends ObjectEntity {
    private final Inventory droppedItems;
    private final int droppedMoney;

    public Grave(Monster monster, GameRule gameRule) {
        super(monster.getPosition(), Colors.LIGHT_GREY, false);
        setSprites("/+\\", "|_|", Colors.LIGHT_GREY);
        droppedItems = new Inventory();
        //plus tard quand les monstres auront des items dans un inventaire ça sera modifié en conséquent
        int potionNumber = gameRule.getPotionNumber();
        ItemFactory itemFactory = new ItemFactory();
        for (int i = 0; i < potionNumber; i++) {
            Item potion = itemFactory.getItem(gameRule.getPotionType());
            droppedItems.addItem((Stuff) potion);
        }
        droppedMoney = monster.getMonsterStats().getMoneyCount();
    }

    public void doInteraction(GameState gameState) {
        StringBuilder dropDescriptor = new StringBuilder("That monster had:\n");
        //the if statement is only here for the "and" after the listing of the items
        if (droppedItems.getInventory().size() > 0) {
            //Items the monster carried and dropped are given to the player
            for (Stuff item : droppedItems.getInventory()) {
                gameState.getPlayer().getInventory().addItem(item);
                dropDescriptor.append('-')
                        .append(item.toString())
                        .append('\n');
            }
            dropDescriptor.append("and");
        }
        //then the money from the monster
        gameState.getPlayer().getPlayerStats().gainMoney(droppedMoney);
        dropDescriptor.append(String.format("%s coins!\n", droppedMoney));

        gameState.getDescriptor().updateDescriptor(dropDescriptor.toString());
        //remove the entity from the grid (and the whole game with the garbage collector)
        gameState.getGridMap().update(this, false);
    }

    public Inventory getDroppedItems() { return droppedItems; }
}
