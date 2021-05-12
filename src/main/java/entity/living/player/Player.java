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
    private final PlayerInventory playerInventory;
    private final InGameClasses classe;

    public Player(Position position, String name, InGameClasses classe, int level) throws IllegalArgumentException {
        super(position, name, Colors.WHITE, new PlayerStats(classe, 100, 100, 1, 2, 1, 1, 200, level));
        playerInventory = new PlayerInventory();
        setSprites("o-o", "/^\\", Colors.WHITE);
        setDirection(Direction.SOUTH);
        this.classe = classe;
        for (int i = 0; i < 100; i++) {
            playerInventory.addItem(new XpBottle());
        }
    }

    public PlayerInventory getInventory() { return playerInventory; }
    public PlayerStats getPlayerStats() {
        return (PlayerStats) stats;
    }
    public InGameClasses getClasse() { return classe; }
}
