package entity.living.monster;

import entity.living.*;
import gameElement.GameState;
import monsterStrategy.Strategy;
import utils.Position;
import utils.Colors;
import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;


public abstract class AbstractMonster extends NPC implements Monster {
    private Strategy strategy;
    private boolean agroPlayer;


    public AbstractMonster(Position position, String name, Colors color, Strategy strategy, AbstractStats stats) throws IllegalArgumentException {
        super(position, name, color, stats);
        this.strategy = strategy;
        this.agroPlayer = false;
    }

    @Override
    public MonsterStats getMonsterStats() {
        return (MonsterStats) stats;
    }
    public Strategy getStrategy() { return strategy; }
    public boolean isAgroPlayer() { return agroPlayer; }
    public String getName(){ return super.getName();}

    public void setAgroPlayer(boolean agroPlayer) {
        this.agroPlayer = agroPlayer;
        String upSprite = this.getBasicSprites(0);
        String downSprite = this.getBasicSprites(1);
        List<String> newSprite = new ArrayList<>(2);
        if (agroPlayer){
            newSprite.add(colorize(upSprite,Colors.ORANGE.textApply()));
            newSprite.add(colorize(downSprite,Colors.ORANGE.textApply()));
        }
        else {
            newSprite.add(colorize(upSprite,Colors.WHITE.textApply()));
            newSprite.add(colorize(downSprite,Colors.WHITE.textApply()));
        }
        this.setSprites(newSprite);
    }

    public void doAction(GameState gameState) {
        getStrategy().doAct(this, gameState.getPlayer(), gameState.getGridMap());
        gameState.getDescriptor().updateDescriptor(getStrategy().getStrategyDescription());
    }
}
