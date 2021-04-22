package entity.living.player;

import entity.living.LivingEntity;
import entity.object.Elixir;
import entity.object.PotionHealth;
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
    private final List<Potion> potionBelt;
    private final int[] potionCount;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name, Colors.WHITE, new PlayerStats(pv, pm, 1, 2, 15, 1, 0, level));
        spellList = new ArrayList<>();
        addSpell(new BasicAttack()); //hard coded to test
        addSpell(new FireAura());
        addSpell(new FireBall());
        potionBelt = new ArrayList<>();
        potionCount= new int[] {0, 0, 0};
        selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
        setSprites("o-o", "/^\\", Colors.WHITE);
        setDirection(Direction.SOUTH);
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

    public void consummePotion(Potion potion){
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
