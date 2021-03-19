package entity.living;

import utils.Position;


public class Player extends LivingEntity {

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
    }

    public String toString(){
        return "@@";
    }
}
