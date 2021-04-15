package entity.living;

import spells.FireAura;
import spells.FireBall;
import spells.Spell;
import utils.Position;

import java.util.Map;

import java.util.ArrayList;
import java.util.List;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private static PlayerStats stats;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, name);
        stats = new PlayerStats(pv, pm, 1, 1, 1, 1, 0, level);
        spellList = new ArrayList<>();
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
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
}
