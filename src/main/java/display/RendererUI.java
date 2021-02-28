package display;

import gameElement.MiniMap;

/**
 * Use to print Elements in the terminal.
 *
 * @author Antoine
 */

public class RendererUI {
    public static void roomRender(GridMap gridMap) {
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
}
