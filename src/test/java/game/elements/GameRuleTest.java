package game.elements;

import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterFactory;
import game.stuff.equipment.EquipmentRarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class GameRuleTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    void getNumberOfPotionInTreasureRoom() {
        int a = GameRule.getNumberOfPotionInTreasureRoom();
        assertTrue(a<=6);
    }


    @Test
    void setMonstersStats() {
        Position position = new Position(5,5);
        MonsterFactory mf = new MonsterFactory(5);
        Monster monster = mf.getMonster(1, position);
        int level = monster.getMonsterStats().getLevel();
        assertTrue(level >= 13 && level <= 15);
        assertEquals(1 + level, monster.getMonsterStats().getAgilityTotal());
        assertEquals(2 + level, monster.getMonsterStats().getDamageTotal());
        assertEquals(level, monster.getMonsterStats().getArmorTotal());
        assertEquals(3 + level, monster.getMonsterStats().getMoneyCount());
    }

    @Test
    void getEquipmentPriceTest() {
        int price = GameRule.getEquipmentPrice(5, EquipmentRarity.E);
        assertEquals(50, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.D);
        assertEquals(100, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.C);
        assertEquals(150, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.B);
        assertEquals(200, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.A);
        assertEquals(250, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.S);
        assertEquals(300, price);
        price = GameRule.getEquipmentPrice(5, EquipmentRarity.L);
        assertEquals(350, price);
    }

}