package game.entity.living.inventory;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.*;
import game.elements.GameRule;
import game.elements.GameState;
import game.stuff.Stuff;
import game.stuff.equipment.*;
import game.stuff.item.*;
import utils.Colors;
import utils.CoupleStuff;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * The Class is used To store all the Stuffs. The Structure of the class is shared with the player and the merchants.
 */
public abstract class AbstractInventory implements Inventory {
    protected List<Stuff> inventory;
    protected List<Stuff> equipped;
    protected Stuff selectedStuff;

    protected boolean onEquipments;

    protected boolean selling;

    protected List<CoupleStuff> sortedEquipment;
    protected List<CoupleStuff> sortedItem;
    protected int indexOfSelectedStuff;

    public AbstractInventory() {
        inventory = new ArrayList<>();
        equipped = new ArrayList<>();
        sortedItem = new ArrayList<>();
        sortedEquipment = new ArrayList<>();
        selling = false;
    }

    /**
     * Uses to add Stuff to the merchant or the player.
     * @param stuff the stuff to add.
     */
    public void addItem(Stuff stuff) {
        inventory.add(stuff);
    }

    /**
     * When removing the stuff. The method has to check if the stuff is an equipment.
     * If it is, the equipment must be removed from both Global Inventory and Sorted inventory.
     * If the Equipment is equipped, then return false.
     * @param stuff the stuff to remove.
     * @return a boolean if the stuff has been removed or not.
     */
    public boolean removeStuff(Stuff stuff) {
        boolean removed = false;
        List<CoupleStuff> listToDelete;
        if (stuff.isEquipable()) {
            listToDelete = sortedEquipment;
            Equipment e = (Equipment) stuff;
            if (e.isEquiped()) {
                return false;
            }
        }
        else {
            listToDelete = sortedItem;
        }
        int index = containsStuff(stuff, listToDelete);
        if (index != -1) {
            inventory.remove(stuff);
            boolean delete = listToDelete.get(index).removeStuff();
            if (delete) {
                listToDelete.remove(index);
            }
            removed = true;
        }
        return removed;
    }

    /**
     * Uses the selectedStuff in the inventory. If the selectedStuff is An Equipment,
     * Equipping or un-equipping it, if it's an item, use it.
     *
     * @param gameState to update the player or action when using an item.
     * @return if the selectedStuff has been used or not.
     */
    public boolean useSelectedStuff(GameState gameState) {
        boolean used = false;
        if (indexOfSelectedStuff != -1) {
            if (onEquipments) {
                Equipment equipment = (Equipment) selectedStuff;
                used = useEquipment(equipment, gameState);
            } else {
                int index = containsStuff(selectedStuff, sortedItem);
                Item item = (Item) selectedStuff;
                used = useItem(item.getType(), gameState);
                if (used) {
                    boolean delete = sortedItem.get(index).removeStuff();
                    if (delete) {
                        sortedItem.remove(index);
                    }
                }
            }
            placeSelectedStuff(false);
        }
        return used;
    }

    /**
     * Uses the items if it's possible to use. You can't use a floor key, but you can use a potion.
     * @param type the type of the item to use.
     * @param gameState the GameState to update the game by using it.
     * @return a boolean if the item has been used or not.
     */
    public boolean useItem(ItemType type, GameState gameState) {
        boolean used = false;
        Item itemToDelete = null;
        for (Stuff stuff : inventory) {
            if (stuff.isUsable()) {
                Item item = (Item) stuff;
                if (item.getType().equals(type)) {
                    used = item.useItem(gameState);
                    itemToDelete = item;
                    break;
                }
            }
        }
        System.out.println("Item used "+itemToDelete);
        System.out.println("utilisation "+used);
        if (itemToDelete != null && used) {
            inventory.remove(itemToDelete);
        }
        return used;
    }

