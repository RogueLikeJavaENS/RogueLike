package game.entity.living.npc.merchants;

import game.entity.living.AbstractStats;
import game.entity.living.npc.NPC;
import game.stuff.Stuff;
import utils.Colors;
import utils.Position;
import java.util.HashMap;

public abstract class AbstractMerchant extends NPC implements Merchant {
    HashMap<Stuff, Integer> shop;

    public AbstractMerchant(Position position, String name, Colors color, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
    }

    protected void setShop(HashMap<Stuff, Integer> shop) {
        this.shop = shop;
    }

    @Override
    public HashMap<Stuff, Integer> getShops() {
        return null;
    }

}
