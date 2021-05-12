package game.entity.living.player.classeSystem;

/**
 * enumerator handling all reward by level for the rangerClass.
 * @author luca
 */

//this structure can be reused for each new class being added.
public enum pathRanger {

    REWARD_0("Skill", 1, "BasicAttackRanger"),
    //REWARD_1("Skill", 1, "PutTrap"),
    //REWARD_2("Skill", 2, "FireArrow"),
    //REWARD_3("Skill", 3, "Dash"),
    REWARD_4("Skill", 4, "Heal");
    //REWARD_5("Skill", 5, "Sniper"),
    //REWARD_6("Skill", 6, "HundredArrows");

    private String info;
    private Integer level;
    private String reward;

    pathRanger(String info, Integer level, String reward){
        this.info = info;
        this.level=level;
        this.reward = reward;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public void setReward(String reward) {
        this.reward = reward;
    }
}
