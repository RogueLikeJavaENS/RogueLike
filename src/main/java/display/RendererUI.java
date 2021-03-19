package display;

import gameElement.MiniMap;

import java.io.Console;
import java.io.IOException;

/**
 * Use to print Elements in the terminal.
 *
 * @author Antoine
 */

public class RendererUI {
    public static void roomRender(GridMap gridMap) {
        clearConsole();
        System.out.println("Game Window :");
        System.out.printf("%s\n", gridMap.toString());
    }
    public static void miniMapRender(MiniMap miniMap) {
        System.out.println("Minimap :");
        System.out.printf("%s\n", miniMap.toString());
    }

    public static void hudRender(HUD hud) {
        System.out.println("HUD :");
        System.out.printf("%s\n", hud.toString());
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
