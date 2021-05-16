package game.entity.object.traps;

import display.GridMap;
import game.elements.GameState;
import game.entity.Entity;
import game.entity.living.LivingEntity;
import game.entity.living.npc.monster.Monster;
import game.entity.object.ObjectEntity;
import utils.Colors;
import utils.Position;

import java.util.List;
import java.util.Random;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * Trap a Ranger can place to deal some damages to the Monsters they're fighting.
 */
public class RangerTrap extends ObjectEntity {

    public RangerTrap(Position position, Colors color, boolean isPlayerAccessible, boolean isNPCAccessible) {
        super(position, color,color, isPlayerAccessible, isNPCAccessible);
        setSprites("   ", colorize("\\w/", Colors.YELLOW_GREEN.textApply()));
    }

    @Override
    public void doAction(GameState gameState) {
        gameState.getMusicStuff().playStabsFX();
        Random gen = new Random();
        int damage = gameState.getPlayer().getPlayerStats().getDamageTotal() + gen.nextInt(20);
        Entity entity = gameState.getGridMap().getEntitiesAt(this.getPosition().getAbs(), this.getPosition().getOrd()).get(0);
        if (entity.isMonster() || entity.isPlayer()) {
            LivingEntity livingEntity = (LivingEntity) entity;
            damage = livingEntity.getStats().sufferDamage(damage);
            gameState.getDescriptor().updateDescriptor(String.format("%s is stuck in your trap and lost %d HP.",livingEntity.getName(),damage));
            GridMap gridMap = gameState.getGridMap();
            List<Entity> entities = gridMap.getEntitiesAt(getPosition().getAbs(), getPosition().getOrd());
            for (Entity e : entities) {
                if (e.isMonster()) {
                    gameState.isMonsterAlive((Monster) e);
                }
            }
        }
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
