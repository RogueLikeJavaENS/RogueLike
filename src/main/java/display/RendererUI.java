package display;

import com.sun.jna.platform.win32.IPHlpAPI;
import gameElement.GameState;
import gameElement.MiniMap;
import jdk.swing.interop.SwingInterOpUtils;
import utils.Colors;
import utils.State;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static com.diogonunes.jcolor.Ansi.colorize;

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
    private HUD hud;

    private String[] gridAndMapArray;
    private static final String help = "Controls :\n"
            + "| Escape : Exit the game | H : Hide Controls  \n"
            + "| Z : Up | Q : Left | S : Down | D : Right \n"
            + "| I : Inventory | M : Minimap | Escape with the same button\n"
            + "\n";
    private static final String miniHelp = "Controls:\nH : Show All Controls\n\n";


    /**
     * Constructor of the Object Renderer UI
     * Initialize the array gridAndMapArray with its size
     *
     * @param gs : the actual GameState
     * @param hud : the current HUD
     */
    public RendererUI(GameState gs, HUD hud) {
        this.hud = hud;
        this.gs = gs;

        gs.getGridMap().updateDisplayGridMap();
        this.gridAndMapArray = new String[gs.getDungeon().getMaxRoomHeight()*4];

        // Create an array for the String of GridMap and Minimap
        // size = maximum height of a room * 2 (a tile is print on 2 line) * 2 (lines of the minimap)
        // gridAndMapArray contain the gridMap on even index and the minimap on uneven index

        updateGrid(gs.getGridMap());
        updateHUD(hud);
        updateMap(gs.getMiniMap());
    }


    /**
     * Return the string to print the gridmap and the minimap
     *
      * @return String
     */
    private String midRenderer(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridAndMapArray.length; i++){           // concatenate the string of gridMapAndArray which represente the gridMap and minimap
            sb.append(gridAndMapArray[i]);
            if (i>27 && i%2 !=0 && (gridAndMapArray[i-1] != "")) {  // put a \n after the uneven line without print of the minimap, "" (the strings of minimap already have a \n)
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Return the string to print all the element of the game
     *
     * @return String
     */
    public String toStringGame(){
        StringBuilder globalSB = new StringBuilder();

        // Controls always print
        if (gs.getHelp()){
            globalSB.append(help);
        }
        else {
            globalSB.append(miniHelp);
        }


        switch (gs.getState()){
            case MAP: // Print the map of the dungeon
                globalSB.append("\t\t###################################\n" +
                                "\t\t#             MINIMAP             #\n" +
                                "\t\t###################################\n");
                globalSB.append(gs.getMiniMap().toStringMap());
                break;

            case INVENTORY: // Print the inventory of the dungeon plus the HUD
                globalSB.append(hud.toString());
                globalSB.append("\t\t###################################\n" +
                                "\t\t#            INVENTORY            #\n" +
                                "\t\t###################################\n");
                break;

            case NORMAL: // Print the HUD, the room and the minimap
                globalSB.append(hud.toString());
                globalSB.append(midRenderer());
                break;

            case FIGHT: // Print the HUD, the room, the minimap and the information about the fight
                globalSB.append(hud.toString());
                globalSB.append(midRenderer());
                globalSB.append(hud.getSpellBar());
                globalSB.append(gs.getFighting().toString());

                break;
            default:
                break;
        }
        globalSB.append(gs.getDescriptor().toString());
        return globalSB.toString();
    }

    /**
     * Clear the console and print the game
     *
     */
    public void display(){
        String string = toStringGame();
        clearConsole();
        System.out.println(string);
    }

    /**
     * Update all the element of the game
     *
     * @param gs gameState
     */
    public void updateAll(GameState gs, HUD hud){
        updateGrid(gs.getGridMap());
        updateHUD(hud);
        updateMap(gs.getMiniMap());
    }

    /**
     * Update the HUD
     *
     * @param hud
     */
    public void updateHUD(HUD hud){
        this.hud = hud;
    }

    /**
     * Update only the GridMap
     *
     * @param gridMap the new GridMap
     */
    public void updateGrid(GridMap gridMap){
        gridMap.updateDisplayGridMap();                         // update of the Strings of Gridmap
        List<String> gridMapString = gridMap.getStrByLine();    // get the List of Strings
        int i = 0;
        while ( i < gridMapString.size()){                      // put the Strings on gridAndMapArray
            gridAndMapArray[i*2] = gridMapString.get(i);        // only on even places (uneven are for the minimap)
            i++;
        }
        while (i*2 < gridAndMapArray.length){                     // fill the rest of gridAndMapArray with ""
            gridAndMapArray[i*2] = "";
            i++;
        }
    }

    /**
     * Permit to update only the Minimap
     *
     * @param miniMap the new Minimap
     */
    public void updateMap(MiniMap miniMap){
        miniMap.updateCutMap();                                         // update of the Map
        List<String> minimapString = miniMap.getLineCutMap();           //  get the list of String
        int i = 0;
        while (i < minimapString.size()){                               // put the String on gridAndMapArray
            gridAndMapArray[i*2+1] = minimapString.get(i);              // only on uneven places (even are for the gridMap)
            i++;
        }
        while (i*2+1  < gridAndMapArray.length){                        // fill the rest of gridAndMapArray with ""
            gridAndMapArray[i*2+1] = "";
            i++;
        }
    }


    public void winEnd() {
        System.out.println("\n\n\n\n\n\n");
        System.out.println("#######################################################");
        System.out.println("#                      YOU WIN !                      #");
        System.out.println("#######################################################");
    }

    public void loseEnd() {
        System.out.println("\n\n\n\n\n\n");
        System.out.println("#######################################################");
        System.out.println("#                      YOU LOSE !                     #");
        System.out.println("#######################################################");
    }

    /**
     * In order to have a better visibility, this function clear the console
     * Use it before re-print the renderer
     *
     */
    public void clearConsole() {
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
