package display;

import gameElement.GameState;
import gameElement.MiniMap;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
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
    private String[] strAll;
    private HUD hud;
    private MiniMap miniMap;

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
        gs.getGridMap().updateDisplayGridMap();
        this.strAll = new String[gs.getDungeon().getMaxRoomHeight()*4];
        updateGrid(gs.getGridMap());
        updateHUD(hud);
        updateMap(miniMap);
    }

    /**
     * Return the string to print all the element of the game
     *
     * @return String
     */
    public String toString(){
        // Renderer Game gridmap + cut minimap
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strAll.length; i++){
            sb.append(strAll[i]);
            if (i>27 && i%2 !=0 && (strAll[i-1] != "")) { //tail cut minimap * 2 (cf strAll)
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
                globalRenderer += miniMap.toStringMap();
                break;
            case INVENTORY:
                globalRenderer += "\n\n\n\n Inventory \n\n\n\n";
                break;
            case NORMAL:
                globalRenderer += renderer;
                break;
            case FIGHT:
                globalRenderer += renderer;
                //globalRenderer += infoFight;
                break;
            case LOSE:
                globalRenderer += "\n\n\n\n YOU LOOSE \n\n\n\n";
                break;
            case WIN:
                globalRenderer += "\n\n\n\n YOU WIN \n\n\n\n";
                break;
        }

        return globalRenderer;
    }

    public void display (){
        clearConsole();
        System.out.println(this.toString());
    }

    /**
     * Permit to update all the element on the print
     *
     * @param gridMap the new GriMap
     * @param miniMap the new minimap
     */
    public void updateAll(GridMap gridMap, MiniMap miniMap, HUD hud){
        updateGrid(gridMap);
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
     */
    public void updateGrid(GridMap gridMap){
        Arrays.fill(strAll,"");
        gridMap.updateDisplayGridMap();
        List<String> gridmapString = gridMap.getStrByLine();
        int i = 0;
        while ( i < gridmapString.size()){
            strAll[i*2] = gridmapString.get(i);
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
