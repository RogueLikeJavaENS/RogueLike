package stuff.equipment;
import stuff.Stuff;

public interface Equipment extends Stuff {
    boolean isEquiped();
    void equip();
    void unequip();
    EquipmentRarity getRarity();
    EquipmentType getType();
    int getBonusDamage();
    int getBonusArmor();
    int getBonusInitiative();
    int getBonusLife();
    int getBonusMana();
    int getLevel();
}
