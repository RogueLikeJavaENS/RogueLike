package entity.object;

import entity.living.npc.monster.Monster;
import entity.living.npc.monster.boss.Boss;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.Stuff;
import stuff.equipment.EquipmentFactory;
import stuff.item.ItemFactory;
import stuff.item.ItemType;
import utils.Colors;

import java.util.ArrayList;
import java.util.List;

public class Grave extends ObjectEntity {
    private final List<Stuff> droppedItems;
    private final int droppedMoney;

    public Grave(Monster monster, GameRule gameRule, GameState gameState) {
        super(monster.getPosition(), Colors.LIGHT_GREY, false, false);
        setSprites("/+\\", "|_|", Colors.LIGHT_GREY);
        droppedItems = new ArrayList<>();
        //plus tard quand les monstres auront des items dans un inventaire ça sera modifié en conséquent
        int level = gameState.getPlayer().getPlayerStats().getLevel();
        ItemFactory itemFactory = new ItemFactory();
        EquipmentFactory equipmentFactory = new EquipmentFactory(gameState);
        for (int i = 0; i < gameRule.getNumberOfPotionsOnCorpse(); i++) {
            droppedItems.add(itemFactory.getItem(gameRule.getPotionType(), level));
        }
        if (monster instanceof Boss) {
            for (int i = 0; i < gameRule.getNumberOfEquipmentsOnBossCorpse(); i++) {
                droppedItems.add(equipmentFactory.getEquipment(level, gameRule.getEquipmentTypeInMerchantShop(), gameRule.getRarityOnBossCorpse()));
            }
            droppedItems.add(itemFactory.getItem(ItemType.FLOORKEY, level));
        } else {
            for (int i = 0; i < gameRule.getNumberOfEquipmentsOnCorpse(); i++) {
                droppedItems.add(equipmentFactory.getEquipment(level, gameRule.getEquipmentTypeInMerchantShop(), gameRule.getEquipmentRarity(true)));
            }
        }
        droppedMoney = monster.getMonsterStats().getMoneyCount();
    }

    public void doInteraction(GameState gameState) {
        StringBuilder dropDescriptor = new StringBuilder("That monster had: ");
        //the if statement is only here for the "and" after the listing of the items
        if (droppedItems.size() > 0) {
            //Items the monster carried and dropped are given to the player
            for (Stuff item : droppedItems) {
                gameState.getPlayer().getInventory().addItem(item);
                dropDescriptor.append('-')
                        .append(item.getName())
                        .append('\n');
            }
            dropDescriptor.append(" and ");
        }
        //then the money from the monster
        gameState.getPlayer().getPlayerStats().gainMoney(droppedMoney);
        dropDescriptor.append(String.format("%s coins!", droppedMoney));

        gameState.getDescriptor().updateDescriptor(dropDescriptor.toString());
        //remove the entity from the grid (and the whole game with the garbage collector)
        gameState.getGridMap().update(this, false);
    }
}
