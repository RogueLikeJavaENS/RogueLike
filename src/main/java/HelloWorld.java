import java.util.Arrays;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(colorize("This text will be yellow on magenta", YELLOW_TEXT(), MAGENTA_BACK()));
        int[] t = new int[3];
        Arrays.fill(t, -1);
        System.out.println(t[0]);
    }
}
