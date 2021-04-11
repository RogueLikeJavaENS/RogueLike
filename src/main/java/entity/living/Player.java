package entity.living;

import utils.Position;

import java.util.Map;


public class Player extends LivingEntity {
    private static PlayerStats Stats;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
        this.Stats = new PlayerStats(pv, pm, 1, 1, level);
    }

    public String toString(){
        String currentLine = getDisplay();
        setDisplay("@@@");
        return currentLine;
    }

    public static void main(String[] args) {
        Player test = new Player(new Position(0,0),100, 100, "Mario", 1);
        for (int i = 1; i <= 100; i++) {
            //System.out.println(Stats.levelCap.get(i));
            System.out.println("---------");
            //System.out.println(i);
            System.out.println(String.format("my current level is %d", Stats.getLevel()));
            System.out.println(String.format("i have that much xp %d", Stats.getXp()));
            System.out.println(Stats.getLifePoint());
            System.out.println(Stats.getManaPoint());
            System.out.println(Stats.getRawDamage());
            //System.out.println(Stats.levelCap.get(Stats.getLevel()));
            Stats.grantXP(1000);
            System.out.println("i won a 1000 xp point");
            System.out.println(String.format("my new level is %d", Stats.getLevel()));
            System.out.println(String.format("i now have %d xp", Stats.getXp()));
            System.out.println(Stats.getLifePoint());
            System.out.println(Stats.getManaPoint());
            System.out.println(Stats.getRawDamage());
            //System.out.println(Stats.levelCap.get(Stats.getLevel()));
            System.out.println("---------");
        }
    }
}
