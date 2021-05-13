package game.stuff.equipment;
import game.stuff.Stuff;

/**
 * The interface of the equipment
 */
public interface Equipment extends Stuff {
    boolean isEquiped();
    boolean equals(Object o);
    void equip();
    void unequip();
    void setLevel(int level);
    EquipmentRarity getRarity();
    EquipmentType getType();
    int getBonusDamage();
    int getBonusArmor();
    int getBonusAgility();
    int getBonusLife();
    int getBonusMana();
    int getLevel();
    void setBonusDamage(int value);
    void setBonusMana(int value);
    void setBonusInitiative(int value);
    void setBonusArmor(int value);
    void setBonusLife(int value);
}
