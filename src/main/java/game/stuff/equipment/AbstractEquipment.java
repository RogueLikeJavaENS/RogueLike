package game.stuff.equipment;

import game.stuff.AbstractStuff;

import java.util.Objects;

/**
 * This class describe an Abstract equipment
 * An equipment give bonus to to the player stats if it is equipped.
 *
 */
public abstract class AbstractEquipment extends AbstractStuff implements Equipment {

    private int bonusLife;
    private int bonusMana;
    private int bonusInitiative;
    private int bonusDamage;
    private int bonusArmor;
    private int level;

    private final EquipmentType type;
    private final EquipmentRarity rarity;

    private boolean isEquiped;

    /**
     * Create an abstract Equipment
     *
     * @param level the level of the equipment
     * @param rarity the rarity of the equipment
     * @param type the type of the equipment
     */
    public AbstractEquipment(int level, EquipmentRarity rarity, EquipmentType type) {
        super("", false, true);
        this.level = level;
        this.rarity = rarity;
        this.type = type;
        this.bonusArmor = 0;
        this.bonusDamage = 0;
        this.bonusInitiative = 0;
        this.bonusLife = 0;
        this.bonusMana = 0;
    }

    // override of the equals method
    // make the equipment equals if they have the same bonus
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractEquipment that = (AbstractEquipment) o;
        return bonusLife == that.bonusLife && bonusMana == that.bonusMana && bonusInitiative == that.bonusInitiative && bonusDamage == that.bonusDamage && bonusArmor == that.bonusArmor && level == that.level && isEquiped == that.isEquiped && type == that.type && rarity == that.rarity;
    }

    // override the hashcode according to the equals
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bonusLife, bonusMana, bonusInitiative, bonusDamage, bonusArmor, level, type, rarity, isEquiped);
    }

    /* GETTERS */
    public EquipmentRarity getRarity() { return rarity; }
    public EquipmentType getType() { return type; }
    public int getBonusDamage() { return bonusDamage; }
    public int getBonusArmor() { return bonusArmor; }
    public int getBonusAgility() { return bonusInitiative; }
    public int getBonusLife() { return bonusLife; }
    public int getBonusMana() { return bonusMana; }
    public int getLevel() { return level; }
    public boolean isEquiped() {
        return isEquiped;
    }

    /* SETTERS */
    public void setBonusArmor(int bonus) { this.bonusArmor = Math.max(bonus, 1); }
    public void setBonusLife(int bonus) { this.bonusLife = Math.max(bonus, 1); }
    public void setBonusMana(int bonus) { this.bonusMana = Math.max(bonus, 1); }
    public void setBonusInitiative(int bonus) { this.bonusInitiative = Math.max(bonus, 1); }
    public void setBonusDamage(int bonus) { this.bonusDamage = Math.max(bonus, 1); }
    public void equip() {
        this.isEquiped = true;
    }
    public void unequip() {
        this.isEquiped = false;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
