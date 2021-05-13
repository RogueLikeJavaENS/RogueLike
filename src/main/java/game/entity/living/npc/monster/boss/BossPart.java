package game.entity.living.npc.monster.boss;


import game.entity.living.AbstractStats;
import game.entity.living.LivingEntity;
import utils.Colors;
import utils.Position;


/**
 * A Boss Part represent an entity under control of the Boss entity.
 * It shares the same Boss, life and strategy. Each of the boss part has a specific sprite.
 */
public class BossPart extends LivingEntity {
    private final Boss myBoss;

    public BossPart(Boss boss, Position position, String name, Colors color, AbstractStats stats, String spritesUp, String spritesDown) throws IllegalArgumentException {
        super(position, name, color, stats, true);
        setSprites(spritesUp, spritesDown);
        myBoss = boss;
    }

    /**
     * Deals damage to the boss when a part of the boss is hit.
     * @param damage the damage to deal.
     * @return the dealt damages.
     */
    public int dealDamageBoss(int damage) {
        damage = myBoss.getMonsterStats().sufferDamage(damage);
        return damage;
    }

    public Boss getMyBoss() {
        return myBoss;
    }

    @Override
    public boolean isBossPart() {
        return true;
    }
}
