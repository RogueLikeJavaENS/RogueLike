package game.stuff.equipment.equipments;

import game.stuff.equipment.EquipmentRarity;

import static org.junit.jupiter.api.Assertions.*;

class ArmorTest {

    void testEquals(){
        Armor armor = new Armor(1, EquipmentRarity.E);
        assertTrue(armor.equals(new Armor(1,EquipmentRarity.E)));
    }
}