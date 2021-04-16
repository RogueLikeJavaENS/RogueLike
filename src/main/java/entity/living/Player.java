package entity.living;

import spells.BasicAttack;
import spells.FireAura;
import spells.FireBall;
import spells.Spell;
import utils.Position;

import java.util.ArrayList;
import java.util.List;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private Spell selectedSpell;
    private static PlayerStats stats;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name, new PlayerStats(pv, pm, 1, 3, 1, 1, 0, level));
        spellList = new ArrayList<>();
        addSpell(new BasicAttack());
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
        selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
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

    public PlayerStats getPlayerStats() {
        return stats;
    }

    public Spell getSelectedSpell() {
        return selectedSpell;
    }

    public void setSelectedSpell(Spell selectedSpell) {
        this.selectedSpell = selectedSpell;
    }
}
