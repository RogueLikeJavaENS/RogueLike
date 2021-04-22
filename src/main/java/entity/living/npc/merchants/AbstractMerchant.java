package entity.living.npc.merchants;

import entity.living.AbstractStats;
import entity.living.npc.NPC;
import items.Item;
import utils.Colors;
import utils.Position;
import java.util.HashMap;

public abstract class AbstractMerchant extends NPC implements Merchant {
    HashMap<Item, Integer> shop;

    public AbstractMerchant(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
    }

    protected void setShop(HashMap<Item, Integer> shop) {
        this.shop = shop;
    }

    @Override
    public HashMap<Item, Integer> getShops() {
        return null;
    }
}
