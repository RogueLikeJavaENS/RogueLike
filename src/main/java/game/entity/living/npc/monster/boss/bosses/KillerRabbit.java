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

/**
 * class handling the KillerRabit boss by extending the AbstractBoss class.
 */
public class KillerRabbit extends AbstractBoss {

    /**
     * create a Killer rabbit with the super of Abstract bosses then construct the
     * different boss part, before setting them.
     * @param name the name of the bosses.
     * @param position the position we want to put the main boss (each bossPart position will be calculated with it).
     * @param color the color used to color the main boss part.
     * @param strategy the bossStrategy we ant to use for this boss.
     * @param stats the boss main part stats.
     */
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

    /**
     * method handling the specialAction of the KillerRabbit, planting explosive carrot into the ground.
     * @param player not used here, but present so a boss can use it.
     * @param gridmap to be able to place the Carrot on the Map.
     */
    public void doSpecialAction(Player player, GridMap gridmap) {
        gridmap.update(new Carrot(new Position(getPosition().getAbs(), getPosition().getOrd()),
                        Colors.ORANGE,
                        true,
                        true),
                 true);
    }
}
