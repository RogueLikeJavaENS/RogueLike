package stuff.equipment;

import stuff.AbstractStuff;

import java.util.Objects;

public abstract class AbstractEquipment extends AbstractStuff implements Equipment {

    private int bonusLife;
    private int bonusMana;
    private int bonusInitiative;
    private int bonusDamage;
    private int bonusArmor;
    private final int level;

    private final EquipmentType type;
    private final EquipmentRarity rarity;

    private boolean isEquiped;

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
        setRarityName();
    }

    public boolean isEquiped() {
        return isEquiped;
    }

    public void equip() {
        this.isEquiped = true;
    }

    public void unequip() {
        this.isEquiped = false;
    }

    protected void setRarityName() {
        super.setName("test");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractEquipment that = (AbstractEquipment) o;
        return bonusLife == that.bonusLife && bonusMana == that.bonusMana && bonusInitiative == that.bonusInitiative && bonusDamage == that.bonusDamage && bonusArmor == that.bonusArmor && level == that.level && isEquiped == that.isEquiped && type == that.type && rarity == that.rarity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bonusLife, bonusMana, bonusInitiative, bonusDamage, bonusArmor, level, type, rarity, isEquiped);
    }

    /* GETTERS */
    public EquipmentRarity getRarity() { return rarity; }
    public EquipmentType getType() { return type; }
    public int getBonusDamage() { return bonusDamage; }
    public int getBonusArmor() { return bonusArmor; }
    public int getBonusInitiative() { return bonusInitiative; }
    public int getBonusLife() { return bonusLife; }
    public int getBonusMana() { return bonusMana; }
    public int getLevel() { return level; }

    /* SETTERS */

    protected void setBonusArmor(int bonusArmor) { this.bonusArmor = bonusArmor; }
    protected void setBonusLife(int bonus) { this.bonusLife = bonus; }
    protected void setBonusMana(int bonus) { this.bonusMana = bonus; }
    protected void setBonusInitiative(int bonus) { this.bonusInitiative = bonus; }
    protected void setBonusDamage(int bonus) { this.bonusDamage = bonus; }
}
