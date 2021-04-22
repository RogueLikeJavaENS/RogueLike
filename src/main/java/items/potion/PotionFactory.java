package items.potion;

import items.AbstractItemFactory;
import items.potion.potionType.Elixir;
import items.potion.potionType.EmptyBottle;
import items.potion.potionType.PotionHealth;
import items.potion.potionType.XpBottle;

public class PotionFactory extends AbstractItemFactory {

    public Potion getItemPotion(PotionType potionType, String name){
        switch (potionType){
            case HEALTH:
                return new PotionHealth();
            case ELIXIR:
                return new Elixir();
            case XP:
                return new XpBottle();
            case EMPTY:
                return new EmptyBottle();
        }
        return null;
    }


}
