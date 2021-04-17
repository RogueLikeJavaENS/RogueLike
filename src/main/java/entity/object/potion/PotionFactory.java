package entity.object.potion;

import utils.Position;

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
