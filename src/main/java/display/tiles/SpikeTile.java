package display.tiles;

import display.AbstractTile;
import gameElement.GameState;

public class SpikeTile extends AbstractTile {

    public SpikeTile() {
        super("^^^", true,false);
    }

    @Override
    public void doAction(GameState gameState) {
        int damage = gameState.getDungeon().getFloor()*2;
        gameState.getPlayer().getPlayerStats().sufferDamage(damage);
        gameState.getDescriptor().updateDescriptor(String.format("You walked on spike, you loose %d HP", damage));
    }
}
