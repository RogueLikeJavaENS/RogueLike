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

public abstract class AbstractSpell implements Spell {
    protected String name;
    protected int damage;
    protected double damageMult;
    protected Range range;
    protected int manaCost;
    private boolean damaging;
    private SpecialEffect effect;
    private int avalaibleRange;

    public AbstractSpell(String name, double damageMult, int damage, Range range, int manaCost, boolean damaging, int avalaibleRange, SpecialEffect effect) {
        this.name = name;
        this.damageMult = damageMult;
        this.damage = damage;
        this.range = range;
        this.manaCost = manaCost;
        this.damaging = damaging;
        this.effect = effect;
        this.avalaibleRange = avalaibleRange;
    }

    public boolean useSpell(GameState gameState) {
        Player player = gameState.getPlayer();
        GridMap gridMap = gameState.getGridMap();
        Descriptor descriptor = gameState.getDescriptor();

        if (player.getPlayerStats().consumeMp(getManaCost())) {
            if (isDamaging()) {
                boolean hitBoss = false;
                for (Position pos : gridMap.getRangeList()) {
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
                return doSpecialEffect(gameState);
            }
            else {
                return doSpecialEffect(gameState);
            }
            return true;
        } else {
            descriptor.updateDescriptor("Not enough PM !");
            return false;
        }
    }


    public boolean doSpecialEffect(GameState gameState) {
        if (effect != null) {
            return effect.doEffect(gameState);
        }
        return true;
    }

    public boolean isDamaging() {
        return damaging;
    }

    @Override
    public int getDamage() {
        return (damage);
    }

    @Override
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

    public void setRange(Position entityPos, Direction direction) {
        setBottomRightCorner(entityPos, direction);
        setTopLeftCorner(entityPos, direction);
    }

    @Override
    public boolean isZoning() {
        return false;
    }

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