    /**
     * If the equipment is already equipped, unequip it. Analog to equip it.
     *
     * @param equipment to equip or unequip.
     * @param gameState to change the state if the equipment has special effect.
     * @return a boolean if the equipment has been equipped or unequipped.
     */
    public boolean useEquipment(Equipment equipment, GameState gameState) {
        if (indexOfSelectedStuff == -1) {
            return false;
        }
        if (equipment.isEquiped()) {
            equipment.unequip();
            equipped.remove(equipment);
            updatePlayerStats(equipment, gameState, false);
            gameState.getDescriptor().updateDescriptor(String.format("%s unequipped the %s.", gameState.getPlayer().getName(), equipment.getName()));
        } else  {
            equipment.equip();
            Equipment equipmentToUnequip = null;
            for (Stuff s : equipped) {
                Equipment e = (Equipment) s;
                if (e.getType().equals(equipment.getType())) {
                    equipmentToUnequip = e;
                }
            }
            if (equipmentToUnequip != null) {
                equipmentToUnequip.unequip();
                equipped.remove(equipmentToUnequip);
                updatePlayerStats(equipmentToUnequip, gameState, false);
            }
            equipped.add(equipment);
            updatePlayerStats(equipment, gameState, true);
            gameState.getDescriptor().updateDescriptor(String.format("%s equipped the %s.", gameState.getPlayer().getName(), equipment.getName()));
        }
        return true;
    }

