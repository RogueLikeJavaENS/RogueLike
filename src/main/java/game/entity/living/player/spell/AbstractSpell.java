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

    public AbstractSpell(String name, double damageMult, int damage, Range range, int manaCost, boolean damaging, SpecialEffect effect) {
        this.name = name;
        this.damageMult = damageMult;
        this.damage = damage;
        this.range = range;
        this.manaCost = manaCost;
        this.damaging = damaging;
        this.effect = effect;
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
            }
            else {
                doSpecialEffect(gameState);
            }
            return true;
        } else {
            descriptor.updateDescriptor("Not enough PM !");
            return false;
        }
    }


    public void doSpecialEffect(GameState gameState) {
        effect.doEffect(gameState);
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
    public void setTopLeftCorner(Position entityPos, Direction direction) {
        range.setTopLeftCorner(entityPos);
    }

    public void setBottomRightCorner(Position entitiyPos, Direction direction) {
        range.setBottomRightCorner(entitiyPos);
    }
}
