package entity.living.inventory;

import com.diogonunes.jcolor.Attribute;
import entity.living.player.Player;
import entity.living.player.PlayerStats;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.Stuff;
import stuff.equipment.Equipment;
import stuff.equipment.EquipmentRarity;
import stuff.item.Item;
import stuff.item.ItemType;
import utils.Colors;
import utils.CoupleStuff;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

public class AbstractInventory implements Inventory {
    protected List<Stuff> inventory;
    protected List<Stuff> equiped;
    protected Stuff selectedStuff;

    protected boolean onEquipments;

    protected boolean selling;

    protected List<CoupleStuff> sortedEquipment;
    protected List<CoupleStuff> sortedItem;
    protected int indexOfSelectedStuff;

    public AbstractInventory() {
        inventory = new ArrayList<>();
        equiped = new ArrayList<>();
        sortedItem = new ArrayList<>();
        sortedEquipment = new ArrayList<>();
        selling = false;
    }

    public void addItem(Stuff stuff) {
        inventory.add(stuff);
    }

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
        if (itemToDelete != null && used) {
            inventory.remove(itemToDelete);
        }
        return used;
    }

    public boolean useEquipment(Equipment equipment, GameState gameState) {
        if (indexOfSelectedStuff == -1) {
            return false;
        }
        if (equipment.isEquiped()) {
            equipment.unequip();
            equiped.remove(equipment);
            updatePlayerStats(equipment, gameState, false);
            gameState.getDescriptor().updateDescriptor(String.format("%s unequipped the %s.", gameState.getPlayer().getName(), equipment.getName()));
        } else  {
            equipment.equip();
            Equipment equipmentToUnequip = null;
            for (Stuff s : equiped) {
                Equipment e = (Equipment) s;
                if (e.getType().equals(equipment.getType())) {
                    equipmentToUnequip = e;
                }
            }
            if (equipmentToUnequip != null) {
                equipmentToUnequip.unequip();
                equiped.remove(equipmentToUnequip);
                updatePlayerStats(equipmentToUnequip, gameState, false);
            }
            equiped.add(equipment);
            updatePlayerStats(equipment, gameState, true);
            gameState.getDescriptor().updateDescriptor(String.format("%s equipped the %s.", gameState.getPlayer().getName(), equipment.getName()));
        }
        return true;
    }

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
        else if (equipment.getBonusDamage() != 0) {
            player.getPlayerStats().changeDamageTotal(equipModifier * equipment.getBonusDamage());
        }
        else if (equipment.getBonusMana() != 0) {
            player.getPlayerStats().changeManaPointTotal(equipModifier * equipment.getBonusMana());
        }
        else if (equipment.getBonusLife() != 0) {
            player.getPlayerStats().changeLifePointTotal(equipModifier * equipment.getBonusLife());
        }
        else if (equipment.getBonusInitiative() != 0) {
            player.getPlayerStats().editInitiativeActual(equipModifier * equipment.getBonusInitiative());
        }
    }

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

    public void openSellingShop(GameState gameState) {
        //
    }

    public void openBuyingSHop(GameState gameState) {
        //
    }

    public void openInventory(int level) {
        GameRule gameRule = new GameRule();
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
                        stuff.setPrice(gameRule.getPotionPrice(level, item.getType()));
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

    public void closeInventory() {
        selectedStuff = null;
        indexOfSelectedStuff = -1;
    }

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

    public String toStringInventory(GameState gameState) {
        StringBuilder sb = new StringBuilder();
        String head = "################################ | Q, D : Switch categories.\n" +
                "#           INVENTORY          # | Z, S : Switch selected stuffs. \n" +
                "################################ | E : Use/Equip/Unequip | ESC : Quit\n";
        String stats = toStringStats(gameState);
        String inventoryList = toStringInventoryList();
        sb.append(head).append(stats).append(inventoryList);

        return sb.toString();
    }

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

    protected String toStringInventoryList() {
        int MAX_HEIGHT = 6;
        StringBuilder sb = new StringBuilder();

        String separationItems =
                "|"+
                        colorize("----------------------", Colors.GREY.textApply())+"|"+
                        colorize("----", Colors.GREY.textApply())+"|"+
                        colorize("---", Colors.GREY.textApply())+"|"+
                        colorize("-----", Colors.GREY.textApply())+"|"+
                        colorize("-----", Colors.GREY.textApply())+"|"+
                        colorize("------------------------------------------------------------------", Colors.GREY.textApply())+"|\n";

        sb.append("|------------------------------|--------------------------------------|\n");
        if (onEquipments) {
            sb.append("|##############    ITEMS    ######## ").append(colorize("-> EQUIPMENTS", Colors.CYAN.textApply())).append("    ################|\n");
        } else {
            sb.append("|############## ").append(colorize("-> ITEMS", Colors.CYAN.textApply())).append("    ########    EQUIPMENTS    ################\n");
        }
        sb.append("|_____________________________________________________________________|_________________________________\n" + "|    ")
                .append(colorize("Stuffs            | NB | R | LVL | BTC | Description", Attribute.BOLD()))
                .append("                                               |\n");
        sb.append("|--------------------------------------------------------------------------------------------------------------\n");

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
                if (i != 0 ) {
                    sb.append(separationItems);
                }
                sb.append(stuffLineToString(coupleStuff));
            }
            i++;
        }
        while (i < MAX_HEIGHT) {
            sb.append(separationItems);
            sb.append("|                      |    |   |     |     |                                                                  |\n");
            i++;
        }
        sb.append(" -------------------------------------------------------------------------------------------------------------- \n");
        return sb.toString();
    }

    private int containsEquipedType(Boolean hp, Boolean mp, Boolean dmg, Boolean armor, Boolean ini, Equipment equipment) {
        for (Stuff stuff : equiped) {
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
                } else if (ini) {
                    return e.getBonusInitiative();
                }
            }
        }
        return -1;
    }

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
                sb.append(colorize(" -> ", Colors.CYAN.textApply()));
            } else sb.append("    ");
            /* NAME */
            if (equipment.isEquiped()) {
                sb.append(colorize(equipment.getName(), Colors.ORANGE.textApply()));
            } else {
                sb.append(equipment.getName());
            }
            sb.append(" ".repeat(name_size-equipment.getName().length())).append("|");

            /* NB */
            int nb = coupleStuff.getCount();
            sb.append(" ").append(nb).append(" ".repeat(nb_size- String.valueOf(nb).length())).append("|");

            /* RARITY */
            EquipmentRarity rarity = equipment.getRarity();
            sb.append(" ").append(colorize(rarity.toString(), EquipmentRarity.getColor(rarity).textApply())).append(" |");

            /* LVL */
            int lvl = equipment.getLevel();
            sb.append(" ").append(lvl).append(" ".repeat(lvl_size-String.valueOf(lvl).length())).append("|");
            /* BTC */
            int btc;
            if (selling) {
                btc = equipment.getSellingPrice();
            } else {
                btc = equipment.getBuyingPrice();
            }
            sb.append(" ").append(colorize(String.valueOf(btc), Colors.YELLOW.textApply())).append(" ".repeat(btc_size-String.valueOf(btc).length())).append("|");

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
                sb.append(colorize(" -> ", Colors.CYAN.textApply()));
            } else sb.append("    ");
            sb.append(item.getName());
            sb.append(" ".repeat(name_size-item.getName().length())).append("|");

            /* NB */
            int nb = coupleStuff.getCount();
            sb.append(" ").append(nb).append(" ".repeat(nb_size- String.valueOf(nb).length())).append("|");

            /* RARITY */
            sb.append(" ").append(colorize("X", Colors.GREY.textApply())).append(" |");

            /* LVL */
            sb.append(colorize(" XXX ", Colors.GREY.textApply())).append("|");

            /* BTC */ // TODO make stuffs a cost.
            int btc;
            if (selling) {
                btc = item.getSellingPrice();
            } else {
                btc = item.getBuyingPrice();
            }
            sb.append(" ").append(colorize(String.valueOf(btc), Colors.YELLOW.textApply())).append(" ".repeat(btc_size-String.valueOf(btc).length())).append("|");

            /* DESCRIPTION */
            sb.append(" ").append(item.getDescription()).append(" ".repeat(des_size-item.getDescription().length())).append("|\n");
        }
        return sb.toString();
    }

    protected String toStringStats(GameState gameState) {
        Player player = gameState.getPlayer();
        PlayerStats stats = player.getPlayerStats();

        int xp, name, btc, lvl, rge, atk, def, ini, mp, hp;
        if (onEquipments) {
            atk = bonusEquipment(false,false, true, false, false, stats);
            def = bonusEquipment(false,false, false, true, false, stats);
            ini = bonusEquipment(false,false, false, false, true, stats);
            mp = bonusEquipment(false,true, false, false, false, stats);
            hp = bonusEquipment(true,false, false, false, false, stats);
        } else {
            atk = Integer.MAX_VALUE;
            def = Integer.MAX_VALUE;
            ini = Integer.MAX_VALUE;
            mp = Integer.MAX_VALUE;
            hp = Integer.MAX_VALUE;
        }


        String HP_Bonus = colorBonus(hp);
        String MP_Bonus = colorBonus(mp);
        String Damage_Bonus = colorBonus(atk);
        String Armor_Bonus = colorBonus(def);
        String Ini_Bonus = colorBonus(ini);

        String playerName = player.getName();
        String XP = "XP : "+stats.getXp()+"/"+stats.getXpRequired();
        String MP = "MP : "+stats.getManaPointActual()+"/"+stats.getManaPointTotal();
        String HP = "HP : "+stats.getLifePointActual()+"/"+stats.getLifePointTotal();
        String BTC = "BTC: "+stats.getMoneyCount();
        String FLR = "FLR: "+ gameState.getDungeon().getFloor();
        String LVL = "LVL: " + stats.getLevel();
        String ATK = "ATK: " + stats.getDamageTotal();
        String DEF = "DEF: " + stats.getArmorTotal();
        String INI = "INI: " + stats.getInitiativeTotal();
        String RGE = "RGE: " + stats.getRangeTotal();
        String KIL = "KIL: " + "0"; //TODO

        atk = bonusLength(atk) + ATK.length();
        def = bonusLength(def) + DEF.length();
        ini = bonusLength(ini) + INI.length();
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
        INI = INI + Ini_Bonus;

        // TODO add class

        return " _____________________________________________________________________\n" +
                "| " + playerName + " ".repeat(29 - name) + "| " +
                LVL + " ".repeat(37 - lvl) + "|\n" +
                "| " + XP + " ".repeat(29 - xp) + "| " +
                ATK + " ".repeat(37 - atk) + "|\n" +
                "| " + HP + " ".repeat(29 - hp) + "| " +
                DEF + " ".repeat(37 - def) + "|\n" +
                "| " + MP + " ".repeat(29 - mp) + "| " +
                INI + " ".repeat(37 - ini) + "|\n" +
                "| " + BTC + " ".repeat(29 - btc) + "| " +
                RGE + " ".repeat(37 - rge) + "|\n" +
                "| " + FLR + " ".repeat(29 - FLR.length()) + "| " +
                KIL + " ".repeat(37 - KIL.length()) + "|\n";
    }

    private int bonusEquipment(Boolean hp, Boolean mp, Boolean dmg, Boolean armor, Boolean ini, PlayerStats stats) {
        String res = "";
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
            else if (ini && equipment.getBonusInitiative() != 0) {
                natural = stats.getInitiativeNatural();
                total = stats.getInitiativeTotal();
                bonus = equipment.getBonusInitiative();
            }
            if (bonus == -1) {
                return 0;
            }
            else {
                if (equipment.isEquiped()) {
                    return -bonus;
                }
                else if (!equipment.isEquiped()) {
                    int current_bonus = containsEquipedType(hp, mp, dmg, armor, ini, equipment);

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

    public List<Stuff> getInventory() {
        return inventory;
    }

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
}
