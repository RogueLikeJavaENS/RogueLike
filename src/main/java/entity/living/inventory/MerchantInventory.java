package entity.living.inventory;

import com.diogonunes.jcolor.Attribute;
import entity.living.player.PlayerStats;
import gameElement.GameRule;
import gameElement.GameState;
import stuff.Stuff;
import stuff.equipment.Equipment;
import stuff.equipment.EquipmentFactory;
import stuff.item.Item;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.ArrayList;
import java.util.List;

public class MerchantInventory extends AbstractInventory {

    private List<Stuff> merchantInventory;

    public MerchantInventory() {
        super();
        merchantInventory = new ArrayList<>();
    }

    @Override
    public void addItem(Stuff stuff) {
        merchantInventory.add(stuff);
    }

    public void openSellingShop(GameState gameState) {
        inventory = gameState.getPlayer().getInventory().getInventory();
        selling = false;
        super.openInventory(gameState.getPlayer().getPlayerStats().getLevel());
    }

    public void openBuyingSHop(GameState gameState) {
        selling = true;
        upgradeEquipmentLevel(gameState);
        inventory = merchantInventory;
        super.openInventory(gameState.getPlayer().getPlayerStats().getLevel());
    }

    private void upgradeEquipmentLevel(GameState gameState) {
        List<Stuff> newInventory = new ArrayList<>();
        EquipmentFactory factory = new EquipmentFactory(gameState.getPlayer().getClasse());
        for (Stuff stuff : merchantInventory) {
            if (stuff.isEquipable()) {
                Equipment equipment = (Equipment) stuff;
                newInventory.add(factory.getCopyOfEquipment(equipment, gameState.getPlayer().getPlayerStats().getLevel()));
            }
            else {
                newInventory.add(stuff);
            }
        }
        this.merchantInventory=newInventory;
    }

    @Override
    public boolean useSelectedStuff(GameState gameState) {
        if (indexOfSelectedStuff == -1) {
            return false;
        }
        PlayerStats stats = gameState.getPlayer().getPlayerStats();
        // If the merchant is selling to the player
        if (selling) {
            int price;
            // if the selectedStuff is an Item.
            if (selectedStuff.isUsable()) {
                Item item = (Item) selectedStuff;
                GameRule gameRule = new GameRule();
                price = gameRule.getPotionPrice(stats.getLevel(), item.getType());
            }
            // if the selected stuff is an Equipment.
            else {
                price = selectedStuff.getSellingPrice();
            }
            if (stats.getMoneyCount() >= price) {
                gameState.getPlayer().getInventory().addItem(selectedStuff);
                removeStuff(selectedStuff);
                stats.spendMoney(price);
                gameState.getDescriptor().updateDescriptor(
                        String.format("%s spend %s BTC to buy %s !",
                                gameState.getPlayer().getName(),
                                colorize(String.valueOf(price), Colors.YELLOW.textApply()),
                                selectedStuff.getName()));
            } else {
                gameState.getDescriptor().updateDescriptor(String.format("%s don't have enough BTC !",gameState.getPlayer().getName()));
            }
        } else {
            int price;
            if (selectedStuff.isUsable()) {
                Item item = (Item) selectedStuff;
                GameRule gameRule = new GameRule();
                price = (int) (gameRule.getPotionPrice(stats.getLevel(), item.getType()) * 0.60);
            } else {
                Equipment equipment = (Equipment) selectedStuff;
                if (equipment.isEquiped()) {
                    gameState.getDescriptor().updateDescriptor(
                            String.format("%s can't sell %s, it's currently equipped.",
                                    gameState.getPlayer().getName(),
                                    selectedStuff.getName()));
                    placeSelectedStuff(false);
                    return false;
                }
                price = selectedStuff.getBuyingPrice();
            }
            removeStuff(selectedStuff);
            stats.gainMoney(price);
            gameState.getDescriptor().updateDescriptor(
                        String.format("%s sold %s for %s BTC !",
                                gameState.getPlayer().getName(),
                                selectedStuff.getName(),
                                colorize(String.valueOf(price), Colors.YELLOW.textApply())));
        }
        placeSelectedStuff(false);
        return true;
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
