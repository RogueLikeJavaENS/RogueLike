package entity.living.player;

import classeSystem.InGameClasses;
import entity.living.inventory.PlayerInventory;
import entity.living.LivingEntity;
import spells.BasicAttack;
import spells.*;
import stuff.item.ItemFactory;
import stuff.item.potions.XpBottle;
import utils.Colors;
import utils.Direction;
import utils.Position;


import java.util.*;

public class Player extends LivingEntity {
    //private final List<Spell> spellList;
    //private Spell selectedSpell;
    private final PlayerInventory playerInventory;
    private final InGameClasses classe;

    public Player(Position position, String name, InGameClasses classe, int level) throws IllegalArgumentException {
        super(position, name, Colors.WHITE, new PlayerStats(classe, 100, 100, 1, 2, 1, 1, 200, level));
        //spellList = new ArrayList<>();
        playerInventory = new PlayerInventory();
        //addSpell(new BasicAttack()); //hard coded to test
        //addSpell(new FireAura());
        //addSpell(new FireBall());
        //selectedSpell = spellList.get(0); //Default selected attack is the BasicAttack
        setSprites("o-o", "/^\\", Colors.WHITE);
        setDirection(Direction.SOUTH);
        this.classe = classe;
        for (int i = 0; i < 100; i++) {
            playerInventory.addItem(new XpBottle());
        }
    }

    /*public void addSpell(Spell spell) {
        spellList.add(spell);
    }*/
//    public void setSelectedSpell(Spell selectedSpell) {
//        this.selectedSpell = selectedSpell;
//    }

    public PlayerInventory getInventory() { return playerInventory; }
//    public List<Spell> getSpells() {
//        return spellList;
//    }
    public PlayerStats getPlayerStats() {
        return (PlayerStats) stats;
    }
//    public Spell getSelectedSpell() { return selectedSpell; }
    public InGameClasses getClasse() { return classe; }
}
