package display;

import gameElement.MiniMap;
import java.util.List;

/**
 * Use to print Elements in the terminal.
 *
 * Create an object which contains an array of all the element to print
 * Return the string to print with the method toString()
 * and allow to update just a part of the map or all the map
 *
 * @author Antoine Juliette
 */

public class RendererUI {
    private String[] strAll;

    /**
     * Constructor of the Object Renderer UI
     * Initialize the array strAll with its size
     *
     * @param gridMap : the GridMap of current room
     * @param miniMap : the Minimap of the dungeon
     * @param hud : the current HUD
     */
    public RendererUI(GridMap gridMap, MiniMap miniMap, HUD hud) {
        this.strAll = new String[Math.max(gridMap.StrByLine().size()+hud.strByLine().size(),miniMap.stringByLine().size())*2];
        updateGrid(gridMap,hud);
        updateHUD(hud,gridMap);
        updateMap(miniMap);
    }


    /**
     * In order to have String of the same size for the HUD and the GridMap fillStr
     * complete the smaller
     *
     * @param hud th current HUD
     * @param gridMap the current GridMap
     */
    private void fillStr(HUD hud, GridMap gridMap){
        List<String> strLineGrid = gridMap.StrByLine();
        int widthStrGrid = strLineGrid.get(0).length();
        int heightStrGrid = strLineGrid.size();

        List<String> strLineHud = hud.strByLine();
        int heightHud = strLineHud.size();
        int widthHud = strLineHud.get(0).length();

        if (widthHud>widthStrGrid){
            for (int i=0; i<heightStrGrid; i++){
                strLineGrid.set(i, " ".repeat(widthHud - widthStrGrid));
            }
        }
        else {
            for (int i=0; i<heightHud; i++){
                strLineHud.set(i, " ".repeat(widthStrGrid - widthHud));
            }
        }
    }

    /**
     * Return the string to print all the element of the game
     *
     * @return String
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strAll.length; i++){
            sb.append(strAll[i]);
            if (i%2 != 0) {
                sb.append("\n");
            }


        }
        return sb.toString();
    }

    /**
     * Permit to update all the element on the print
     *
     * @param gridMap the new GriMap
     * @param hud the new Hud
     * @param miniMap the new minimap
     */
    public void updateAll(GridMap gridMap, HUD hud, MiniMap miniMap){
        updateGrid(gridMap, hud);
        updateHUD(hud,gridMap);
        updateMap(miniMap);
    }

    /**
     * Permit to update only the HUD on the print
     *
     * @param hud the new Hud
     * @param gridMap the current GridMap
     */
    public void updateHUD(HUD hud,GridMap gridMap){
        fillStr(hud,gridMap);
        int heightGrid = gridMap.StrByLine().size();
        for (int i = 0; i < hud.strByLine().size(); i++){
            strAll[heightGrid*2 + i] = hud.strByLine().get(i);
        }
    }

    /**
     * Permit to update only the GridMap
     *
     * @param gridMap the new GridMap
     * @param hud the current HUD
     */
    public void updateGrid(GridMap gridMap,HUD hud){
        fillStr(hud, gridMap);
        int i = 0;
        while ( i < gridMap.StrByLine().size()){
            strAll[i*2] = gridMap.StrByLine().get(i);
            i++;
        }
        while (i*2 < strAll.length){
            strAll[i*2] = " ".repeat(gridMap.StrByLine().get(0).length());
            i++;
        }
    }

    /**
     * Permit to update only the Minimap
     *
     * @param miniMap the new Minimap
     */
    public void updateMap(MiniMap miniMap){
        int i = 0;
        while (i < miniMap.stringByLine().size()){
            strAll[i*2+1] = miniMap.stringByLine().get(i);
            i++;
        }
        while (i*2+1  < strAll.length){
            strAll[i*2+1] = " ".repeat(miniMap.stringByLine().get(0).length());
            i++;
        }
    }


    /**
     * In order to have a better visibility, it permit to clear the console
     * Use it before re-print the renderer
     *
     */
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
