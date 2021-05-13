package game.entity.object.traps;

import game.elements.GameState;
import game.entity.living.LivingEntity;
import game.entity.object.ObjectEntity;
import utils.Colors;
import utils.Position;

import java.util.Random;

import static com.diogonunes.jcolor.Ansi.colorize;

public class RangerTrap extends ObjectEntity {

    public RangerTrap(Position position, Colors color, boolean isPlayerAccessible, boolean isNPCAccessible) {
        super(position, color, isPlayerAccessible, isNPCAccessible);
        setSprites("   ", colorize("\\w/", Colors.YELLOW_GREEN.textApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playStabsFX();
        Random gen = new Random();
        int damage = gameState.getPlayer().getPlayerStats().getDamageTotal() + gen.nextInt(20);
        LivingEntity entity = (LivingEntity) gameState.getGridMap().getEntitiesAt(this.getPosition().getAbs(), this.getPosition().getOrd()).get(0);
        damage = entity.getStats().sufferDamage(damage);
        gameState.getDescriptor().updateDescriptor(String.format("%s is stuck in your trap and lost %d HP.",entity.getName(),damage));
    }

    @Override
    public void doInteraction(GameState gameState) {
        gameState.getGridMap().update(this, false);
        gameState.getDescriptor().updateDescriptor("You cleaned up the trap.");
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }

    @Override
    public boolean isTrap() {
        return true;
    }
}
