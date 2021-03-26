package entity.object;

import entity.living.Player;
import gameElement.GameState;
import utils.Position;

public class Coins extends ObjectEntity {

    public int getValue() {
        return value;
    }

    private final int value;

    public Coins(Position position) {
        super(position, true);
        this.value = 1;
    }

    @Override
    public void doAction(GameState gameState) {
        Player player = gameState.getPlayer();
        player.setMoneyCount(player.getMoneyCount()+value);
    }
}
