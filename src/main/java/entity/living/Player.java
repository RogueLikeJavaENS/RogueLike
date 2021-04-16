package entity.living;

import spells.FireAura;
import spells.FireBall;
import spells.Spell;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.List;


public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private static PlayerStats Stats;

    public Player(Position position, int pv, int pm, String name, int level) throws IllegalArgumentException {
        super(position, pv, pm, name, level);
        this.Stats = new PlayerStats(pv, pm, 1, 1, 1, level);
        spellList = new ArrayList<>();
        addSpell(new FireBall()); //hard coded to test
        addSpell(new FireAura());
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
}
