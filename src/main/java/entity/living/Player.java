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
    private static PlayerStats Stats;
    private Spell selectedSpell;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
        this.Stats = new PlayerStats(pv, pm, 1, 1, 1, level);
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

    public Spell getSelectedSpell() {
        return selectedSpell;
    }

    public void setSelectedSpell(Spell selectedSpell) {
        this.selectedSpell = selectedSpell;
    }
}
