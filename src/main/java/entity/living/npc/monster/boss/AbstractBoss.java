package entity.living.npc.monster.boss;

import display.GridMap;
import entity.living.AbstractStats;
import entity.living.npc.monster.AbstractMonster;
import gameElement.GameState;
import monsterStrategy.Strategy;
import utils.Colors;
import utils.Position;

import java.util.List;

public abstract class AbstractBoss extends AbstractMonster implements Boss {

    List<BossPart> bossParts;
    boolean hasMoved;

    public AbstractBoss(String name, Position position, Colors color, Strategy strategy, AbstractStats stats) {
        super(position, name, color, strategy, stats);
        hasMoved = false;
    }

    public void setBossParts(List<BossPart> bossParts) {
        this.bossParts = bossParts;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        hasMoved = moved;
    }

    public List<BossPart> getBossPartList() {
        return bossParts;
    }
}
