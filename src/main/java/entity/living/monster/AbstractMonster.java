package entity.living.monster;

import entity.living.MonsterStats;
import entity.living.NPC;
import entity.living.Player;
import gameElement.GameState;
import monsterStrategy.StrategyUtils;
import utils.Direction;
import utils.Position;

public class AbstractMonster extends NPC implements Monster {
    private MonsterStats monsterStats;

    public AbstractMonster(Position position, int pv, int pm, int range, int rawDamage, int naturalArmor, String name, int level, int xpWorth) throws IllegalArgumentException {
        super(position,name);
        monsterStats = new MonsterStats(pv, pm, range, rawDamage, naturalArmor,level, xpWorth);
    }

    @Override
    public  void doAction(GameState gameState) {
        System.out.println("my turn ! " + getName());
    }

    public MonsterStats getMonsterStats() { return monsterStats; }
    public void setMonsterStats(MonsterStats stats) { this.monsterStats = stats; }





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
