package generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeedTest {

    @Test
    void getSeed() {
        Seed seed = new Seed();
        assertEquals(15, seed.getSeed().size());
    }
}