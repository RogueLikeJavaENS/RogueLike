package generation;

import generation.GraphDungeon;
import generation.Seed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphDungeonTest {
    @Test
     void getGraph(){
        GraphDungeon test = new GraphDungeon(new Seed());
        assertEquals(test.getQuantity(), test.getGraph().size());
    }
}