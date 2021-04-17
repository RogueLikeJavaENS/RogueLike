package entity.living;

import entity.object.potion.Elixir;
import entity.object.potion.PotionHealth;
import entity.object.potion.XpBottle;
import spells.BasicAttack;
import entity.object.potion.Potion;
import spells.*;
import utils.Colors;
import utils.Direction;
import utils.Position;

import java.util.*;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private Spell selectedSpell;
    private List<Potion> potionBelt;
    private int[] potionCount;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name, Colors.WHITE, new PlayerStats(pv, pm, 1, 3, 15, 1, 0, level));
        spellList = new ArrayList<>();
        addSpell(new BasicAttack());
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
        potionBelt = new ArrayList<>();
        potionCount= new int[] {0, 0, 0};
        selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
        ArrayList<String> sprites = new ArrayList<>();
        sprites.add("o-o");
        sprites.add("/^\\");
        setSprites(sprites);
        setDirection(Direction.SOUTH);
    }

    public String toString(){
        String currentLine = getDisplay();
        setUpSprites("@@@");
        return currentLine;
    }

    public void addSpell(Spell spell) {
        spellList.add(spell);
    }

    public List<Spell> getSpells() {
        return spellList;
    }

    public PlayerStats getPlayerStats() {
        return (PlayerStats) stats;
    }

    public Spell getSelectedSpell() {
        return selectedSpell;
    }

    public void setSelectedSpell(Spell selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public List<Potion> getPotionBelt() {
        return potionBelt;
    }

    public void pickupPotion(Potion potion){
        potionBelt.add(potion);
        if (potion instanceof PotionHealth){
            potionCount[0]+=1;
        }
        else if (potion instanceof Elixir){
            potionCount[1]+=1;
        }
        else {
            potionCount[2]+=1;
        }
    }

    public void usePotion(Potion potion){
        potionBelt.remove(potion);
        if (potion instanceof PotionHealth){
            potionCount[0]-=1;
        }
        else if (potion instanceof Elixir){
            potionCount[1]-=1;
        }
        else {
            potionCount[2]-=1;
        }
    }

    public int getPotionHealthNumber(){
        return potionCount[0];
    }
    public int getElixirNumber(){
        return potionCount[1];
    }
    public int getXpBottleNumber(){
        return potionCount[2];
    }
}
