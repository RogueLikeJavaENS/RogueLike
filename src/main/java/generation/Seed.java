package generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class generate a fully random 15 characters long seed in hexadecimal
 *
 * @author Luca
 */

public class Seed {
    /**
     * getter for Seed
     * @return Seed
     */
    public List<String> getSeed() {
        return Seed;
    }

    private final List<String> Seed = new ArrayList();

    /**
     * @param Seed 15 characters long in hexadecimal
     */

    /**
     * Seed generation
     */
    public Seed(){
        Random GEN = new Random();
        for (int i = 0; i < 15; i++) {
            // Seed.add(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 15 + 1)));
            Seed.add(Integer.toHexString(GEN.nextInt(16)));
        }
    }
}
