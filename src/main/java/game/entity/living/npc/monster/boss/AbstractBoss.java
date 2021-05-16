package game.entity.living.npc.monster.boss;

import display.GridMap;
import game.entity.living.AbstractStats;
import game.entity.living.npc.monster.AbstractMonster;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import game.entity.living.npc.monster.monsterStrategy.StrategyUtils;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *AbstractClass for the bosses extending AbstractMonster implementing Boss.
 */
public abstract class AbstractBoss extends AbstractMonster implements Boss {

    List<BossPart> bossParts;
    boolean hasSpecial;
    private Direction previousDirection = null;
    private final List<Direction> bufferPath;
    private int turnCounter = 0;

    /**
     * Create an AbstractBoss (only called as a super for different bosses).
     * @param name name of the boss.
     * @param position position of the boss.
     * @param upColor color of top of the boss.
     * @param downColor color of the down of the boss.
     * @param strategy bossStrategy attributed to the bosses.
     * @param stats the boss main part stats.
     */
    public AbstractBoss(String name, Position position, Colors upColor, Colors downColor, Strategy strategy, AbstractStats stats) {
        super(position, name, upColor, downColor, null, strategy, stats);
        hasSpecial = true;
        bufferPath = new ArrayList<>();
        bufferInit();
    }

    /**
     * used to receive the list of boss part from the bosses and linking it to the mainPart of the boss.
     * @param bossParts List of bossPart, added in one go when a specified boss is created.
     */
    public void setBossParts(List<BossPart> bossParts) {
        this.bossParts = bossParts;
    }

    public boolean hasSpecial() {
        return hasSpecial;
    }

    public void setActedSpecial(boolean hasSpecial) {
        this.hasSpecial = hasSpecial;
    }

    public List<BossPart> getBossPartList() {
        return bossParts;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    /**
     * Handle the movement of the boss part that aren't the main part.
     * @param gridMap so we can access all the map, and move all part of the bosses at once.
     */
    public void updatePosBoss(GridMap gridMap) {
        if (turnCounter == 20) {
            bufferPath.clear();
            bufferInit();
        }
        List<Direction> impossibleDirection = new ArrayList<>();

        if (previousDirection != null) {
            impossibleDirection.add(previousDirection.oppositeDirection());
        }

        for (BossPart currentBossPart : getBossPartList()) {
            impossibleDirection.addAll(StrategyUtils.foundInaccessibleDirection(currentBossPart.getPosition(), gridMap));
        }
        List<Direction> copyOfBufferPath = List.copyOf(bufferPath);
        copyOfBufferPath = copyOfBufferPath.stream()
                .filter(direction -> !impossibleDirection.contains(direction))
                .collect(Collectors.toList());

        Direction direction;
        if (!copyOfBufferPath.isEmpty()) {
            Random gen = new Random();
            direction = copyOfBufferPath.get(gen.nextInt(copyOfBufferPath.size()));
            previousDirection = direction;
            bufferPath.add(previousDirection);
            turnCounter++;
        } else {
            direction = Direction.NONE;
        }
        switch (direction){
            //pas fou de faire quasi la même chose dans chaque case mais on peut pas faire une position négative temporaire et flemme de faire des couples
            case EAST:
                getPosition().updatePos(1,0);
                for (BossPart currentBossPart : getBossPartList()) {
                    currentBossPart.getPosition().updatePos(1,0);
                }
                break;
            case WEST:
                getPosition().updatePos(-1,0);
                for (BossPart currentBossPart : getBossPartList()) {
                    currentBossPart.getPosition().updatePos(-1,0);
                }
                break;
            case NORTH:
                getPosition().updatePos(0,-1);
                for (BossPart currentBossPart : getBossPartList()) {
                    currentBossPart.getPosition().updatePos(0,-1);
                }
                break;
            case SOUTH:
                getPosition().updatePos(0,1);
                for (BossPart currentBossPart : getBossPartList()) {
                    currentBossPart.getPosition().updatePos(0,1);
                }
                break;
            default:
                getPosition().updatePos(0,0);
                for (BossPart currentBossPart : getBossPartList()) {
                    currentBossPart.getPosition().updatePos(0,0);
                }
                break;
        }
    }

    private void bufferInit() {
        bufferPath.add(Direction.NORTH);
        bufferPath.add(Direction.EAST);
        bufferPath.add(Direction.WEST);
        bufferPath.add(Direction.SOUTH);
    }
}
