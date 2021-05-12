package game.entity.living.npc.monster.monsterStrategy;

import game.entity.living.player.classeSystem.InGameClasses;
import display.GridMap;
import game.entity.living.npc.monster.Monster;
import game.entity.living.npc.monster.MonsterFactory;
import game.entity.living.player.Player;
import game.element.Room;
import generation.RoomFactory;
import generation.RoomType;
import generation.Seed;
import org.junit.jupiter.api.Test;
import utils.Position;
import static org.junit.jupiter.api.Assertions.*;

class ApproachStrategyTest {

    @Test
    void test() {
        Condition condition = (Monster monster, Player player) -> true;
        Strategy approach = new ApproachStrategy(condition,null);
        RoomFactory roomFactory = new RoomFactory(15,15,1,2);
        int[] tab = {1,2,3,4,5,6};
        Room room = roomFactory.getRoom(new Seed(), RoomType.MONSTER,1,tab);
        GridMap gridMap = new GridMap(room);
        MonsterFactory monsterFactory = new MonsterFactory(1);
        Position monsterPosition = new Position(1,2);
        Position playerPosition1 = new Position(2,2);
        Position playerPosition2 = new Position(4,4);
        Position playerPosition3 = new Position(7,8);
        Position playerPosition4 = new Position(1,3);
        Monster monster = monsterFactory.getMonster(0,monsterPosition);
        Player player = new Player(playerPosition1,"Hero", InGameClasses.DUMMY, 1);

        assertFalse(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition2);
        assertTrue(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition3);
        assertTrue(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition4);
        monster.setPosition(new Position(5, 5));
        //assertTrue(approach.act(monster,player,gridMap));
    }


}