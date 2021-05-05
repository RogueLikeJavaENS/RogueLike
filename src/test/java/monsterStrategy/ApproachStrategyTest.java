package monsterStrategy;

import classeSystem.InGameClasses;
import display.GridMap;
import entity.living.npc.monster.Monster;
import entity.living.npc.monster.MonsterFactory;
import entity.living.player.Player;
import gameElement.Room;
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
        Player player = new Player(playerPosition1,10,10,"Hero", InGameClasses.DUMMY, 1);

        System.out.println("test 1\nMonster = (" + monster.getPosition().getAbs() + ", " + monster.getPosition().getOrd() + ")");
        System.out.println("Player = (" + player.getPosition().getAbs() + ", " + player.getPosition().getOrd() + ")\n");
        assertFalse(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition2);
        System.out.println("test 2\nMonster = (" + monster.getPosition().getAbs() + ", " + monster.getPosition().getOrd() + ")");
        System.out.println("Player = (" + player.getPosition().getAbs() + ", " + player.getPosition().getOrd() + ")\n");
        assertTrue(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition3);
        System.out.println("test 3\nMonster = (" + monster.getPosition().getAbs() + ", " + monster.getPosition().getOrd() + ")");
        System.out.println("Player = (" + player.getPosition().getAbs() + ", " + player.getPosition().getOrd() + ")\n");
        assertTrue(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition4);
        monster.setPosition(new Position(5, 5));
        System.out.println("Test 4\nMonster = (" + monster.getPosition().getAbs() + ", " + monster.getPosition().getOrd() + ")");
        System.out.println("Player = (" + player.getPosition().getAbs() + ", " + player.getPosition().getOrd() + ")\n");
        assertTrue(approach.act(monster,player,gridMap));
    }


}