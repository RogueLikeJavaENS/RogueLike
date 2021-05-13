package game.elements;

import game.entity.living.npc.monster.MonsterType;
import game.entity.living.player.classeSystem.InGameClasses;
import game.stuff.equipment.Equipment;
import game.stuff.equipment.EquipmentRarity;
import game.stuff.equipment.EquipmentType;
import game.stuff.item.ItemType;
import java.util.Random;


/**
 * The Class Contains every rules of the game.
 * Percentage of drop, monster encounter. etc...
 */
public class GameRule {
    private final static Random GEN = new Random();

    /////////// Hole and Spike /////////

    /**
     * 5 to 10 hole in a room
     */
    public int numberOfHole(){
        return GEN.nextInt(5)+5;
    }

    /**
     * 1 to 3 tiles of hole
     */
    public int sizeOfPathHole(){
        return GEN.nextInt(3)+1;
    }



    /**
     * 10 to 15 spike in a room
     */
    public int numberOfSpike(){
        return GEN.nextInt(5)+10;
    }

    /**
     * 1 to 7 tiles of spike
     */
    public int sizeOfPathSpike(){
        return GEN.nextInt(7)+1;
    }



    /////////////// Monster Room //////////////

    /**
     * 30% of chance to have a chest in a monster room
     */
    public boolean presenceOfClassicChestOnMonsterRoom(){
        int nb = GEN.nextInt(100);
        return nb < 20;
    }

    /**
     *
     */
    public int getMonsterType(){
        return GEN.nextInt(6);

    }



    //////////// Treasure Room //////////
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


    ////////// Monster Loot //////////
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


    ///////////// Type of game.stuff //////////////
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
     * Equipment Type
     * - WEAPON 34%
     * - SHIELD 11%
     * - ARMOR 11%
     * - BOOT 11%
     * - GLOVE 11%
     * - HELMET 11%
     * - PANT 11%
     */
    public EquipmentType getEquipmentType(){
        int nb = GEN.nextInt(100);
        if (nb < 34){ return EquipmentType.WEAPON; }
        else if (nb < 45) { return EquipmentType.SHIELD; }
        else if (nb < 56) { return EquipmentType.ARMOR; }
        else if (nb < 67) { return EquipmentType.BOOT; }
        else if (nb < 78) { return EquipmentType.GLOVE; }
        else if (nb < 89) { return EquipmentType.HELMET; }
        else { return EquipmentType.PANT; }
    }


    /**
     * Get the rarity of Equipment
     *
     * Classic
     * - E classic 39% / gold 0%
     * - D classic 30% / gold 0%
     * - C classic 20% / gold 3%
     * - B classic 10% / gold 22%
     * - A classic 1% / gold 25%
     * - S classic 0% / gold 25%
     * - L classic 0% / gold 25%
     *
     */
    public EquipmentRarity getEquipmentRarity(boolean isClassic){
        int nb = GEN.nextInt(100);
        if (isClassic){
            if (nb < 39){ return EquipmentRarity.E; }
            else if (nb < 69) { return EquipmentRarity.D; }
            else if (nb < 89) { return EquipmentRarity.C; }
            else if (nb < 99) { return EquipmentRarity.B; }
            else { return EquipmentRarity.A; }
        }
        else{
            if (nb < 3){ return EquipmentRarity.C; }
            else if (nb < 25) { return EquipmentRarity.B; }
            else if (nb < 50) { return EquipmentRarity.A; }
            else if (nb < 75) { return EquipmentRarity.S; }
            else { return EquipmentRarity.L; }
        }
    }

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

    public int getEquipmentPrice(int level, EquipmentRarity rarity) {
        return level * (rarity.ordinal()*2+2) * 5;
    }

    public int getPotionPrice(int level, ItemType type) {
        switch (type) {
            case HEALTH_POTION:
            case ELIXIR:
                return Math.max(10 ,(int) Math.floor(Math.log(level*level*2) *10));
            case XP_BOTTLE:
                return Math.max(15, (int) Math.floor(Math.log(level*level*2) *15));
            case GOLD_KEY:
                return (int) Math.floor(Math.log(level*level*2) *300);
            default:
                return (int) Math.floor(Math.log(level*level*2) *999);
        }
    }

