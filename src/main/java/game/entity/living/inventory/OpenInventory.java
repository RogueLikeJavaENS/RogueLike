package game.entity.living.inventory;

import display.RendererUI;
import game.element.GameState;
import utils.State;

import java.awt.event.KeyEvent;

public class OpenInventory {
    private final GameState gs;
    private Inventory inventory;
    private boolean acted;
    private boolean exitInventory;

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

    public boolean hasActed() {
        return acted;
    }

    private void displayInventory() {
        System.out.println(inventory.toStringInventory(gs));
        System.out.println(gs.getDescriptor());
    }

}
