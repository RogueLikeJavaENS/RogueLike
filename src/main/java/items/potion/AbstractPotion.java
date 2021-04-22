package items.potion;

public class AbstractPotion implements Potion{
    final String potionName;

    public AbstractPotion(String potionName) {
        this.potionName = potionName;
    }

    public String getPotionName() { return potionName; }




}
