package display;

import gameElement.GameState;
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
    GameState gs;
    private final String[] strAll;
    private HUD hud;
    private final MiniMap miniMap;

    /**
     * Constructor of the Object Renderer UI
     * Initialize the array strAll with its size
     *
     * @param gs : the actual GameState
     * @param miniMap : the Minimap of the dungeon
     * @param hud : the current HUD
     */
    public RendererUI(GameState gs, MiniMap miniMap, HUD hud) {
        this.hud = hud;
        this.gs = gs;
        this.miniMap = miniMap;
        this.strAll = new String[Math.max(gs.getGridMap().StrByLine().size(),miniMap.getLineCutMap().size())*2];
        updateGrid(gs.getGridMap(),hud);
        updateHUD(hud);
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
        // Renderer Game
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strAll.length; i++){
            sb.append(strAll[i]);
            if (i%2 != 0) {
                sb.append("\n");
            }
        }

        String renderer = sb.toString();

        // Control and HUD
        String controls = "Controls :\n"
                    + "| Escape : Exit the game | \n"
                    + "| Z : Up | Q : Left | S : Down | D : Right \n"
                    + "| I : Inventory | M : Minimap | Escape with the same button\n"
                    + "\n";
        String hudString = hud.toString();

        // Global Renderer
        String globalRenderer = controls+hudString;

        switch (gs.getState()){
            case MAP:
                //globalRenderer += "\n\n\n\n Minimap \n\n\n\n";
                globalRenderer += miniMap.toStringMap();
                break;
            case INVENTORY:
                globalRenderer += "\n\n\n\n Inventory \n\n\n\n";
                break;
            case NORMAL:
                globalRenderer += renderer;
                break;
            case FIGHT:
                String spellBar = hud.getSpellBar();
                globalRenderer += renderer + spellBar;
                //globalRenderer += infoFight;
                break;
            case LOSE:
                globalRenderer += "\n\n\n\n YOU LOSE \n\n\n\n";
                break;
            case WIN:
                globalRenderer += "\n\n\n\n YOU WIN \n\n\n\n";
                break;
        }

        return globalRenderer;
    }

    public void display (){
        clearConsole();
        System.out.println(this); //appel implicite à toString() dans le print
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
        updateHUD(hud);
        updateMap(miniMap);
    }

    public void updateHUD(HUD hud){
        this.hud = hud;
    }

    /**
     * Permit to update only the GridMap
     *
     * @param gridMap the new GridMap
     * @param hud the current HUD
     */
    public void updateGrid(GridMap gridMap,HUD hud){
        List<String> gridmapString = gridMap.StrByLine();
        fillStr(hud, gridMap);
        int i = 0;
        while ( i < gridmapString.size()){
            strAll[i*2] = gridmapString.get(i);
            i++;
        }
        while (i*2 < strAll.length){
            strAll[i*2] = " ".repeat(gridmapString.get(0).length());
            i++;
        }
    }

    /**
     * Permit to update only the Minimap
     *
     * @param miniMap the new Minimap
     */
    public void updateMap(MiniMap miniMap){
        miniMap.updateCutMap();
        List<String> minimapString = miniMap.getLineCutMap();
        int i = 0;
        while (i < minimapString.size()){
            strAll[i*2+1] = minimapString.get(i);
            i++;
        }
        while (i*2+1  < strAll.length){
            strAll[i*2+1] = " ".repeat(minimapString.get(0).length());
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
