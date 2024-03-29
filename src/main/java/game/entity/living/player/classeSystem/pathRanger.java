package game.entity.living.player.classeSystem;

/**
 * Enumerator handling every reward for each level for the Ranger Class.
 * @author luca
 */

//this structure can be reused for each new class being added.
public enum pathRanger {

    REWARD_0("Skill", 1, "BasicAttackRanger"),
    REWARD_1("Skill", 1, "Trap"),
    REWARD_2("Skill", 2, "FireArrow"),
    REWARD_3("Skill", 3, "Dash"),
    REWARD_4("Skill", 4, "Heal"),
    REWARD_5("Skill", 5, "Sniper"),
    REWARD_6("Skill", 6,"Multishot");

    private String info;
    private Integer level;
    private String reward;

    pathRanger(String info, Integer level, String reward){
        this.info = info;
        this.level=level;
        this.reward = reward;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getReward() {
        return reward;
    }
}
