package entity.living;

import entity.object.potion.Potion;
import spells.FireAura;
import spells.FireBall;
import spells.Spell;
import utils.Position;

import java.util.Map;

import java.util.ArrayList;
import java.util.List;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private PlayerStats stats;
    private List<Potion> potionBelt;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name);
        stats = new PlayerStats(pv, pm, 1, 1, 1, 1, 0, level);
        spellList = new ArrayList<>();
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
        potionBelt = new ArrayList<>();
    }

    public String toString(){
        String currentLine = getDisplay();
        setDisplay("@@@");
        return currentLine;
    }

    public void addSpell(Spell spell) {
        spellList.add(spell);
    }

    public List<Spell> getSpells() {
        return spellList;
    }

    public PlayerStats getStats() {
        return stats;
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
