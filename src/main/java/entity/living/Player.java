package entity.living;

import spells.BasicAttack;
import entity.object.potion.Potion;
import spells.*;
import utils.Direction;
import utils.Position;

import java.util.*;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private Spell selectedSpell;
    private List<Potion> potionBelt;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name, new PlayerStats(pv, pm, 1, 3, 15, 1, 0, level));

        spellList = new ArrayList<>();
        addSpell(new BasicAttack());
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
        potionBelt = new ArrayList<>();
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
    }

    public void usePotion(Potion potion){
        potionBelt.remove(potion);
    }
}
