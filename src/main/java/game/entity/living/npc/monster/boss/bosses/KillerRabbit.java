package game.entity.living.npc.monster.boss.bosses;

import com.diogonunes.jcolor.Attribute;
import display.GridMap;
import game.entity.living.AbstractStats;
import game.entity.living.npc.NPCStats;
import game.entity.living.npc.monster.boss.AbstractBoss;
import game.entity.living.npc.monster.boss.BossPart;
import game.entity.living.player.Player;
import game.entity.object.traps.Carrot;
import game.entity.living.npc.monster.monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import java.util.ArrayList;
import java.util.List;

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
