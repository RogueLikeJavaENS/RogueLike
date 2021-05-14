package game.entity.living.inventory;

import com.diogonunes.jcolor.Attribute;
import game.entity.living.player.PlayerStats;
import game.elements.GameRule;
import game.elements.GameState;
import game.stuff.Stuff;
import game.stuff.equipment.Equipment;
import game.stuff.equipment.EquipmentFactory;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.item.Item;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory of the merchant. Overriding some methods as the adding Items.
 * Using an items is selling or buying and not use and equip.
 */
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

    /**
     * Opens the inventory with the player inventory. The player can sells his stuff.
     * @param gameState to get the player inventory.
     */
    public void openSellingShop(GameState gameState) {
        inventory = gameState.getPlayer().getInventory().getInventory();
        selling = false;
        super.openInventory(gameState.getPlayer().getPlayerStats().getLevel());
    }

    /**
     * Opens the buying shop. The inventory is set with the merchant inventory.
     * The player can buy items to the merchant.
     * The equipments is upgrades according to the player level
     * @param gameState to get the player level.
     */
    public void openBuyingSHop(GameState gameState) {
        selling = true;
        upgradeEquipmentLevel(gameState);
        inventory = merchantInventory;
        super.openInventory(gameState.getPlayer().getPlayerStats().getLevel());
    }

    /**
     * Selling or buying stuffs according to the selling boolean
     * @param gameState to update the player or action when using an item.
     * @return if the selected stuff has been sold or bought.
     */
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
                price = GameRule.getItemPrice(stats.getLevel(), item.getType());
            }
            // if the selected game.stuff is an Equipment.
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
                price = (int) (GameRule.getItemPrice(stats.getLevel(), item.getType()) * 0.60);
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

    /**
     * Changing the head of the inventory according if the merchant is selling or buying stuffs.
     * @param gameState gameState used to check the floor and things like that.
     * @return the string of the inventory.
     */
    @Override
    public String toStringInventory(GameState gameState) {
        StringBuilder sb = new StringBuilder();
        String headSelling =
                String.format("Hello %s ! There might be something you want to buy...\n", gameState.getPlayer().getName()) +
                        "################################ | Q, D : Switch categories.\n" +
                        "#    MERCHANT SHOP INVENTORY   # | Z, S : Switch selected stuffs. \n" +
                        "################################ | E : Buy | ESC : Quit shop\n";
        String headBuying =
               String.format("Hello %s ! Show me your stuffs you want to sell...\n", gameState.getPlayer().getName()) +
                        "################################ | Q, D : Switch categories.\n" +
                        "#       PLAYER INVENTORY       # | Z, S : Switch selected stuffs. \n" +
                        "################################ | E : Sell | ESC : Quit shop\n";
        String inventoryList = toStringInventoryList();
        if (selling) {
            sb.append(headSelling);
        } else {
            sb.append(headBuying);
        }
        sb.append(toStringStatsSelectedStuff(gameState));
        sb.append(inventoryList);
        equipped = gameState.getPlayer().getInventory().getEquiped();
        sb.append(toStringEquipped());

        return sb.toString();
    }

    private String toStringStatsSelectedStuff(GameState gameState) {
        StringBuilder sb = new StringBuilder();
        if (indexOfSelectedStuff != -1) {
            sb.append(" _____________________________________________________________________\n");
            if (onEquipments) {
                int lineCount = 8;
                Equipment e = (Equipment) selectedStuff;
                lineCount += e.getName().length() + String.valueOf(e.getLevel()).length() + e.getRarity().getRarity().length();
                sb.append("| A ").append(colorize(e.getRarity().getRarity(), EquipmentRarity.getColor(e.getRarity()).textApply()))
                        .append(" ")
                        .append(e.getName())
                        .append(" ");
                sb.append(" LVL ").append(e.getLevel()).append(" ".repeat(67-lineCount)).append("|\n");
                lineCount = 6;
                sb.append("| Stats: ");
                int hpBonus = e.getBonusLife();
                int mpBonus = e.getBonusMana();
                int agiBonus = e.getBonusAgility();
                int defBonus = e.getBonusArmor();
                int atkBonus = e.getBonusDamage();
                if (hpBonus > 0) {
                    sb.append("+").append(hpBonus).append(colorize(" HP ", Colors.RED.textApply()));
                    lineCount += 5+ String.valueOf(hpBonus).length();
                }
                if (mpBonus > 0) {
                    sb.append("+").append(mpBonus).append(colorize(" MP ", Colors.BLUE.textApply()));
                    lineCount += 5+ String.valueOf(mpBonus).length();
                }
                if (atkBonus > 0) {
                    sb.append("+").append(atkBonus).append(colorize(" ATK ", Attribute.BOLD()));
                    lineCount += 6+ String.valueOf(atkBonus).length();
                }
                if (defBonus > 0) {
                    sb.append("+").append(defBonus).append(colorize(" DEF ", Attribute.BOLD()));
                    lineCount += 6+ String.valueOf(defBonus).length();
                }
                if (agiBonus > 0) {
                    sb.append("+").append(agiBonus).append(colorize(" AGI ", Attribute.BOLD()));
                    lineCount += 6+ String.valueOf(agiBonus).length();
                }
                sb.append(" ".repeat(67-lineCount)).append("|\n");
                int cost;
                if (selling) {
                    cost = e.getSellingPrice();
                } else {
                    cost = e.getBuyingPrice();
                }
                lineCount = 8 + String.valueOf(cost).length();
                sb.append("| Cost ").append(colorize(String.valueOf(cost), Colors.YELLOW.textApply(), Attribute.BOLD()))
                        .append(" BTC").append(" ".repeat(67-lineCount)).append("|\n");


            } else {
                Item i = (Item) selectedStuff;
                int lineCount = 4 + i.getName().length() + i.getDescription().length();
                sb.append("| A ").append(i.getName()).append(" : ").append(i.getDescription())
                        .append(" ".repeat(67-lineCount)).append("|\n");

                int cost;
                if (selling) {
                    cost = i.getSellingPrice();
                } else {
                    cost = i.getBuyingPrice();
                }
                lineCount = 8 + String.valueOf(cost).length();
                sb.append("| Cost ").append(colorize(String.valueOf(cost), Colors.YELLOW.textApply(), Attribute.BOLD()))
                        .append(" BTC").append(" ".repeat(67-lineCount)).append("|\n");
            }
            int playerMoney = gameState.getPlayer().getPlayerStats().getMoneyCount();
            int lineCount = 19 + String.valueOf(playerMoney).length();;

            sb.append("| Player wealth : ").append(colorize(String.valueOf(playerMoney), Colors.YELLOW.textApply(), Attribute.BOLD()))
                    .append(" BTC").append(" ".repeat(67-lineCount)).append("|\n");
            return sb.toString();
        }
        return "";
    }

    /**
     * Iterates on the merchantInventory and upgrades the equipments.
     * @param gameState to get the player classes and level.
     */
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
}
