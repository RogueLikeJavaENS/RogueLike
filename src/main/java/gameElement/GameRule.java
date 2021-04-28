package gameElement;

import stuff.item.ItemType;
import java.util.Random;


/**
 * The Class Contains every rules of the game.
 * Percentage of drop, monster encounter. etc...
 */
public class GameRule {
    private final static Random GEN = new Random();

    /**
     * between 3 and 6 potions in Treasure Room.
     */
    public int getNumberOfPotionInTreasureRoom(){
        return GEN.nextInt(4)+3;
    }

    /**
     * between 8 and 15 gold by room, 1% chance of 50 golds.
     */
    public int getNumberOfGoldInTreasureRoom(){
        int nb = GEN.nextInt(100);
        if (nb == 0){
            return 50;
        }
        else {
        return GEN.nextInt(8)+8;
        }
    }


    /**
     * Monster
     * - 50% 1 potion
     * - 20% 2 potions
     * - 20% nothing
     * - 9% 3 potions
     * - 1% 4 potions
     */
    public int getNumberOfPotionOnMonster(){
        int nb = GEN.nextInt(100);
        if (nb < 50){
            return 1;
        }
        else if (nb < 70){
            return 2;
        }
        else if (nb < 90){
            return 0;
        }
        else if (nb < 99){
            return 3;
        }
        else{
            return 4;
        }
    }


    /**
     * Type of Potion
     * - 40% health
     * - 40% mana
     * - 20% XP
     */
    public ItemType getPotionType(){
        int nb = GEN.nextInt(100);
        if (nb < 40){
            return ItemType.HEALTH_POTION;
        }
        else if(nb < 80){
            return ItemType.ELIXIR;
        }
        else{
            return ItemType.XP_BOTTLE;
        }
    }

    /**
     * Rarity of Equipment
     * -
     * -
     * -
     * -
     */

    //TODO get the rarity of an equipment

    ////////////  Chest  =   true : classic / false : golden
    /**
     * Between 1 and 3 potion in a classic chest
     * Between 10 and 15 potion in a golden chest
     */
    public int getNumberOfPotionInChest(boolean isClassic) {
        if (isClassic) {
            return GEN.nextInt(3) + 1;
        }
        else {
            return GEN.nextInt(6)+10;
        }
    }
    /**
     * Between 0 and 5 gold in a classic chest
     * Between 30 and 50 gold in a golden chest
     */
    public int  getNumberOfGoldInChest(boolean isClassic){
        if (isClassic){
            return GEN.nextInt(6);
        }
        else {
            return GEN.nextInt(21)+30;
        }
    }
    /**
     * Between 1 and 3 equipment in a classic chest
     */
    public int getNumberOfEquipmentInChest(){
        return GEN.nextInt(3)+1;
    }



    /**
     * 5% of luck to have a golden key
     */
    public boolean presenceOfGoldenKeyInClassicChest(){
        return (GEN.nextInt(100) < 5);
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
