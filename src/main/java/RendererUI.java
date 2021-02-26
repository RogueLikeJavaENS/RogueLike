/**
 * Use to print Elements in the terminal.
 *
 * @author Antoine
 */

public class RendererUI {
    public static void render(GridMap gridMap, HUD hud) {
        System.out.printf("%s/n/n%s", gridMap.toString(), hud.toString());
    }
}
