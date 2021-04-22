package items.potion;

public class AbstractPotion implements Potion{
    private final String potionName;
    private final PotionType potionType;

    public AbstractPotion(String potionName,PotionType potionType) {
        this.potionName = potionName;
        this.potionType = potionType;
    }

    public String getPotionName() { return potionName; }
    public int getPotionType() { return potionType.ordinal(); }



}
