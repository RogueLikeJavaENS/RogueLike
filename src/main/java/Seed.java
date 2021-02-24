import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Seed {
    public List getSeed() {
        return Seed;
    }

    private final List Seed = new ArrayList();

    public Seed(){
        for (int i = 0; i < 16; i++) {
            Seed.add(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 10 + 1)));
        }
    }
}
