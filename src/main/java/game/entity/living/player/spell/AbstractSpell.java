package game.entity.living.player.spell;

import display.Descriptor;
import display.GridMap;
import game.elements.GameState;
import game.entity.Entity;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.boss.BossPart;
import game.entity.living.player.Player;
import utils.Colors;
import utils.Position;
import utils.Direction;

import java.util.GregorianCalendar;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class contains everything that is common to every spell.
 */
public abstract class AbstractSpell implements Spell {
    protected String name;
    protected int damage;
    protected double damageMult;
    protected Range range;
    protected int manaCost;
    private boolean damaging;
    private SpecialEffect effect;
    private int avalaibleRange;
    private int level;

    public AbstractSpell(String name, boolean damaging, int avalaibleRange, int level, SpecialEffect effect) {
        this.name = name;
        this.damageMult = 0;
        this.damage = 0;
        this.manaCost = 0;
        this.damaging = damaging;
        this.effect = effect;
        this.avalaibleRange = avalaibleRange;
        this.level = level;
        range = new Range();
    }

    public boolean useSpell(GameState gameState) {
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        Descriptor descriptor = gameState.getDescriptor();

        if (player.getPlayerStats().consumeMp(getManaCost())) {
            if (isDamaging()) {
                boolean hitBoss = false;
                List<Position> rangeList = gridMap.getRangeList();
                for (Position pos : rangeList) {
                    List<Entity> entityList = gridMap.getEntitiesAt(pos.getAbs(), pos.getOrd());
                    for (Entity currentEntity : entityList) {
                        if (pos.equals(currentEntity.getPosition())) {
                            if(currentEntity.isMonster()) {
                                Monster monster = (Monster) currentEntity;
                                if (!monster.isBoss()) {
                                    int damage = getDamage();
                                    double mult = getDamageMult();
                                    int damages = (int) (damage + mult * player.getPlayerStats().getDamageTotal());
                                    damages = monster.getMonsterStats().sufferDamage(damages);
                                    descriptor.updateDescriptor(String.format("%s used %s for %s mana and inflicted %s damages to the %s !",
                                            player.getName(),
                                            this,
                                            colorize(Integer.toString(getManaCost()), Colors.BLUE.textApply()),
                                            colorize(Integer.toString(damages), Colors.ORANGE.textApply()),
                                            monster.getName()));
                                    gameState.isMonsterAlive(monster);
                                }
                            }
                            else if (currentEntity.isDestroyable()) {
                                gridMap.update(currentEntity, false);
                                descriptor.updateDescriptor(String.format("%s used %s for %s mana and destroyed a trap!",
                                        player.getName(),
                                        this,
                                        colorize(Integer.toString(getManaCost()), Colors.BLUE.textApply())));
                            }
                            else if (!hitBoss && currentEntity.isBossPart()) {
                                BossPart bossPart = (BossPart) currentEntity;
                                int damage = getDamage();
                                double mult = getDamageMult();
                                int damages = (int) (damage + mult * player.getPlayerStats().getDamageTotal());
                                damages = bossPart.dealDamageBoss(damages);
                                descriptor.updateDescriptor(String.format("%s used %s for %s mana and inflicted %s damages to the %s !",
                                        player.getName(),
                                        this,
                                        colorize(Integer.toString(getManaCost()), Colors.BLUE.textApply()),
                                        colorize(Integer.toString(damages), Colors.ORANGE.textApply()),
                                        bossPart.getMyBoss().getName()));
                                gameState.isMonsterAlive(bossPart.getMyBoss());
                                hitBoss = true;
                            }
                        }
                    }
                }
            }
            boolean didItWork = doSpecialEffect(gameState);
            if (!didItWork) {
                descriptor.updateDescriptor("You cannot do that here...");
                player.getPlayerStats().recoverMp(getManaCost());
            }
            return didItWork;
        } else {
            descriptor.updateDescriptor("Not enough PM !");
            return false;
        }
    }

    /**
     * Execute the Spell's particular effect if it has one, and return wether it worked or not.
     * If the Spell doesn't have any particular effect, it always returns true.
     *
     * @param gameState GameState needed to correctly apply the effect to the game
     * @return True if the effect worked or if the spell doesn't have any special effect, false otherwise.
     */
    public boolean doSpecialEffect(GameState gameState) {
        if (effect != null) {
            return effect.doEffect(gameState);
        }
        return true;
    }

    public boolean isMovement() {
        return false;
    }

    /**
     * Returns wether the Spell can deal damamges or not.
     *
     * @return True if the Spell the Spell can deal damamges, false otherwise.
     */
    public boolean isDamaging() {
        return damaging;
    }

    public int getDamage() {
        return (damage);
    }

    public String toString() {
        return name;
    }

    public double getDamageMult() {
        return damageMult;
    }

    public Range getRange() {
        return range;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getLevel() {
        return level;
    }

    public void setDamageMult(double damageMult) {
        this.damageMult = damageMult;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setRange(Position entityPos, Direction direction) {
        setBottomRightCorner(entityPos, direction);
        setTopLeftCorner(entityPos, direction);
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean isZoning() {
        return false;
    }

    /**
     * Sets the top left corner of the range of the Spell.
     *
     * @param entityPos Position of the entity, used as the referential to place the top left corner.
     * @param direction Direction of the entity to place the top left corner accordingly.
     */
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        int avalaibleRangeCopy = avalaibleRange;
        switch (direction) {
            case NORTH:
                if (entityPos.getOrd() < avalaibleRangeCopy) {
                    avalaibleRangeCopy = entityPos.getOrd();
                }
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd() - avalaibleRangeCopy
                ));
                break;
            case WEST:
                if (entityPos.getAbs() < avalaibleRangeCopy) {
                    avalaibleRangeCopy = entityPos.getAbs();
                }
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs() - avalaibleRangeCopy, entityPos.getOrd()
                ));
                break;
            default:
                range.setTopLeftCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd()
                ));
        }
    }

    /**
     * Sets the bottom right corner of the range of the Spell.
     *
     * @param entityPos Position of the entity, used as the referential to place the bottom right corner.
     * @param direction Direction of the entity to place the bottom right corner accordingly.
     */
    public void setBottomRightCorner(Position entityPos, Direction direction) {
        switch (direction) {
            case EAST:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs() + avalaibleRange, entityPos.getOrd()
                ));
                break;
            case SOUTH:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd() + avalaibleRange
                ));
                break;
            default:
                range.setBottomRightCorner(new Position(
                        entityPos.getAbs(), entityPos.getOrd()
                ));
        }
    }


}
