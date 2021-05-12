package game.entity.living.spell;

import game.entity.living.spell.spells.FireBall;
import org.junit.jupiter.api.Test;
import utils.Direction;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class FireBallTest {
    FireBall fireBall = new FireBall();
    Position entityPos = new Position(0, 0);

    /**
     * Tests if the top left corner of a fire ball range is correctly corrected when facing towards the limits of the map
     * and less than 3 tiles away from them
     */
    @Test
    void setTopLeftCorner00() {
        fireBall.setTopLeftCorner(entityPos, Direction.NORTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.WEST);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.SOUTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.EAST);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
    }

    @Test
    void setTopLeftCorner75() {
        entityPos.setOrd(7);
        entityPos.setAbs(5);
        fireBall.setTopLeftCorner(entityPos, Direction.NORTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd()-3, fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.WEST);
        assertEquals(entityPos.getAbs()-3, fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.SOUTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
        fireBall.setTopLeftCorner(entityPos, Direction.EAST);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getTopLeftCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getTopLeftCorner().getOrd());
    }

    @Test
    void setBottomRightCorner00() {
        fireBall.setBottomRightCorner(entityPos, Direction.NORTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.WEST);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.SOUTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd()+3, fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.EAST);
        assertEquals(entityPos.getAbs()+3, fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
    }

    @Test
    void setBottomRightCorner75() {
        fireBall.setBottomRightCorner(entityPos, Direction.NORTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.WEST);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.SOUTH);
        assertEquals(entityPos.getAbs(), fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd()+3, fireBall.getRange().getBottomRightCorner().getOrd());
        fireBall.setBottomRightCorner(entityPos, Direction.EAST);
        assertEquals(entityPos.getAbs()+3, fireBall.getRange().getBottomRightCorner().getAbs());
        assertEquals(entityPos.getOrd(), fireBall.getRange().getBottomRightCorner().getOrd());
    }
}