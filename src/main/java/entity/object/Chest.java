package entity.object;

import gameElement.GameState;
import stuff.item.Item;
import utils.Colors;
import utils.Position;
import java.util.ArrayList;
import java.util.List;

public class Chest extends ObjectEntity {

    private List<Item> items;
    private final boolean hasKey;
    private boolean opened;

    public Chest(Position position, boolean hasKey) {
        super(position, Colors.BROWN, false);
        items = new ArrayList<>();
        this.hasKey = hasKey;
        this.opened = false;
        setSprites("   ", "[¤]", Colors.BROWN);
        // items = GameRule.getItemsInChest(int floor)
    }

    @Override
    public void doInteraction(GameState gameState) {
        if (!opened) {
            opened = true;
            // give items here
            if (hasKey) {
                System.out.println("add key !");
            }
            setSprites("/'\\", "[¤]", Colors.BROWN);
            System.out.println("add + items !");
        } else {
            // already opened !
        }
    }
}
