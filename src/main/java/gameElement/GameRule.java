package gameElement;

import stuff.item.ItemType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * The Class Contains every rules of the game.
 * Percentage of drop, monster encounter. etc...
 */
public class GameRule {
    private final List<Integer> dropRatePotion;
    private final List<ItemType> dropRatePotionType;
    private final List<Integer> numberOfGoldInTreasureRoom;
    private final List<Integer> numberOfPotionInTreasureRoom;
    private final static Random GEN = new Random();


    public GameRule() {
        this.dropRatePotion = new ArrayList<>();
        this.dropRatePotionType = new ArrayList<>();
        this.numberOfGoldInTreasureRoom = new ArrayList<>();
        this.numberOfPotionInTreasureRoom = new ArrayList<>();

        setDropRatePotion();
        setDropRatePotionType();
        setNumberOfGoldInTreasureRoom();
        setNumberOfPotionInTreasureRoom();
    }

    /**
     * between 3 and 6 potions in Treasure Room.
     */
    private void setNumberOfPotionInTreasureRoom() {
        for (int i = 3; i <= 6; i++) {
            for (int j = 0; j < 30; j++) {
                numberOfPotionInTreasureRoom.add(i);
            }
        }
        numberOfPotionInTreasureRoom.add(5);
        Collections.shuffle(numberOfPotionInTreasureRoom);
    }

    /**
     * between 8 and 15 gold by room, 1% chance of 50 golds.
     */
    private void setNumberOfGoldInTreasureRoom() {
        for (int i = 8; i <= 15; i++) {
            for (int j = 0; j < 12; j++) {
                numberOfGoldInTreasureRoom.add(i);
            }
        }
        numberOfGoldInTreasureRoom.add(100);
        Collections.shuffle(numberOfGoldInTreasureRoom);
    }
    /**
     * - 50% 1 potion
     * - 20% 2 potions
     * - 20% nothing
     * - 9% 3 potions
     * - 1% 4 potions
     */
    private void setDropRatePotion() {
        for (int i = 0; i < 60; i++) { // 1 potion
            dropRatePotion.add(1);
        }
        for (int i = 0; i < 5; i++) { // 0 potion
            dropRatePotion.add(0);
        }
        for (int i = 0; i < 25; i++) { // 2 potions
            dropRatePotion.add(2);
        }
        for (int i = 0; i < 9; i++) { // 3 potions
            dropRatePotion.add(3);
        }
        dropRatePotion.add(4); // 4 potions
        Collections.shuffle(dropRatePotion);
    }

    /**
     * - 40% health
     * - 40% mana
     * - 20% XP
     */
    private void setDropRatePotionType() {
        for (int i = 0; i < 45; i++) { // Health
            dropRatePotionType.add(ItemType.HEALTH_POTION);
        }
        for (int i = 0; i < 35; i++) { // Mana
            dropRatePotionType.add(ItemType.ELIXIR);
        }
        for (int i = 0; i < 20; i++) { // XP
            dropRatePotionType.add(ItemType.XP_BOTTLE);
        }
        Collections.shuffle(dropRatePotionType);
    }


    public int getNumberOfPotionInTreasureRoom() {
        return numberOfPotionInTreasureRoom.get(GEN.nextInt(100));
    }
    public int getPotionNumber() {
        return dropRatePotion.get(GEN.nextInt(100));
    }
    public ItemType getPotionType() {
        return dropRatePotionType.get(GEN.nextInt(100));
    }
    public int getNumberOfGoldInTreasureRoom() {
        return numberOfGoldInTreasureRoom.get(GEN.nextInt(numberOfGoldInTreasureRoom.size()));
    }

    public int getBonusDamage(int level) {
        return level;
    }
    public int getBonusMana(int level) {
        return level;
    }
    public int getBonusInitiative(int level) {
        return level;
    }
    public int getBonusLife(int level) {
        return level;
    }
    public int getBonusArmor(int level) {
        return level;
    }
}
