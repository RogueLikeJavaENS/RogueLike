package classeSystem;

/**
 * enumerator handling all reward by level for the mageClass.
 * @author luca
 */

//this structure can be reused for each new class being added.
public enum pathMage {

    REWARD_0("Skill", 1, "BasicAttack"),
    REWARD_1("Skill", 1, "FireAura"),
    REWARD_2("Skill", 2, "FireBall");

    private String info;
    private Integer level;
    private String reward;

    pathMage(String info, Integer level, String reward){
        this.info = info;
        this.level=level;
        this.reward = reward;
    }

    public static pathMage getRewardForLevel(Integer level){
        for(pathMage reward : pathMage.values()){
            if(reward.getLevel().equals(level)){
                return reward;
            }
        }
        return null;
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
