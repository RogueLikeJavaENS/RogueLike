package entity.living.npc.monster.boss.bosses;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import entity.living.AbstractStats;
import entity.living.npc.NPCStats;
import entity.living.npc.monster.Monster;
import entity.living.npc.monster.boss.AbstractBoss;
import entity.living.npc.monster.boss.BossPart;
import entity.living.player.Player;
import entity.object.Carrot;
import gameElement.GameState;
import monsterStrategy.Strategy;
import monsterStrategy.StrategyUtils;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.awt.dnd.DragGestureEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class KillerRabbit extends AbstractBoss {

    private Direction previousDirection = null;
    private List<Direction> bufferPath;
    private int turnCounter = 0;

    public KillerRabbit(String name, Position position, Colors color, Strategy strategy, AbstractStats stats) {
        super(name, position, color, strategy, stats);
        List<BossPart> listBossParts = new ArrayList<>();
        bufferPath = new ArrayList<>();
        bufferPath.add(Direction.NORTH);
        bufferPath.add(Direction.EAST);
        bufferPath.add(Direction.SOUTH);
        bufferPath.add(Direction.WEST);

        BossPart partUpRight = new BossPart(
                this,
                new Position(position.getAbs()+1, position.getOrd()),
                "UpRight",
                Colors.PINK,
                new NPCStats(1,1,1,1,1,1,1,1),
                colorize("   ", Attribute.BOLD(),Colors.PINK.textApply()),
                colorize(" / ", Attribute.BOLD(),Colors.PINK.textApply()));

        BossPart partDownRight = new BossPart(
                this,
                new Position(position.getAbs()+1, position.getOrd()+1),
                "DownRight",
                Colors.PINK,
                new NPCStats(1,1,1,1,1,1,1,1),
                colorize("<O ", Attribute.BOLD(),Colors.PINK.textApply()),
                colorize("_)>", Attribute.BOLD(),Colors.PINK.textApply()));

        BossPart partUpLeft = new BossPart(
                this,
                new Position(position.getAbs(), position.getOrd()),
                "UpLeft",
                Colors.PINK,
                new NPCStats(1,1,1,1,1,1,1,1),
                colorize("   ", Attribute.BOLD(),Colors.PINK.textApply()),
                colorize(" \\ ", Attribute.BOLD(),Colors.PINK.textApply()));

        BossPart partDownLeft = new BossPart(
                this,
                new Position(position.getAbs(), position.getOrd()+1),
                "DownLeft",
                Colors.PINK,
                new NPCStats(1,1,1,1,1,1,1,1),
                colorize(" O>", Attribute.BOLD(),Colors.PINK.textApply()),
                colorize("<(_", Attribute.BOLD(),Colors.PINK.textApply()));
        listBossParts.add(partUpLeft);
        listBossParts.add(partUpRight);
        listBossParts.add(partDownLeft);
        listBossParts.add(partDownRight);

        setBossParts(listBossParts);
    }

    public void doSpecialAction(Player player, GridMap gridmap) {
        gridmap.update(new Carrot(new Position(getPosition().getAbs(), getPosition().getOrd()),
                        Colors.ORANGE,
                        true,
                        true),
                 true);
    }

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

    public List<Direction> getBufferPath() {
        return bufferPath;
    }
}
