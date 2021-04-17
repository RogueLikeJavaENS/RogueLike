package entity.living.npc.merchants;

import entity.living.AbstractStats;
import entity.living.npc.NPC;
import entity.object.potion.Potion;
import utils.Colors;
import utils.Position;
import java.util.HashMap;

public abstract class AbstractMerchant extends NPC implements Merchant {
    HashMap<Potion, Integer> shop;

    public AbstractMerchant(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
    }

    protected void setShop(HashMap<Potion, Integer> shop) {
        this.shop = shop;
    }

    @Override
    public HashMap<Potion, Integer> getShops() {
        return null;
    }
}
