package items.potion;

import items.AbstractItemFactory;
import items.potion.potionType.Elixir;
import items.potion.potionType.EmptyBottle;
import items.potion.potionType.PotionHealth;
import items.potion.potionType.XpBottle;

public class PotionFactory extends AbstractItemFactory {

    public Potion getItemPotion(int potionType){
        switch (potionType){
            case 0:
                return new PotionHealth();
            case 1:
                return new Elixir();
            case 2:
                return new XpBottle();
            case -1:
                return new EmptyBottle();
        }
        return null;
    }


}
