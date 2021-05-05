package monsterStrategy;

import display.GridMap;
import entity.living.npc.monster.Monster;
import entity.living.npc.monster.MonsterFactory;
import entity.living.player.Player;
import gameElement.Room;
import generation.RoomFactory;
import generation.RoomType;
import generation.Seed;
import org.junit.jupiter.api.Test;
import utils.Classe;
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
        Monster monster = monsterFactory.getMonster(0,monsterPosition);
        Player player = new Player(playerPosition1,10,10,"Hero",1, Classe.MAGE);

        assertFalse(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition2);
        assertTrue(approach.act(monster,player,gridMap));
        player.setPosition(playerPosition3);
        assertTrue(approach.act(monster,player,gridMap));

    }


}