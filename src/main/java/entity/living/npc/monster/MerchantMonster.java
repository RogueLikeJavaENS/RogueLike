package entity.living.npc.monster;

import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import static com.diogonunes.jcolor.Ansi.colorize;

public class MerchantMonster extends AbstractMonster{
    private final static int basicHP = 100;
    private final static int basicMP = 10;
    private final static int hpModifier = 5;
    private final static int mpModifier = 1;

    /**
     * Create a new Skeleton
     *
     * @param position his position in the room
     * @param name his name
     * @param level his level
     * @param strategy his strategy to apply
     */
    public MerchantMonster(Position position, String name, int level, Strategy strategy) {
        super(position, name, Colors.WHITE, strategy,
                new MonsterStats(basicHP+(hpModifier*level),
                        basicMP+(mpModifier*level),
                        1,
                        1+level,
                        15+level,
                        1+level,
                        200+level,
                        1+level,
                        15*level));
        setSprites("~.~", "|_|", Colors.CYAN, Colors.MAGENTA);
        setBasicSprites("~.~", "|_|");
    }
}
