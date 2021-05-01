package entity.living.npc.merchants;

import com.diogonunes.jcolor.Attribute;
import entity.living.Inventory;
import entity.living.player.PlayerStats;
import gameElement.GameState;
import stuff.Stuff;
import stuff.equipment.EquipmentRarity;
import stuff.equipment.EquipmentType;
import stuff.equipment.equipments.Glove;
import stuff.equipment.equipments.Helmet;
import stuff.item.potions.Elixir;
import stuff.item.potions.PotionHealth;
import stuff.item.potions.XpBottle;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.ArrayList;
import java.util.List;

public class MerchantInventory extends Inventory {

    private List<Stuff> merchantInventory;

    public MerchantInventory() {
        super();
        merchantInventory = new ArrayList<>();
        merchantInventory.add(new Helmet(5, EquipmentRarity.E, EquipmentType.HELMET));
        merchantInventory.add(new Glove(4, EquipmentRarity.E, EquipmentType.HELMET));
        merchantInventory.add(new Helmet(3, EquipmentRarity.L, EquipmentType.HELMET));
        merchantInventory.add(new Helmet(2, EquipmentRarity.E, EquipmentType.HELMET));
//        merchantInventory.add(new PotionHealth());
//        merchantInventory.add(new Elixir());
//        merchantInventory.add(new XpBottle());
    }

    public boolean openSellingShop(GameState gameState) {
        inventory = gameState.getPlayer().getInventory().getInventory();
        selling = false;
        if (inventory.isEmpty()) {
            gameState.getDescriptor().updateDescriptor(String.format("%s have nothing to sell !", gameState.getPlayer().getName()));
            return false;
        } else {
            super.openInventory(true);
            return true;
        }
    }

    public void openBuyingSHop(GameState gameState) {
        selling = true;
        inventory = merchantInventory;
        super.openInventory(true);
    }

    public void setMerchantInventory(List<Stuff> merchantInventory) {
        this.merchantInventory = merchantInventory;
    }

    @Override
    public boolean useSelectedStuff(GameState gameState) {
        boolean sold = false;
        PlayerStats stats = gameState.getPlayer().getPlayerStats();
        if (selling) {
            int price = selectedStuff.getSellingPrice();
            if (stats.getMoneyCount() >= price) {
                stats.spendMoney(price);
                gameState.getPlayer().getInventory().addItem(selectedStuff);
                sold = true;
                gameState.getDescriptor().updateDescriptor(
                        String.format("%s spend %s BTC to buy %s !",
                                gameState.getPlayer().getName(),
                                colorize(String.valueOf(price), Colors.YELLOW.textApply()),
                                selectedStuff.getName()));
            } else {
                gameState.getDescriptor().updateDescriptor("%s don't have enough BTC !");
            }
        } else {
            int price = selectedStuff.getBuyingPrice();
            removeStuff(selectedStuff);
            stats.gainMoney(price);
            gameState.getDescriptor().updateDescriptor(
                        String.format("%s sold %s for %s BTC !",
                                gameState.getPlayer().getName(),
                                selectedStuff.getName(),
                                colorize(String.valueOf(price), Colors.YELLOW.textApply())));
        }
        return sold;
    }

    @Override
    public String toStringInventory(GameState gameState) {
        StringBuilder sb = new StringBuilder();
        String headSelling =
                colorize(String.format("Hello %s ! There might be something you want to buy...\n", gameState.getPlayer().getName()) +
                        "################################ | Q, D : Switch categories.\n" +
                        "#    MERCHANT SHOP INVENTORY   # | Z, S : Switch selected stuffs. \n" +
                        "################################ | E : Buy | ESC : Quit shop\n", Attribute.BOLD(), Colors.MAGENTA.textApply());
        String headBuying =
                colorize(String.format("Hello %s ! Show me your stuffs you want to sell...\n", gameState.getPlayer().getName()) +
                        "################################ | Q, D : Switch categories.\n" +
                        "#       PLAYER INVENTORY       # | Z, S : Switch selected stuffs. \n" +
                        "################################ | E : Sell | ESC : Quit shop\n", Attribute.BOLD(), Colors.CYAN.textApply());
        String stats = toStringStats(gameState);
        String inventoryList = toStringInventoryList();
        if (selling) {
            sb.append(headSelling);
        } else {
            sb.append(headBuying);
        }
        sb.append(stats).append(inventoryList);

        return sb.toString();
    }
}
