package game.entity.living.inventory;

import game.elements.GameState;
import game.stuff.Stuff;

import java.util.List;

public class PlayerInventory extends AbstractInventory {
    public PlayerInventory() {
        super();
    }

    public List<Stuff> getEquiped() {
        return equipped;
    }

    @Override
    public void openSellingShop(GameState gameState) {
        // nothing
    }

    @Override
    public void openBuyingSHop(GameState gameState) {
        // nothing
    }
}
