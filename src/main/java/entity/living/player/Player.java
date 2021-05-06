package entity.living.player;

import classeSystem.InGameClasses;
import entity.living.Inventory;
import entity.living.LivingEntity;
import spells.BasicAttack;
import spells.*;
import stuff.item.keys.FloorKey;
import stuff.item.potions.Elixir;
import stuff.item.potions.PotionHealth;
import stuff.item.potions.XpBottle;
import utils.Colors;
import utils.Direction;
import utils.Position;


import java.util.*;

public class Player extends LivingEntity {
    private final List<Spell> spellList;
    private Spell selectedSpell;
    private final Inventory inventory;
    private final InGameClasses classe;

    public Player(Position position, int pv, int pm, String name, InGameClasses classe, int level) throws IllegalArgumentException {
        super(position, name, Colors.WHITE, new PlayerStats(classe, 500, 5000, 1, 2, 15, 1, 0, level));
        spellList = new ArrayList<>();
        inventory = new Inventory();
        addSpell(new BasicAttack()); //hard coded to test
        addSpell(new FireAura());
        addSpell(new FireBall());
        selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
        setSprites("o-o", "/^\\", Colors.WHITE);
        setDirection(Direction.SOUTH);
        this.classe = classe;
        inventory.addItem(new FloorKey());
        inventory.addItem(new Elixir());
        inventory.addItem(new Elixir());
        inventory.addItem(new Elixir());
        inventory.addItem(new Elixir());
        inventory.addItem(new Elixir());
        inventory.addItem(new Elixir());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());
        inventory.addItem(new PotionHealth());

    }

    public void addSpell(Spell spell) {
        spellList.add(spell);
    }
    public void setSelectedSpell(Spell selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public Inventory getInventory() { return inventory; }
    public List<Spell> getSpells() {
        return spellList;
    }
    public PlayerStats getPlayerStats() {
        return (PlayerStats) stats;
    }
    public Spell getSelectedSpell() { return selectedSpell; }
    public InGameClasses getClasse() { return classe; }
}
