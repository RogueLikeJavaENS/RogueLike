package entity.living.player;

import gameElement.GameState;
import stuff.Stuff;
import stuff.equipment.Equipment;
import stuff.equipment.EquipmentFactory;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;
import stuff.item.Item;
import stuff.item.ItemFactory;
import stuff.item.ItemType;
import utils.CoupleStuff;

import java.util.*;

public class Inventory {
    private List<Stuff> inventory;
    private Stuff selectedStuff;

    private boolean onEquipments;
    private boolean openInventory;

    private List<CoupleStuff> sortedEquipment;
    private List<CoupleStuff> sortedItem;
    private int indexOfSelectedStuff;

    // Comparator.comparing(Stuff::getName)
    public Inventory() {
        inventory = new ArrayList<>();

        sortedItem = new ArrayList<>();
        sortedEquipment = new ArrayList<>();

        openInventory = false;
//            /* TEST à enlever */
//            EquipmentFactory equipmentFactory = new EquipmentFactory();
//            ItemFactory itemFactory = new ItemFactory();
//            inventory.add(itemFactory.getItem(ItemType.KEY));
//            inventory.add(itemFactory.getItem(ItemType.KEY));
//            inventory.add(itemFactory.getItem(ItemType.HEALTH_POTION));
//            inventory.add(itemFactory.getItem(ItemType.ELIXIR));
//            inventory.add(itemFactory.getItem(ItemType.XP_BOTTLE));
//            inventory.add(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.A));
//            inventory.add(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.A));
//            inventory.add(equipmentFactory.getEquipment(1, EquipmentType.ARMOR, EquipmentRarity.A));
//            inventory.add(equipmentFactory.getEquipment(2, EquipmentType.GLOVE, EquipmentRarity.A));
//            inventory.add(equipmentFactory.getEquipment(3, EquipmentType.HELMET, EquipmentRarity.A));
//            Collections.shuffle(inventory);
//            /* TEST  à enlever */
    }

    public void addItem(Stuff stuff) {
        inventory.add(stuff);
    }

    public boolean useItem(ItemType type, GameState gameState) {
        boolean used = false;
        Item itemToDelete = null;
        for (Stuff stuff : inventory) {
            if (stuff.isUsable()) {
                Item item = (Item) stuff;
                if (item.getType().equals(type)) {
                    item.useItem(gameState);
                    itemToDelete = item;
                    used = true;
                    break;
                }
            }
        }
        if (itemToDelete != null) {
            inventory.remove(itemToDelete);
        }
        return used;
    }

    public void openInventory() {
        openInventory = false;
        if (!inventory.isEmpty()) {
            openInventory = true;
            indexOfSelectedStuff = 0;
            sortedItem.clear();
            sortedEquipment.clear();

            for (Stuff stuff : inventory) {
                if (stuff.isUsable()) {
                    int index = containsStuff(stuff, sortedItem);
                    if (index != -1) {
                        sortedItem.get(index).addStuff();
                    } else {
                        sortedItem.add(new CoupleStuff(stuff));
                    }
                }
                else {
                    int index = containsStuff(stuff, sortedEquipment);
                    if (index != -1) {
                        sortedEquipment.get(index).addStuff();
                    } else {
                        sortedEquipment.add(new CoupleStuff(stuff));
                    }
                }
            }
            onEquipments = false;
            if (!sortedItem.isEmpty()) {
                selectedStuff = sortedItem.get(0).getStuff();
                onEquipments = false;
            }
            if (!sortedEquipment.isEmpty()) {
                selectedStuff = sortedEquipment.get(0).getStuff();
                onEquipments = true;
            }
        }
    }

    public void closeInventory() {
        openInventory = false;
    }

    public void switchCategory() {
        if (openInventory) {
            if (onEquipments) {
                if (!sortedItem.isEmpty()) {
                    selectedStuff = sortedItem.get(0).getStuff();
                    onEquipments = false;
                    indexOfSelectedStuff = 0;
                }
            } else {
                if (!sortedEquipment.isEmpty()) {
                    selectedStuff = sortedEquipment.get(0).getStuff();
                    onEquipments = true;
                    indexOfSelectedStuff = 0;
                }
            }
        }
    }

    public void nextSelectedStuff() {
        if (openInventory) {
            List<CoupleStuff> list;
            if (onEquipments) {
                list = sortedEquipment;
            } else {
                list = sortedItem;
            }
            indexOfSelectedStuff = (indexOfSelectedStuff +1) % list.size();
            selectedStuff = list.get(indexOfSelectedStuff).getStuff();
        }
    }

    public void previousSelectedStuff() {
        if (openInventory) {
            List<CoupleStuff> list;
            if (onEquipments) {
                list = sortedEquipment;
            } else {
                list = sortedItem;
            }
            indexOfSelectedStuff = (indexOfSelectedStuff+list.size() - 1) % list.size();
            selectedStuff = list.get(indexOfSelectedStuff).getStuff();
        }
    }

    public void useSelectedStuff() {
        if (openInventory) {
            if (onEquipments) {
                int index = containsStuff(selectedStuff, sortedEquipment);
                Equipment equipment = (Equipment) selectedStuff;
                System.out.printf("%s Equipé !\n", equipment.getName());
                inventory.remove(selectedStuff);
            } else {
                int index = containsStuff(selectedStuff, sortedItem);
                Item item = (Item) selectedStuff;
                System.out.printf("%s utilisé !\n", item.getName());
                inventory.remove(selectedStuff);
            }
            openInventory();
        }
    }

    public int getItemNumber(ItemType type) {
        int acc = 0;
        for (Stuff stuff : inventory) {
            if (stuff.isUsable()) {
                Item item = (Item) stuff;
                if (item.getType().equals(type)) {
                    acc++;
                }
            }
        }
        return acc;
    }

    @Override
    public String toString() {
        if (openInventory) { // If the inventory is not empty
            List<CoupleStuff> listTodisplay;
            if (onEquipments) {
                listTodisplay = sortedEquipment;
            }
            else listTodisplay = sortedItem;

            StringBuilder sb = new StringBuilder();
            for (CoupleStuff coupleStuff : listTodisplay) {
                if (coupleStuff.getStuff().equals(selectedStuff)) {
                    sb.append(" -> ");
                } else sb.append("    ");
                sb.append(coupleStuff.getStuff().getName()).append(" ").append(coupleStuff.getCount()).append("\n");
            }
            return sb.toString();
        } else {
            return "Empty Inventory.";
        }
    }

    public boolean containsItem(ItemType type){
        for (Stuff stuff : inventory){
            if (stuff.isUsable()){
                Item item = (Item) stuff;
                if (item.getType() == type){
                    return true;
                }
            }
        }
        return false;
    }

    private static int containsStuff(Stuff stuff, List<CoupleStuff> coupleStuffList) {
        for (int i = 0; i < coupleStuffList.size(); i++) {
            if (coupleStuffList.get(i).getStuff().equals(stuff)) {
                return i;
            }
        }
        return -1;
    }

    public void removeItem(ItemType type){
        for(Stuff stuff : inventory){
            if (stuff.isUsable()){
                Item item = (Item) stuff;
                if (item.getType() == type){
                    inventory.remove(item);
                }
            }
        }
    }
}
