package game.elements;

import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterStats;
import game.entity.living.npc.monster.MonsterType;
import game.entity.living.player.classeSystem.InGameClasses;
import game.entity.living.player.spell.Spell;
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

    /*/////////////////// GOLD AND PRICE/////////////////////////////////////////*/
    /**
     * between 8 and 15 gold by room, 1% chance of 50 golds.
     */
    public static int getNumberOfGoldInTreasureRoom(){
        int nb = GEN.nextInt(100);
        if (nb == 0){
            return 50;
        }
        else {
        return GEN.nextInt(8)+8;
        }
    }

    /**
     * Between 0 and 5 gold in a classic chest
     * Between 30 and 50 gold in a golden chest
     */
    public static int  getNumberOfGoldInChest(boolean isClassic){
        if (isClassic){
            return GEN.nextInt(6);
        }
        else {
            return GEN.nextInt(21)+30;
        }
    }

    /**
     * Get the item price int the merchant shop.
     */
    public static int getItemPrice(int level, ItemType type) {
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
     * Get the Equipment price in the merchant shop.
     */
    public static int getEquipmentPrice(int level, EquipmentRarity rarity) {
        return level * (rarity.ordinal()*2+2) * 5;
    }
    /*/////////////////// GOLD AND PRICE/////////////////////////////////////////*/







    /*/////////////////////////////   ITEMS  /////////////////////////////////////*/
    /**
     * Type of Potion
     * - 40% health
     * - 40% mana
     * - 20% XP
     * - 5% map of the floor
     */
    public static ItemType getPotionType(){
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
    public static ItemType getItemType(){
        int nb = GEN.nextInt(100);
        if (nb < 40){
            return ItemType.HEALTH_POTION;
        }
        else if(nb < 80){
            return ItemType.ELIXIR;
        }
        else if (nb < 97){
            return ItemType.XP_BOTTLE;
        }
        else {
            return ItemType.DUNGEON_MAP;
        }
    }

    /**
     * between 3 and 6 potions in Treasure Room.
     */
    public static int getNumberOfPotionInTreasureRoom(){
        return GEN.nextInt(4)+3;
    }

    /**
     * Between 1 and 3 potion in a classic chest
     * Between 10 and 15 potion in a golden chest
     */
    public static int getNumberOfPotionInChest(boolean isClassic) {
        if (isClassic) {
            return GEN.nextInt(3) + 1;
        }
        else {
            return GEN.nextInt(6)+10;
        }
    }

    /**
     * Between 1 and 3 items on corpse.
     * @return the number of items dropped on monster's corpse.
     */
    public static int getNumberOfItemsOnCorpse() {
        return GEN.nextInt(3)+1;
    }
    /*/////////////////////////////   ITEMS  /////////////////////////////////////*/







    /*/////////////////////////  EQUIPMENTS /////////////////////////////////*/
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
    public static EquipmentType getEquipmentTypeInChest(){
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
    public static EquipmentRarity getEquipmentRarityDroped(boolean isClassic){
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

    /**
     * Get the rarity of Equipment in start chest
     *
     * - E 70%
     * - D 30%
     */
    public static EquipmentRarity getEquipmentRarityDropedStartChest(){
        int nb = GEN.nextInt(100);
        if (nb < 70) {
            return EquipmentRarity.E;
        } else return EquipmentRarity.D;
    }

    /**
     *  Get the equipment type in the merchant shop.
     */
    public static EquipmentType getEquipmentTypeInMerchantShop() {
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
    public static EquipmentRarity getRarityEquipmentInMerchantShop() {
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

    /**
    * Get the rarity of an equipment on a Boss Corpse.
    * L 15 %
    * S 35 %
    * A 50 %
    */
    public static EquipmentRarity getRarityOnBossCorpse() {
        int nb = GEN.nextInt(100);
        if (nb < 50) {
            return EquipmentRarity.A;
        } else if (nb<85) {
            return EquipmentRarity.S;
        } else return EquipmentRarity.L;
    }

    /**
     * Between 1 and 3 equipment(s) in a classic chest
     */
    public static int getNumberOfEquipmentInChest(){
        return GEN.nextInt(3)+1;
    }

    /**
     * Between 3 and 5 equipment(s) in a classic chest
     */
    public static int getNumberOfEquipmentInStartChest(){
        return GEN.nextInt(3)+3;
    }

    /**
     *  Between 2 and 5 Equipments in the merchant shop.
     */
    public static int getNumberOfEquipMerchantShop() {
        return GEN.nextInt(4)+2;
    }

    /**
     * Between 0 and 1 equipments on corpse.
     * @return the number of equipments dropped on monster's corpse.
     */
    public static int getNumberOfEquipmentsOnCorpse() {
        return GEN.nextInt(2);
    }

    /**
     * Between 1 and 2 equipments on Bosses' corpse
     * @return the number of items dropped on monster's corpse.
     */
    public static int getNumberOfEquipmentsOnBossCorpse() {
        return GEN.nextInt(2)+1;
    }
    /*/////////////////////////  EQUIPMENTS /////////////////////////////////*/







    /*////////////////////////////  GENERATION ////////////////////////////////*/
    /**
     * 5% of luck to have a golden key in a classic chest.
     */
    public static boolean presenceOfGoldenKeyInClassicChest(){
        return (GEN.nextInt(100) < 5);
    }

    /**
     * Get if a chest is a mimic or not when generating the room.
     * 5% of chance that a chest is a mimic
     */
    public static boolean isChestMimic() {
        int nb = GEN.nextInt(100);
        return nb < 5;
    }

    /**
     * Get the number of hole when generating a room.
     * 5 to 10 hole in a room
     */
    public static int numberOfHoleAtGeneration(){
        return GEN.nextInt(5)+5;
    }

    /**
     * Get the number of spike when generating a room.
     * 10 to 15 spike in a room
     */
    public static int numberOfSpikeAtGeneration(){
        return GEN.nextInt(5)+10;
    }

    /**
     * When generating get if there is a chest in the room.
     * 30% of chance to have a chest in a monster room.
     */
    public static boolean presenceOfClassicChestOnMonsterRoom(){
        int nb = GEN.nextInt(100);
        return nb < 20;
    }

    /**
     * Get the the monster type to add when generating the room.
     * Equal chance of every mobs.
     */
    public static int getMonsterType(){
        return GEN.nextInt(6);
    }
    /*////////////////////////////  GENERATION ////////////////////////////////*/







    ///////////////////////// Bonus of equipment ///////////////////////////////////
    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public static int getBonusDamage(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public static int getBonusMana(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public static int getBonusAgility(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public static int getBonusLife(int level) {
        return level;
    }

    /**
     * Get the Bonus Damage According to the level of the player.
     * @param level level of the player
     * @return the bonus to add
     */
    public static int getBonusArmor(int level) {
        return level;
    }
    ///////////////////////// Bonus of equipment ///////////////////////////////////







    ////////////////////////////////// STATS //////////////////////////////////////

    /**
     * Sets a bonus according to the equipment and the classes of the player.
     *
     * @param equipement the equipment to set the bonus.
     * @param classe the player classes.
     */
    public static void setBonusEquipement(Equipment equipement, InGameClasses classe){
        int value = equipement.getLevel()+equipement.getRarity().ordinal();
        switch (equipement.getType()){
            case WEAPON:
                switch (classe){
                    case RANGER:
                        equipement.setBonusDamage(value+1);
                    case WARRIOR:
                        equipement.setBonusDamage(value+2);
                        break;
                    case MAGE:
                        equipement.setBonusDamage(value);
                        equipement.setBonusMana(value*10);
                        break;
                }
                break;
            case SHIELD:
                switch (classe){
                    case RANGER:
                        equipement.setBonusInitiative(Math.max(value/2, 1));
                        equipement.setBonusArmor(value+1);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value*5);
                        equipement.setBonusArmor(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value+2);
                        break;
                }
                break;
            case ARMOR:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value+1);
                        equipement.setBonusInitiative(Math.max(value/4, 1));
                        equipement.setBonusMana(value);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value*5);
                        equipement.setBonusArmor(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusArmor(value+3);
                        equipement.setBonusLife(value*5);
                        break;
                }
                break;
            case BOOT:
                switch (classe){
                    case RANGER:
                    case MAGE:
                        equipement.setBonusInitiative(Math.max(value/4, 1));
                        equipement.setBonusArmor(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusLife(value);
                        equipement.setBonusArmor(value);
                        break;
                }
                break;
            case GLOVE:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value+1);
                        break;
                    case MAGE:
                        equipement.setBonusMana(value);
                        equipement.setBonusArmor(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusDamage(value);
                        equipement.setBonusArmor(value+2);
                        break;
                }
                break;
            case PANT:
                switch (classe) {
                    case RANGER:
                        equipement.setBonusArmor(value+1);
                        break;
                    case MAGE:
                        equipement.setBonusArmor(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusLife(value*3);
                        equipement.setBonusArmor(value+2);
                        break;
                }
                break;
            case HELMET:
                switch (classe){
                    case RANGER:
                        equipement.setBonusArmor(value);
                        break;
                    case MAGE:
                        equipement.setBonusArmor(value+1);
                        equipement.setBonusInitiative(value);
                        break;
                    case WARRIOR:
                        equipement.setBonusLife(value*2);
                        equipement.setBonusArmor(value+1);
                        break;
                }
                break;
        }
    }

    public static void setMonstersStats(Monster monster, MonsterType monsterType) {
        int lifePoint, agility, damage, armor, money, xp;
        int basicHP, hpModifier;
        int level = monster.getMonsterStats().getLevel();
        switch (monsterType) {
            case GOBLIN:
                basicHP = 35;
                hpModifier = 4;

                agility = 3+level;
                damage = 2+level;
                armor = level;
                money = 5+level;
                xp = 5*level;
                break;
            case SKELETON:
                basicHP = 50;
                hpModifier = 5;


                agility = 1+level;
                damage = 2+level;
                armor = level;
                money = 3+level;
                xp = 5*level;
                break;
            case BAT:
                basicHP = 20;
                hpModifier = 2;

                agility = 3+level;
                damage = 1+(level/2);
                armor = level;
                money = 1+level;
                xp = 5*level;
                break;
            case ZOMBIE:
                basicHP = 40;
                hpModifier = 4;

                agility = 1+level;
                damage = 2+level;
                armor = level;
                money = 2+level;
                xp = 5*level;
                break;
            case ORC:
                basicHP = 60;
                hpModifier = 6;

                agility = 1+level;
                damage = 2+level;
                armor = level;
                money = 5+level;
                xp = 7*level;
                break;
            case VAMPIRE:
                basicHP = 45;
                hpModifier = 15;

                agility = 2+level;
                damage = 2+level;
                armor = level;
                money = 3+level;
                xp = 6*20;
                break;
            case MIMIC:
                basicHP = 100;
                hpModifier = 5;

                agility = 1+level;
                damage = 2+level;
                armor = 1+level;
                money = 20+level;
                xp = 7*level;
                break;
            case MERCHANT:
                basicHP = 150;
                hpModifier = 7;

                agility = 1+level;
                damage = 15+level;
                armor = 1+level;
                money = 200+level;
                xp = 15*level;
                break;
            default:
                basicHP = 1;
                hpModifier = 1;

                agility = 1;
                damage = 1;
                armor = 1;
                money = 1;
                xp = 0;
        }

        lifePoint = basicHP+(hpModifier*level);

        MonsterStats stats = monster.getMonsterStats();
        stats.setLifePoint(lifePoint);
        stats.setAgility(agility);
        stats.setDamage(damage);
        stats.setArmor(armor);
        stats.setMoney(money);
        stats.setXpWorth(xp);
    }

    public static void setSpellStats(Spell spell, InGameClasses inGameClasses) {
        int level = spell.getLevel();
        double damageMult = 0;
        int manaCost = 0, damage = 0;
        switch (inGameClasses) {
            case RANGER:
                switch (level) {
                    case 0: // BasicAttack
                        damageMult = 1.0;
                        manaCost = 0;
                        damage = 12;
                        break;
                    case 1: // Trap
                        damageMult = 0;
                        manaCost = 15;
                        damage = 0;
                        break;
                    case 2: // FireArrow
                        damageMult = 1.2;
                        manaCost = 15;
                        damage = 11;
                        break;
                    case 3: // Dash
                        damageMult = 0;
                        manaCost = 10;
                        damage = 0;
                        break;
                    case 4: // Heal
                        damageMult = 0;
                        manaCost = 16;
                        damage = 0;
                        break;
                    case 5: // Sniper
                        damageMult = 1.1;
                        manaCost = 25;
                        damage = 12;
                        break;
                    case 6: // MultiShot
                        damageMult = 2.0;
                        manaCost = 70;
                        damage = 50;
                }
                break;
            case WARRIOR:
                switch (level) {
                    case 0: // BasicAttack
                        damageMult = 1.0;
                        manaCost = 0;
                        damage = 11;
                        break;
                    case 1: // StrongPunch
                        damageMult = 1.4;
                        manaCost = 10;
                        damage = 15;
                        break;
                    case 2: // TourbiLOL
                        damageMult = 1.2;
                        manaCost = 15;
                        damage = 15;
                        break;
                    case 3: // Charge
                        damageMult = 1.2;
                        manaCost = 25;
                        damage = 15;
                        break;
                    case 4: // IronSkin
                        damageMult = 0;
                        manaCost = 15;
                        damage = 0;
                        break;
                    case 5: // ThrowAxe
                        damageMult = 1.5;
                        manaCost = 25;
                        damage = 20;
                        break;
                    case 6: // EarthQuake
                        damageMult = 1.5;
                        manaCost = 70;
                        damage = 60;
                }
                break;
            case MAGE:
                switch (level) {
                    case 0: // BasicAttack
                        damageMult = 1.0;
                        manaCost = 0;
                        damage = 9;
                        break;
                    case 1: // FireAura
                        damageMult = 1.4;
                        manaCost = 10;
                        damage = 15;
                        break;
                    case 2: // FireBall
                        damageMult = 1.6;
                        manaCost = 15;
                        damage = 16;
                        break;
                    case 3: // Teleport
                        damageMult = 0;
                        manaCost = 25;
                        damage = 0;
                        break;
                    case 4: // Heal
                        damageMult = 0.0;
                        manaCost = 15;
                        damage = 0;
                        break;
                    case 5: // FireBolt
                        damageMult = 1.5;
                        manaCost = 30;
                        damage = 20;
                        break;
                    case 6: // HellWave
                        damageMult = 1.5;
                        manaCost = 50;
                        damage = 60;
                }
                break;
            default:
                damageMult = 0;
                manaCost = 0;
                damage = 0;
        }
        spell.setDamageMult(damageMult);
        spell.setManaCost(manaCost);
        spell.setDamage(damage);
    }
    ////////////////////////////////// STATS //////////////////////////////////////
}
