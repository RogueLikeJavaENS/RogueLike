package game.entity.object.elements;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.boss.Boss;
import game.entity.object.ObjectEntity;
import game.elements.GameRule;
import game.elements.GameState;
import game.stuff.Stuff;
import game.stuff.equipment.Equipment;
import game.stuff.equipment.EquipmentFactory;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.item.Item;
import game.stuff.item.ItemFactory;
import game.stuff.item.ItemType;
import utils.Colors;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

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
        EquipmentFactory equipmentFactory = new EquipmentFactory(gameState.getPlayer().getClasse());

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
        gameState.getMusicStuff().playGraveFX();
        StringBuilder dropDescriptor = new StringBuilder("That monster had: ");
        //the if statement is only here for the "and" after the listing of the items
        if (droppedItems.size() > 0) {
            //Items the monster carried and dropped are given to the player
            dropDescriptor.append("You found : ");

            for (int i = 0; i < droppedItems.size(); i++) {
                Stuff stuff = droppedItems.get(i);
                if (stuff.isUsable()) {
                    Item item = (Item) stuff;
                    dropDescriptor.append(String.format(
                            "%s", colorize(item.getType().toString(), ItemType.getColor(item.getType()).textApply())));
                } else {
                    Equipment equipment = (Equipment) stuff;
                    dropDescriptor.append(String.format(
                            "%s", colorize(equipment.getName(), EquipmentRarity.getColor(equipment.getRarity()).textApply())));
                 }
                if (i == droppedItems.size()-1) {
                    dropDescriptor.append(".");
                } else {
                    dropDescriptor.append(", ");
                }
                gameState.getPlayer().getInventory().addItem(stuff);
            }
            gameState.getDescriptor().updateDescriptor(dropDescriptor.toString());
        }
        //then the money from the monster
        gameState.getPlayer().getPlayerStats().gainMoney(droppedMoney);
        gameState.getDescriptor().updateDescriptor(String.format(
                "You found %s BTC in the chest", colorize(String.valueOf(droppedMoney), Attribute.BOLD(), Colors.YELLOW.textApply())));
        //remove the game.entity from the grid (and the whole game with the garbage collector)
        gameState.getGridMap().update(this, false);
    }
}
