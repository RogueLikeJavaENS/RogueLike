package game.entity.living.npc.monster.boss;


import game.entity.living.AbstractStats;
import game.entity.living.LivingEntity;
import utils.Colors;
import utils.Position;

public class BossPart extends LivingEntity {
    private final Boss myBoss;

    public BossPart(Boss boss, Position position, String name, Colors color, AbstractStats stats, String spritesUp, String spritesDown) throws IllegalArgumentException {
        super(position, name, color, stats, true);
        setSprites(spritesUp, spritesDown);
        myBoss = boss;
    }

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