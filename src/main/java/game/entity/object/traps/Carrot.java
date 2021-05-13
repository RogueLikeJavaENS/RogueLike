package game.entity.object.traps;

import game.entity.living.player.Player;
import game.entity.object.ObjectEntity;
import game.elements.GameState;
import utils.Colors;
import utils.Position;

import java.util.Random;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Carrot extends ObjectEntity {
    public Carrot(Position position, Colors color, boolean isPlayerAccessible, boolean isNPCAccessible) {
        super(position, color, isPlayerAccessible, isNPCAccessible);
        setSprites("   ", colorize("<=", Colors.ORANGE.textApply()) + colorize("#", Colors.GREEN.textApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playStabsFX();
        Random gen = new Random();
        int damage = 50 + gen.nextInt(70);
        Player player = gameState.getPlayer();
        damage = player.getPlayerStats().sufferDamage(damage);
        gameState.getDescriptor().updateDescriptor(String.format("%s walked on the Rabbit's carrot and lost %d HP.",player.getName(),damage));
        gameState.getGridMap().update(this, false);
    }

    @Override
    public void doInteraction(GameState gameState) {
        gameState.getGridMap().update(this, false);
        gameState.getDescriptor().updateDescriptor("You cleaned up the carrot.");
    }

    @Override
    public boolean isDestroyable() {
        return true;
    }
}
