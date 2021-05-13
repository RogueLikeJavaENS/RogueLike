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

    /////////////////// All Room /////////////////////
    /**
     * Get the number of hole when generating a room.
     * 5 to 10 hole in a room
     */
    public int numberOfHoleAtGeneration(){
        return GEN.nextInt(5)+5;
    }

    /**
     * Get the number of spike when generating a room.
     * 10 to 15 spike in a room
     */
    public int numberOfSpikeAtGeneration(){
        return GEN.nextInt(5)+10;
    }

    /////////////// Monster Room //////////////

    /**
     * When generating get if there is a chest in the room.
     * 30% of chance to have a chest in a monster room.
     */
    public boolean presenceOfClassicChestOnMonsterRoom(){
        int nb = GEN.nextInt(100);
        return nb < 20;
    }

    /**
     * Get the the monster type to add when generating the room.
     * Equal chance of every mobs.
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

    ///////////// Type of game.stuff //////////////
    /**
     * Type of Potion
     * - 40% health
     * - 40% mana
     * - 20% XP
     * - 5% map of the floor
     */
    public ItemType getPotionType(){
        int nb = GEN.nextInt(100);
        if (nb < 40){
            return ItemType.HEALTH_POTION;
        }
        else if(nb < 80){
            return ItemType.ELIXIR;
        }
        else {
            return ItemType.XP_BOTTLE;
        }
    }

    /**
     * Type of Potion
     * - 40% health
     * - 40% mana
     * - 20% XP
     * - 5% map of the floor
     */
    public ItemType getItemType(){
        int nb = GEN.nextInt(100);
        if (nb < 40){
            return ItemType.HEALTH_POTION;
        }
        else if(nb < 80){
            return ItemType.ELIXIR;
        }
        else if (nb < 95){
            return ItemType.XP_BOTTLE;
        }
        else {
            return ItemType.DUNGEON_MAP;
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
    public EquipmentType getEquipmentTypeInChest(){
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
    public EquipmentRarity getEquipmentRarityDroped(boolean isClassic){
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

    ////////////  Chest = true : classic / false : golden
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
     * Between 1 and 3 equipment(s) in a classic chest
     */
    public int getNumberOfEquipmentInChest(){
        return GEN.nextInt(3)+1;
    }

    /**
     * 5% of luck to have a golden key in a classic chest.
     */
    public boolean presenceOfGoldenKeyInClassicChest(){
        return (GEN.nextInt(100) < 5);
    }

    /**
     * Get the Equipment price in the merchant shop.
     */
    public int getEquipmentPrice(int level, EquipmentRarity rarity) {
        return level * (rarity.ordinal()*2+2) * 5;
    }

    /**
     * Get the item price int the merchant shop.
     */
    public int getItemPrice(int level, ItemType type) {
        switch (type) {
            case HEALTH_POTION:
            case ELIXIR:
                return Math.max(10 ,(int) Math.floor(Math.log(level*level*2) *10));
            case XP_BOTTLE:
                return Math.max(15, (int) Math.floor(Math.log(level*level*2) *15));
            case GOLD_KEY:
                return (int) Math.floor(Math.log(level*level*2) *300);
            case DUNGEON_MAP:
                return Math.max(17, (int) Math.floor(Math.log(level*level*2) *20));
            default:
                return (int) Math.floor(Math.log(level*level*2) *999);
        }
    }

    /**
     *  Between 2 and 5 Equipments in the merchant shop.
     */
    public int getNumberOfEquipMerchantShop() {
        return GEN.nextInt(4)+2;
    }

    /**
     *  Get the equipment type in the merchant shop.
     */
    public EquipmentType getEquipmentTypeInMerchantShop() {
        int type = GEN.nextInt(7);
        return EquipmentType.values()[type];
    }

    /**
     *  Get the Rarity of the Equipment in the merchant shop.
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
    * Get the rarity of an equipment on a Boss Corpse.
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

    /**
     * Between 2 and 3 items on corpse.
     * @return the number of items dropped on monster's corpse.
     */
    public int getNumberOfItemsOnCorpse() {
        return GEN.nextInt(2)+2;
    }

    /**
     * Between 0 and 2 equipments on corpse.
     * @return the number of equipments dropped on monster's corpse.
     */
    public int getNumberOfEquipmentsOnCorpse() {
        return GEN.nextInt(3);
    }

    /**
     * Between 1 and 2 equipments on Bosses' corpse
     * @return the number of items dropped on monster's corpse.
     */
    public int getNumberOfEquipmentsOnBossCorpse() {
        return GEN.nextInt(2)+1;
    }

    /**
     * Set a bonus according to the equipment and the classes of the player.
     *
     * @param equipement the equipment to set the bonus.
     * @param classe the player classes.
     */
    public void SetBonusEquipement(Equipment equipement, InGameClasses classe){
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

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public int getBonusDamage(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public int getBonusMana(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public int getBonusAgility(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public int getBonusLife(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public int getBonusArmor(int level) {
        return level;
    }

    /**
     * Get if a chest is a mimic or not when generating the room.
     * 5% of chance that a chest is a mimic
     */
    public boolean isChestMimic() {
        int nb = GEN.nextInt(100);
        return nb < 5;
    }
}
