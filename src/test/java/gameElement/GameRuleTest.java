package gameElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRuleTest {
    GameRule gameRule;

    @BeforeEach
    public void setUp() {
        gameRule = new GameRule();
    }

    @Test
    void getNumberOfPotionInTreasureRoom() {
        int a = gameRule.getNumberOfPotionInTreasureRoom();
        assertTrue(a<=6);
    }

    @Test
    void getPotionNumber() {
        int a = gameRule.getPotionNumber();
        assertTrue(a<=4);
    }

    @Test
    void getPotionType() {
        int a = gameRule.getPotionType();
        assertTrue(a==0 || a == 1 || a == 2 || a == 3);
    }

    @Test
    void getNumberOfGoldInTreasureRoom() {
        int a = gameRule.getNumberOfGoldInTreasureRoom();
        assertTrue(a<=15);
    }
}