package entity.object.potion;

import utils.Position;

/**
 * This Factory is used to generate any given potion type, for now go from 0 to 2.
 *
 * @author luca
 */

public class PotionFactory {

    public Potion getPotion(int potionType, Position position){
        switch (potionType){
            case 0:
                return new PotionHealth(position);
            case 1:
                return new Elixir(position);
            case 2:
                return new XpBottle(position);
        }
        return new EmptyBottle(position);
    }

    public Potion getPotion(int potionType) {
        return getPotion(potionType, new Position(0,0));
    }
}