    /**
     * Opens the inventory. The inventory is sorted by equipment and items.
     * @param level the level of the player to set the price according to the player level.
     */
    public void openInventory(int level) {
        indexOfSelectedStuff = -1;
        if (!inventory.isEmpty()) {
            sortedItem.clear();
            sortedEquipment.clear();
            for (Stuff stuff : inventory) {
                if (stuff.isUsable()) {
                    int index = containsStuff(stuff, sortedItem);
                    if (index != -1) {
                        sortedItem.get(index).addStuff();
                    } else {
                        Item item = (Item) stuff;
                        stuff.setPrice(GameRule.getItemPrice(level, item.getType()));
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
            placeSelectedStuff(true);
        }
    }

    /**
     * Closes the inventory. Clear the sortedItem list and the sortedEquipment list.
     * The selected stuff is null as the index is -1.
     * When the indexOfSelectedStuff is -1, provide to use an none existing equipment.
     */
    public void closeInventory() {
        selectedStuff = null;
        sortedEquipment.clear();
        sortedItem.clear();
        indexOfSelectedStuff = -1;
    }

    /**
     * Switchs the display to the Items or Equipments.
     * If the sortedList to switch isn't empty. Places the index of the selected stuff at the first element.
     */
    public void switchCategory() {
        if (onEquipments) {
            onEquipments = false;
            if (sortedItem.isEmpty()) {
                selectedStuff = null;
                indexOfSelectedStuff = -1;
            } else {
                indexOfSelectedStuff = 0;
                selectedStuff = sortedItem.get(indexOfSelectedStuff).getStuff();
            }
        } else {
            onEquipments = true;
            if (sortedEquipment.isEmpty()) {
                selectedStuff = null;
                indexOfSelectedStuff = -1;
            } else {
                indexOfSelectedStuff = 0;
                selectedStuff = sortedEquipment.get(indexOfSelectedStuff).getStuff();
            }
        }
    }

    /**
     * Selects the next element in the inventory list. If the next element is the last one, places the index
     * of the selected stuff at the first stuff in the list.
     * @return a boolean if there was a next stuff. Provides unnecessary refresh.
     */
    public boolean nextSelectedStuff() {
        int previous = indexOfSelectedStuff;
        if (indexOfSelectedStuff != -1) {
            List<CoupleStuff> list;
            if (onEquipments) {
                list = sortedEquipment;
            } else {
                list = sortedItem;
            }
            indexOfSelectedStuff = (indexOfSelectedStuff +1) % list.size();
            selectedStuff = list.get(indexOfSelectedStuff).getStuff();
        }
        return indexOfSelectedStuff != previous;
    }

    /**
     * Selects the previous element in the inventory list.
     * @return a boolean if there was a next stuff. Provides unnecessary refresh.
     */
    public boolean previousSelectedStuff() {
        int previous = indexOfSelectedStuff;
        if (indexOfSelectedStuff != -1) {
            List<CoupleStuff> list;
            if (onEquipments) {
                list = sortedEquipment;
            } else {
                list = sortedItem;
            }
            indexOfSelectedStuff = (indexOfSelectedStuff+list.size() - 1) % list.size();
            selectedStuff = list.get(indexOfSelectedStuff).getStuff();
        }
        return indexOfSelectedStuff != previous;
    }

    /**
     * Get the Inventory list.
     * @return the List of Stuff.
     */
    public List<Stuff> getInventory() {
        return inventory;
    }

    /**
     * Removes an item by type.
     * @param type the type of the element to remove.
     */
    public void removeItem(ItemType type){
        Stuff itemToDelete = null;
        for(Stuff stuff : inventory){
            if (stuff.isUsable()){
                Item item = (Item) stuff;
                if (item.getType() == type){
                    itemToDelete = item;
                }
            }
        }
        if (itemToDelete != null) {
            inventory.remove(itemToDelete);
        }
    }

    /**
     * Checks if the itemType is contained in the inventory.
     * @param type the type of the item to check.
     * @return a boolean if the item is contained or not.
     */
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

    /**
     * Counts the number of the given type in the inventory.
     * @param type the type of the element to count.
     * @return the number the item's type.
     */
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

    /**
     * Get the String inventory.
     * @param gameState gameState used to check the floor and things like that.
     * @return the String of the inventory.
     */
    public String toStringInventory(GameState gameState) {
        StringBuilder sb = new StringBuilder();
        String head = "################################ | Q, D : Switch categories.\n" +
                "#           INVENTORY          # | Z, S : Switch selected stuffs. \n" +
                "################################ | E : Use/Equip/Unequip | ESC : Quit\n";
        String stats = toStringStats(gameState);
        String inventoryList = toStringInventoryList();
        sb.append(head).append(stats).append(inventoryList);
        sb.append(toStringEquipped());
        return sb.toString();
    }

    public String toStringEquipped() {
        return  "| Weapon Equipped: " + getEquipedEquipments(EquipmentType.WEAPON) + "\n" +
                "| Second hand    : " + getEquipedEquipments(EquipmentType.SHIELD) + "\n" +
                "| Helmet Equipped: " + getEquipedEquipments(EquipmentType.HELMET) + "\n" +
                "| Armor Equipped : " + getEquipedEquipments(EquipmentType.ARMOR) + "\n" +
                "| Pant Equipped  : " + getEquipedEquipments(EquipmentType.PANT) + "\n" +
                "| Glove Equipped : " + getEquipedEquipments(EquipmentType.GLOVE) + "\n" +
                "| Boot Equipped  : " + getEquipedEquipments(EquipmentType.BOOT) + "\n";
    }

    private String getEquipedEquipments(EquipmentType equipmentType) {
        StringBuilder sb = new StringBuilder();
        Equipment equipmentToPrint = null;
        for (Stuff stuff : equipped) {
            Equipment equipment = (Equipment) stuff;
            if (equipment.getType().equals(equipmentType)) {
                equipmentToPrint = equipment;
                break;
            }
        }
        if (equipmentToPrint == null) {
            return colorize("[ X ]", Colors.RED.textApply(), Attribute.BOLD());
        } else {
            sb.append("[ ")
            .append(colorize(equipmentToPrint.getName(), Attribute.BOLD()))
                    .append(" ");
            sb.append(colorize(equipmentToPrint.getRarity().toString(), EquipmentRarity.getColor(equipmentToPrint.getRarity()).textApply()))
                    .append(" ");
            sb.append("LVL ").append(equipmentToPrint.getLevel())
                    .append(" | ");
            int hpBonus = equipmentToPrint.getBonusLife();
            if (hpBonus > 0) {
                sb.append("+")
                        .append(hpBonus)
                        .append(colorize(" HP ", Attribute.BOLD(), Colors.RED.textApply()));
            }
            int mpBonus = equipmentToPrint.getBonusMana();
            if (mpBonus > 0) {
                sb.append("+")
                        .append(mpBonus)
                        .append(colorize(" MP ", Attribute.BOLD(), Colors.BLUE.textApply()));
            }
            int atkBonus = equipmentToPrint.getBonusDamage();
            if (atkBonus > 0) {
                sb.append("+")
                        .append(atkBonus)
                        .append(colorize(" ATK ", Attribute.BOLD()));
            }
            int defBonus = equipmentToPrint.getBonusArmor();
            if (defBonus > 0) {
                sb.append("+")
                        .append(defBonus)
                        .append(colorize(" DEF ", Attribute.BOLD()));
            }
            int agiBonus = equipmentToPrint.getBonusAgility();
            if (agiBonus > 0) {
                sb.append("+")
                        .append(agiBonus)
                        .append(colorize(" AGI ", Attribute.BOLD()));
            }
            sb.append(" ]");
        }
        return sb.toString();
    }

    /**
     * Places the selected stuff at the right index. If the SortedList is empty, the index is -1.
     * Provided to use a none existing equipment.
     * @param first if the inventory is opened for the first time or not.
     */
    protected void placeSelectedStuff(boolean first) {
        if (sortedEquipment.isEmpty() && sortedItem.isEmpty()) {
            selectedStuff = null;
            indexOfSelectedStuff = -1;
        }
        else {
            if (first) {
                indexOfSelectedStuff = 0;
                onEquipments = false;
                if (sortedItem.isEmpty()) {
                    selectedStuff = null;
                    indexOfSelectedStuff = -1;
                } else {
                    selectedStuff = sortedItem.get(indexOfSelectedStuff).getStuff();
                }
            }
            else {
                if (onEquipments) {
                    if (sortedEquipment.isEmpty()) {
                        selectedStuff=null;
                        indexOfSelectedStuff = -1;
                    }
                    else {
                        if (indexOfSelectedStuff == sortedEquipment.size()) {
                            indexOfSelectedStuff--;
                        }
                        selectedStuff = sortedEquipment.get(indexOfSelectedStuff).getStuff();
                    }
                }
                else {
                    if (sortedItem.isEmpty()) {
                        selectedStuff=null;
                        indexOfSelectedStuff = -1;
                    }
                    else {
                        if (indexOfSelectedStuff == sortedItem.size()) {
                            indexOfSelectedStuff--;
                        }
                        selectedStuff = sortedItem.get(indexOfSelectedStuff).getStuff();
                    }
                }
            }
        }
    }

    /**
     * Counts the number of stuffs in the inventory. Stack the elements in a CoupleStuff.
     * Avoids to display 99 Potions line by line in the inventory.
     *
     * @param stuff the stuff to stack.
     * @param coupleStuffList the list of the items or the Equipment.
     * @return the number of the the given stuff.
     */
    protected static int containsStuff(Stuff stuff, List<CoupleStuff> coupleStuffList) {
        for (int i = 0; i < coupleStuffList.size(); i++) {
            if (coupleStuffList.get(i).getStuff().isEquipable() && stuff.isEquipable()) {
                Equipment equipment = (Equipment) stuff;
                Equipment equipment1 = (Equipment) coupleStuffList.get(i).getStuff();
                if (equipment.equals(equipment1)) {
                    return i;
                }
            }
            else if (coupleStuffList.get(i).getStuff().isUsable() && stuff.isUsable()) {
                Item item = (Item) stuff;
                Item item1 = (Item) coupleStuffList.get(i).getStuff();
                if (item.equals(item1)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Sets the string of the inventory.
     * @return the string of the inventory.
     */
    protected String toStringInventoryList() {
        int MAX_HEIGHT = 8;
        StringBuilder sb = new StringBuilder();

        sb.append("|---------------------------------------------------------------------|\n");
        if (onEquipments) {
            sb.append("|##############    ITEMS    ######## ").append(colorize("-> EQUIPMENTS", Colors.RED.textApply())).append("    ################|\n");
        } else {
            sb.append("|############## ").append(colorize("-> ITEMS", Colors.RED.textApply())).append("    ########    EQUIPMENTS    ################\n");
        }
        sb.append("|_____________________________________________________________________|_________________________________\n" + "|    ")
                .append(colorize("Stuffs            | NB | R | LVL | BTC | Description", Attribute.BOLD()))
                .append("                                               |\n");

        if (indexOfSelectedStuff < MAX_HEIGHT) {
            sb.append("|-------------------------------------------------------------------------------------------------------------\n");
        } else {
            sb.append(colorize(
                    "|^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n",
                    Colors.LIGHT_GREY.textApply())
            );
        }


        List<CoupleStuff> listTodisplay;
        if (onEquipments) {
            listTodisplay = sortedEquipment;
        } else {
            listTodisplay = sortedItem;
        }
        int i = 0;

        for (CoupleStuff coupleStuff : listTodisplay) {
            if ((indexOfSelectedStuff < MAX_HEIGHT && i < MAX_HEIGHT)
                    || (indexOfSelectedStuff >= MAX_HEIGHT && ( i <= indexOfSelectedStuff && i > indexOfSelectedStuff-MAX_HEIGHT))) {
                sb.append(stuffLineToString(coupleStuff));
            }
            i++;
        }
        while (i < MAX_HEIGHT) {
            sb.append("|                      |    |   |     |     |                                                                  |\n");
            i++;
        }
        if ((onEquipments && (indexOfSelectedStuff == sortedEquipment.size()-1
                            || (sortedEquipment.size()-1 <= MAX_HEIGHT)))
            || (!onEquipments &&
                            (indexOfSelectedStuff == sortedItem.size()-1
                            || (sortedItem.size()-1 <= MAX_HEIGHT)))) {
            sb.append(" -------------------------------------------------------------------------------------------------------------- \n");
        } else {
            sb.append(colorize(
                    " vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv \n",
                    Colors.LIGHT_GREY.textApply())
            );
        }
        return sb.toString();
    }

    /**
     * Sets the string of the player stats.
     * @param gameState used to count some elements as the floor.
     * @return the string of the player stats.
     */
    protected String toStringStats(GameState gameState) {
        Player player = gameState.getPlayer();
        PlayerStats stats = player.getPlayerStats();

        int xp, name, btc, lvl, rge, atk, def, agi, mp, hp;
        if (onEquipments) {
            atk = bonusEquipment(false,false, true, false, false, stats);
            def = bonusEquipment(false,false, false, true, false, stats);
            agi = bonusEquipment(false,false, false, false, true, stats);
            mp = bonusEquipment(false,true, false, false, false, stats);
            hp = bonusEquipment(true,false, false, false, false, stats);
        } else {
            atk = Integer.MAX_VALUE;
            def = Integer.MAX_VALUE;
            agi = Integer.MAX_VALUE;
            mp = Integer.MAX_VALUE;
            hp = Integer.MAX_VALUE;
        }


        String HP_Bonus = colorBonus(hp);
        String MP_Bonus = colorBonus(mp);
        String Damage_Bonus = colorBonus(atk);
        String Armor_Bonus = colorBonus(def);
        String Agi_Bonus = colorBonus(agi);

        String playerName = player.getName();
        String XP = "XP : "+stats.getXp()+"/"+stats.getXpRequired();
        String MP = "MP : "+stats.getManaPointActual()+"/"+stats.getManaPointTotal();
        String HP = "HP : "+stats.getLifePointActual()+"/"+stats.getLifePointTotal();
        String BTC = "BTC: "+stats.getMoneyCount();
        String FLR = "FLR: "+ gameState.getDungeon().getFloor();
        String LVL = "LVL: " + stats.getLevel();
        String ATK = "ATK: " + stats.getDamageTotal();
        String DEF = "DEF: " + stats.getArmorTotal();
        String AGI = "AGI: " + stats.getAgilityTotal();
        String RGE = "RGE: " + stats.getRangeTotal();
        String KIL = "KIL: " + stats.getKillCounter();

        atk = bonusLength(atk) + ATK.length();
        def = bonusLength(def) + DEF.length();
        agi = bonusLength(agi) + AGI.length();
        mp = bonusLength(mp) + MP.length();
        hp = bonusLength(hp) + HP.length();
        xp = XP.length();
        name = playerName.length();
        btc = BTC.length();
        lvl = LVL.length();
        rge = RGE.length();

        XP = colorize(XP, Colors.GREEN.textApply());
        MP = colorize(MP, Colors.BLUE.textApply()) + MP_Bonus;
        HP = colorize(HP, Colors.RED.textApply()) + HP_Bonus;
        BTC = colorize(BTC, Colors.YELLOW.textApply());
        ATK = ATK + Damage_Bonus;
        DEF = DEF + Armor_Bonus;
        AGI = AGI + Agi_Bonus;

        return " _____________________________________________________________________\n" +
                "| " + playerName + " ".repeat(29 - name) + "| " +
                LVL + " ".repeat(37 - lvl) + "|\n" +
                "| " + XP + " ".repeat(29 - xp) + "| " +
                ATK + " ".repeat(37 - atk) + "|\n" +
                "| " + HP + " ".repeat(29 - hp) + "| " +
                DEF + " ".repeat(37 - def) + "|\n" +
                "| " + MP + " ".repeat(29 - mp) + "| " +
                AGI + " ".repeat(37 - agi) + "|\n" +
                "| " + BTC + " ".repeat(29 - btc) + "| " +
                RGE + " ".repeat(37 - rge) + "|\n" +
                "| " + FLR + " ".repeat(29 - FLR.length()) + "| " +
                KIL + " ".repeat(37 - KIL.length()) + "|\n";
    }

    /**
     * Checks the actual wanted Bonus.
     *
     * @param hp boolean if the type is wanted
     * @param mp boolean if the type is wanted
     * @param dmg boolean if the type is wanted
     * @param armor boolean if the type is wanted
     * @param agi boolean if the type is wanted
     * @param equipment the equipment to count the bonus.
     * @return the actual Bonus.
     */
    private int containsEquipedType(Boolean hp, Boolean mp, Boolean dmg, Boolean armor, Boolean agi, Equipment equipment) {
        for (Stuff stuff : equipped) {
            Equipment e = (Equipment) stuff;
            if (e.getType().equals(equipment.getType())) {
                if (hp) {
                    return e.getBonusLife();
                } else if (mp) {
                    return e.getBonusMana();
                } else if (dmg) {
                    return e.getBonusDamage();
                } else if (armor) {
                    return e.getBonusArmor();
                } else if (agi) {
                    return e.getBonusAgility();
                }
            }
        }
        return -1;
    }

    /**
     * Counts the bonus to add with the selected stuff.
     * @param hp boolean if the type is wanted
     * @param mp boolean if the type is wanted
     * @param dmg boolean if the type is wanted
     * @param armor boolean if the type is wanted
     * @param agi boolean if the type is wanted
     * @param stats use to check the player actual stats.
     * @return the Bonus given by the selected stuff according to the actual bonuses.
     */
    private int bonusEquipment(Boolean hp, Boolean mp, Boolean dmg, Boolean armor, Boolean agi, PlayerStats stats) {
        if (selectedStuff == null) {
            return Integer.MAX_VALUE;
        }
        if (selectedStuff.isEquipable()) {
            Equipment equipment = (Equipment) selectedStuff;
            int natural = 0;
            int total = 0;
            int bonus = -1;

            if (hp && equipment.getBonusLife() != 0) {
                natural = stats.getLifePointNatural();
                total = stats.getLifePointTotal();
                bonus = equipment.getBonusLife();
            }
            else if (mp && equipment.getBonusMana() != 0) {
                natural = stats.getManaPointNatural();
                total = stats.getManaPointTotal();
                bonus = equipment.getBonusMana();
            }
            else if (dmg && equipment.getBonusDamage() != 0) {
                natural = stats.getDamageNatural();
                total = stats.getDamageTotal();
                bonus = equipment.getBonusDamage();
            }
            else if (armor && equipment.getBonusArmor() != 0) {
                natural = stats.getArmorNatural();
                total = stats.getArmorTotal();
                bonus = equipment.getBonusArmor();
            }
            else if (agi && equipment.getBonusAgility() != 0) {
                natural = stats.getAgilityNatural();
                total = stats.getAgilityTotal();
                bonus = equipment.getBonusAgility();
            }
            if (bonus == -1) {
                return 0;
            }
            else {
                if (equipment.isEquiped()) {
                    return -bonus;
                }
                else if (!equipment.isEquiped()) {
                    int current_bonus = containsEquipedType(hp, mp, dmg, armor, agi, equipment);

                    if (natural + bonus == total) {
                        return 0;
                    }
                    else if (current_bonus == -1) {
                        return bonus;
                    }
                    else {
                        int dif = bonus - current_bonus;
                        if (dif > 0) {
                            return dif;
                        }
                        else if (dif < 0) {
                            return dif;
                        }
                        else {
                            return dif;
                        }
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    /**
     * The line of the stuff.
     * @param coupleStuff the stuff to display.
     * @return the string of the line of the stuff, contains the description, the rarity, the price etc...
     */
    private String stuffLineToString(CoupleStuff coupleStuff) {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        int name_size = 18;
        int nb_size = 3;
        int lvl_size = 4;
        int btc_size = 4;
        int des_size = 65;
        if (onEquipments && !sortedEquipment.isEmpty()) {
            Equipment selectedEquipment = (Equipment) selectedStuff;
            Equipment equipment = (Equipment) coupleStuff.getStuff();
            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize(" -> ", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("    ");
            /* NAME */
            if (equipment.isEquiped()) {
                sb.append(colorize(equipment.getName(), Colors.ORANGE.textApply()));
            } else {
                sb.append(equipment.getName());
            }
            sb.append(" ".repeat(name_size-equipment.getName().length()));

            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* NB */
            int nb = coupleStuff.getCount();
            sb.append(" ").append(nb).append(" ".repeat(nb_size- String.valueOf(nb).length()));


            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");
            /* RARITY */
            EquipmentRarity rarity = equipment.getRarity();
            sb.append(" ").append(colorize(rarity.toString(), EquipmentRarity.getColor(rarity).textApply()));

            sb.append(" ");
            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* LVL */
            int lvl = equipment.getLevel();
            sb.append(" ").append(lvl).append(" ".repeat(lvl_size-String.valueOf(lvl).length()));

            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");
            /* BTC */
            int btc;
            if (selling) {
                btc = equipment.getSellingPrice();
            } else {
                btc = equipment.getBuyingPrice();
            }
            sb.append(" ").append(colorize(String.valueOf(btc), Colors.YELLOW.textApply())).append(" ".repeat(btc_size-String.valueOf(btc).length()));

            if (equipment.equals(selectedEquipment)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* DESCRIPTION */
            String description = equipment.getDescription();
            description = description.replace(
                    equipment.getRarity().getRarity(),
                    colorize(equipment.getRarity().getRarity(), Attribute.BOLD(), EquipmentRarity.getColor(equipment.getRarity()).textApply()));
            sb.append(" ").append(description).append(" ".repeat(des_size-equipment.getDescription().length())).append("|\n");

        } else {
            Item selectedItem = (Item) selectedStuff;
            Item item = (Item) coupleStuff.getStuff();
            if (item.equals(selectedItem)) {
                sb.append(colorize(" -> ", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("    ");
            sb.append(item.getName());
            sb.append(" ".repeat(name_size-item.getName().length()));

            if (item.equals(selectedItem)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* NB */
            int nb = coupleStuff.getCount();
            sb.append(" ").append(nb).append(" ".repeat(nb_size- String.valueOf(nb).length()));

            if (item.equals(selectedItem)) {
                sb.append(colorize("|", Colors.RED.textApply(), Colors.LIGHT_GREY.bgApply(), Attribute.BOLD()));
            } else sb.append("|");

            /* RARITY */
            sb.append(" ").append(colorize("X ", Colors.GREY.textApply()));

            if (item.equals(selectedItem)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* LVL */
            sb.append(colorize(" XXX ", Colors.GREY.textApply()));

            if (item.equals(selectedItem)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* BTC */ // TODO make stuffs a cost.
            int btc;
            if (selling) {
                btc = item.getSellingPrice();
            } else {
                btc = item.getBuyingPrice();
            }
            sb.append(" ").append(colorize(String.valueOf(btc), Colors.YELLOW.textApply())).append(" ".repeat(btc_size-String.valueOf(btc).length()));

            if (item.equals(selectedItem)) {
                sb.append(colorize("|", Colors.RED.textApply(), Attribute.BOLD(), Colors.LIGHT_GREY.bgApply()));
            } else sb.append("|");

            /* DESCRIPTION */
            sb.append(" ").append(item.getDescription()).append(" ".repeat(des_size-item.getDescription().length())).append("|\n");
        }
        return sb.toString();
    }

    /**
     * Visits the playerStats to add or remove the bonus, depending on the boolean equip.
     * @param equipment the equipment to get the bonuses.
     * @param gameState to get the player.
     * @param equip if the equipment is equipped or un-equipped.
     */
    private void updatePlayerStats(Equipment equipment, GameState gameState, boolean equip) {
        Player player = gameState.getPlayer();
        int equipModifier;
        if (equip) {
            equipModifier = 1;
        } else {
            equipModifier = -1;
        }

        if (equipment.getBonusArmor() != 0) {
            player.getPlayerStats().changeArmorTotal(equipModifier * equipment.getBonusArmor());
        }
        if (equipment.getBonusDamage() != 0) {
            player.getPlayerStats().changeDamageTotal(equipModifier * equipment.getBonusDamage());
        }
        if (equipment.getBonusMana() != 0) {
            player.getPlayerStats().changeManaPointTotal(equipModifier * equipment.getBonusMana());
        }
        if (equipment.getBonusLife() != 0) {
            player.getPlayerStats().changeLifePointTotal(equipModifier * equipment.getBonusLife());
        }
        if (equipment.getBonusAgility() != 0) {
            player.getPlayerStats().editAgiltyActual(equipModifier * equipment.getBonusAgility());
        }
    }

    /**
     * Colors the bonus according to the bonus. Grey for 0, Red for negative and Green for positive bonus.
     * @param bonus the bonus to color.
     * @return the string with the colored bonus.
     */
    private String colorBonus(int bonus) {
        if (bonus == Integer.MAX_VALUE) {
            return "";
        } else if (bonus == 0) {
            return colorize(" (+0) ", Colors.GREY.textApply());
        } else if (bonus < 0) {
            return colorize(" ("+bonus+") ", Colors.RED.textApply());
        }
        else return colorize(" (+"+bonus+")", Colors.GREEN.textApply());
    }

    /**
     * Counts the length of the bonus without the coloring characters.
     * @param bonus the bonus to count.
     * @return the length.
     */
    private int bonusLength(int bonus) {
        if (bonus == Integer.MAX_VALUE) {
            return 0;
        } else if (bonus == 0) {
            return 6;
        } else if (bonus < 0) {
            return String.valueOf(bonus).length()+4;
        }
        else return String.valueOf(bonus).length()+4;
    }
}
