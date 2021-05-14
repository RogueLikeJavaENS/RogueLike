package game;

import game.elements.GameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game.stuff.item.ItemType;

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
    void getPotionType() {
        ItemType itemType = GameRule.getItemType();
        //assertTrue(a==0 || a == 1 || a == 2 || a == 3);
    }
}