/**
 * Use to print Elements in the terminal.
 *
 * @author Antoine
 */

public class RendererUI {
    public static void roomRender(GridMap gridMap) {
        System.out.printf("%s", gridMap.toString());
    }
    public static void miniMapRender(MiniMap miniMap) {
        System.out.printf("%s", miniMap.toString());
    }
}
