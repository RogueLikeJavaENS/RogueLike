package stuff.equipment;
import stuff.Stuff;

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
    int getBonusInitiative();
    int getBonusLife();
    int getBonusMana();
    int getLevel();
}
