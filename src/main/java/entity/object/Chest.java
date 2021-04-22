package entity.object;

import gameElement.GameState;
import items.Item;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Chest extends ObjectEntity {
    private List<Item> items;
    private int nbKey;

    public Chest(Position position) {
        super(position, Colors.BROWN, false);
        nbKey = 1;
        items = new ArrayList<>();
        // items = GameRule.getItemsInChest(int floor)
    }

    @Override
    public void doAction(GameState gameState) {
        super.doAction(gameState);
    }

    @Override
    public void doInteraction(GameState gameState) {
        // give items here
        System.out.println("+ items !");
    }
}
