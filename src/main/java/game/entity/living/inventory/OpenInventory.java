package game.entity.living.inventory;

import display.RendererUI;
import game.elements.GameState;
import utils.State;

import java.awt.event.KeyEvent;

/**
 * Used to get the inputs from the player to navigate in the inventory.
 */
public class OpenInventory {
    private final GameState gs;
    private Inventory inventory;
    private boolean acted;
    private boolean exitInventory;

    /**
     * Constructor use to open the Player inventory.
     * @param gs the GameState used to get the level og the player.
     * @param inventory the inventory to open.
     */
    public OpenInventory(GameState gs, Inventory inventory) {
        this.gs = gs;
        this.inventory = inventory;
        exitInventory = false;
        acted = false;
        inventory.openInventory(gs.getPlayer().getPlayerStats().getLevel());
        try {
            inventoryLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor used to open the inventory on the merchant if the player is buying stuffs. Or on the player inventory
     * if the player is selling stuffs.
     * @param gs GameState used to get the player inventory.
     * @param inventory the merchant inventory.
     * @param selling if the player is buying or selling.
     */
    public OpenInventory(GameState gs, Inventory inventory, boolean selling) {
        this.gs = gs;
        this.inventory = inventory;
        exitInventory = false;
        acted = false;
        if (!selling) {
            if (gs.getPlayer().getInventory().getInventory().isEmpty()) {
                exitInventory = true;
                gs.getDescriptor().updateDescriptor(
                        String.format("%s have nothing to sell !", gs.getPlayer().getName()));
            } else {
                inventory.openSellingShop(gs);
            }
        }
        else {
            inventory.openBuyingSHop(gs);
        }
        try {
            inventoryLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to know if the player consumes his turn during fight.
     * @return a boolean if the player acted ord not.
     */
    public boolean hasActed() {
        return acted;
    }

    /**
     * The loop of the inventory.
     * @throws InterruptedException if the scanPanel is closed
     */
    private void inventoryLoop() throws InterruptedException {
        boolean display = true;
        while(!exitInventory) {
            if (display) {
                RendererUI.clearConsole();
                displayInventory();
            }

            gs.getScanPanel().reset();
            int keyCode = 0;
            while(keyCode == 0) {
                keyCode = gs.getScanPanel().getKeyPressed();
                Thread.sleep(1);  // Without that, Java deletes the loop
            }
            switch (keyCode) {
                case KeyEvent.VK_Z:
                case KeyEvent.VK_UP:
                    display = inventory.previousSelectedStuff();
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    display = inventory.nextSelectedStuff();
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_Q:
                case KeyEvent.VK_LEFT:
                    inventory.switchCategory();
                    display = true;
                    break;
                case KeyEvent.VK_E:
                case KeyEvent.VK_ENTER:
                    acted = inventory.useSelectedStuff(gs);
                    if (acted) {
                        if (gs.isThereMonstersInventory()) {
                            exitInventory = true;
                        } else {
                            acted = false;
                        }
                    }
                    display = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gs.getPlayer().getInventory().closeInventory();
                    gs.setState(State.NORMAL);
                    gs.isThereMonstersInventory();
                    exitInventory = true;
                    break;
                default:
                    display = false;
                    break;
            }
        }
    }

    /**
     * Display the inventory in the loop.
     */
    private void displayInventory() {
        System.out.println(inventory.toStringInventory(gs));
        System.out.println(gs.getDescriptor());
    }

}
