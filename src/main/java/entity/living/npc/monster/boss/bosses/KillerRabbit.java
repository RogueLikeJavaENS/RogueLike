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

    public KillerRabbit(String name, Position position, Colors color, Strategy strategy, AbstractStats stats) {
        super(name, position, color, strategy, stats);
        List<BossPart> listBossParts = new ArrayList<>();
        setSprites(colorize("   ", Attribute.BOLD(),Colors.PINK.textApply()),
                colorize(" \\ ", Attribute.BOLD(),Colors.PINK.textApply()));

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

    @Override
    public void setActedSpecial(boolean hasSpecial) {
        super.setActedSpecial(true);
    }

    public void doSpecialAction(Player player, GridMap gridmap) {
        gridmap.update(new Carrot(new Position(getPosition().getAbs(), getPosition().getOrd()),
                        Colors.ORANGE,
                        true,
                        true),
                 true);
    }
}
