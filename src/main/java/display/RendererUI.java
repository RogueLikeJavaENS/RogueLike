package display;

import gameElement.MiniMap;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Use to print Elements in the terminal.
 *
 * @author Antoine
 */

public class RendererUI {
    private List<String> strAll;

    public static void Renderer(GridMap gridMap, MiniMap miniMap, HUD hud){
        List<String> strLineGrid = gridMap.StrByLine();
        int widthStrGrid = strLineGrid.get(0).length();
        int heightStrGrid = strLineGrid.size();

        List<String> strLineMap = miniMap.stringByLine();
        int widthStrMap = strLineMap.get(0).length();
        int heightStrMap = strLineMap.size();

        List<String> strLineHud = hud.strByLine();
        int widthHud = strLineHud.get(0).length();
        int heightHud = strLineHud.size();



        String[] strArray = new String[Math.max(heightStrGrid+2,heightHud)*2];
        int widthLeftSide = Math.max(widthHud,widthStrGrid)+5;

        // GripMap







/*
        // GridMap plus Minimap
        while (line<strLineGrid.size()){
            sb.append(strLineGrid.get(line));
            sb.append("     ");
            sb.append(strLineMap.get(line));
            sb.append("\n");
            line++;
        }

        // HUD plus Minimap
        sb.append(" ".repeat(lengthStrGrid+5));
        sb.append(strLineMap.get(line));
        line++;
        sb.append("\n");
        sb.append(lineHud);
        sb.append(" ".repeat(lengthStrGrid-lengthHud+5));
        sb.append(strLineMap.get(line));
        line++;
        sb.append("\n");

        // Rest MiniMap
        if (line<strLineMap.size()){
            sb.append(" ".repeat(lengthStrGrid+5));
            sb.append(strLineMap.get(line));
            line++;
            sb.append("\n");
        }
        System.out.println(sb.toString());*/
    }

    private String fillStr(HUD hud, GridMap gridMap){
        return null;
    }

    public void updateHUD(HUD hud,GridMap gridMap){
        List<String> strLineGrid = gridMap.StrByLine();
        int widthStrGrid = strLineGrid.get(0).length();
        int heightStrGrid = strLineGrid.size();

        List<String> strLineHud = hud.strByLine();
        int heightHud = strLineHud.size();
        int widthHud = strLineHud.get(0).length();




        for (int i = 0; i<heightHud;i++){
            strAll.set(heightStrGrid + i, strLineHud.get(i));
        }
    }

    public void updateGrid(GridMap gridMap,HUD hud){
        List<String> strLineGrid = gridMap.StrByLine();
        int widthStrGrid = strLineGrid.get(0).length();
        int heightStrGrid = strLineGrid.size();

        List<String> strLineHud = hud.strByLine();
        int widthHud = strLineHud.get(0).length();
        int heightHud = strLineHud.size();

        for (int i=0; i<heightStrGrid; i++){
            strAll.set(i*2,strLineGrid.get(i));
        }
    }

    public void updateMap(MiniMap miniMap){
        List<String> strLineMap = miniMap.stringByLine();
        int widthStrMap = strLineMap.get(0).length();
        int heightStrMap = strLineMap.size();

        for (int i=0; i<heightStrMap; i++){
            strAll.set(i*2+1,strLineMap.get(i));
        }
    }


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