    /**
     *  Between 2 and 5 Equipments in Merchant Shop
     * @return number of Equipments.
     */
    public int getNumberOfEquipMerchantShop() {
        return GEN.nextInt(4)+2;
    }

    public EquipmentType getEquipmentTypeInMerchantShop() {
        int type = GEN.nextInt(7);
        return EquipmentType.values()[type];
    }

    /**
     *  D 40%
     *  C 24%
     *  B 16%
     *  A 12%
     *  S 6%
     *  L 2%
     */
    public EquipmentRarity getRarityEquipmentInMerchantShop() {
        int nb = GEN.nextInt(100);
        if (nb < 40) {
            return EquipmentRarity.D;
        } else if (nb<64) {
            return EquipmentRarity.C;
        } else if (nb < 80) {
            return EquipmentRarity.B;
        } else if (nb < 92) {
            return EquipmentRarity.A;
        } else if (nb < 98) {
            return EquipmentRarity.S;
        } else return EquipmentRarity.L;
    }

    /*
    * L 15 %
    * S 35 %
    * A 50 %
    */
    public EquipmentRarity getRarityOnBossCorpse() {
        int nb = GEN.nextInt(100);
        if (nb < 50) {
            return EquipmentRarity.A;
        } else if (nb<85) {
            return EquipmentRarity.S;
        } else return EquipmentRarity.L;
    }

    public int getNumberOfPotionsOnCorpse() {
        return GEN.nextInt(2)+2;
    }

    public int getNumberOfEquipmentsOnCorpse() {
        return GEN.nextInt(3);
    }

    public int getNumberOfEquipmentsOnBossCorpse() {
        return GEN.nextInt(2)+1;
    }

    public void SetBonusEquipement(Equipment equipement, InGameClasses classe){
        GameRule gamerule = new GameRule();
        int value = equipement.getLevel()+equipement.getRarity().ordinal()+1;
        switch (equipement.getType()){
            case WEAPON:
                switch (classe){
                    case RANGER:
                    case WARRIOR:
                        equipement.setBonusDamage(value);
                        break;
                    case MAGE:
                        equipement.setBonusDamage(value/4);
                        equipement.setBonusMana(value*10);
                        break;
                }
                break;
            case SHIELD:
                switch (classe){
                    case RANGER:
                        equipement.setBonusInitiative((value/2));
                        equipement.setBonusArmor(value/4);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value);
                        equipement.setBonusArmor(value/2);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value);
                        break;
                }
                break;
            case ARMOR:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value/2);
                        equipement.setBonusInitiative(value/4);
                        equipement.setBonusMana(value/4);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value*5);
                        equipement.setBonusArmor(value/4);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value);
                        equipement.setBonusLife(value*5);
                        break;
                }
                break;
            case BOOT:
                switch (classe){
                    case RANGER:
                        equipement.setBonusInitiative(value);
                        equipement.setBonusArmor(1);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value);
                        equipement.setBonusArmor(1);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value);
                        break;
                }
                break;
            case GLOVE:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value/4);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value);
                        equipement.setBonusArmor(value/4);
                        break;
                    case WARRIOR:
                        equipement.setBonusDamage(value*2);
                        equipement.setBonusArmor(value/4);
                        break;
                }
                break;
            case PANT:
                switch (classe) {
                    case RANGER:
                        equipement.setBonusArmor(value/2);
                        break;
                    case MAGE:
                        equipement.setBonusArmor(value/4);
                        break;
                    case WARRIOR:
                        equipement.setBonusInitiative(value/4);
                        equipement.setBonusArmor(value/2);
                        break;
                }
                break;
            case HELMET:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value/2);
                        break;
                    case MAGE:
                        equipement.setBonusArmor(value/4);
                        equipement.setBonusInitiative(value/4);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value);
                        break;
                }
                break;
        }
    }

    ///////////// Bonus of equipment //////////////

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

    /**
     * 5% of chance that a chest is a mimic
     */
    public boolean isChestMimic() {
        int nb = GEN.nextInt(100);
        return nb < 5;
    }

    public static void main(String[] args) {
        System.out.println(new GameRule().getEquipmentPrice(1, EquipmentRarity.L));
    }
}
