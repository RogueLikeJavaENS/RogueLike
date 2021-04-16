package monsterStrategy;

import entity.living.monster.Monster;

public interface Condition {
    public boolean isVerified(Monster monster);

}
