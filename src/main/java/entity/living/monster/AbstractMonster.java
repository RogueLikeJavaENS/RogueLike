package entity.living.monster;

import entity.living.MonsterStats;
import entity.living.NPC;
import entity.living.Player;
import gameElement.GameState;
import monsterStrategy.StrategyUtils;
import utils.Direction;
import utils.Position;

public class AbstractMonster extends NPC implements Monster {

    public AbstractMonster(Position position, String name, int level) throws IllegalArgumentException {
        super(position, name);
        this.stats=new MonsterStats(10,10,1,1,1,1, 0,level, 10);
    }

    @Override
    public void doAction(GameState gameState) {
        System.out.println("my turn ! " + getName());
    }



    private boolean approach(Player player){
        boolean move = StrategyUtils.getDistance(this, player) > 1;
        if (move){
            StrategyUtils.updatePos(this, StrategyUtils.goToPlayerDir(this, player));
        }
        return move;
    }

    private boolean escape(Player player){
        boolean move = StrategyUtils.getDistance(this,player) > 1;
        if (move) {
            StrategyUtils.updatePos(this, StrategyUtils.goToPlayerDir(this,player).oppositeDirection());
        }
        return move;
    }

    private void attack(Player player){
        int damage = getMonsterStats().getRawDamage();
        player.getStats().setLifePoint(player.getStats().getLifePoint()-damage);
    }

}
