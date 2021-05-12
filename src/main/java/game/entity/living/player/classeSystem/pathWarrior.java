package game.entity.living.player.classeSystem;

/**
 * enumerator handling all reward by level for the warriorClass.
 * @author luca
 */

//this structure can be reused for each new class being added.
public enum pathWarrior {

    REWARD_0("Skill", 1, "BasicAttackWarrior"),
    REWARD_1("Skill",1,"StrongPunch"),
    REWARD_2("Skill",2,"TourbiLOL"),
    //REWARD_3("Skill",3,"Charge");
    //REWARD_4("Skill",4,"IronSkin");
    REWARD_5("Skill",5,"ThrowAxe"),
    REWARD_6("Skill",6,"Earthquake");

    private String info;
    private Integer level;
    private String reward;

    pathWarrior(String info, Integer level, String reward){
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
