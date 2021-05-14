package display;

import com.diogonunes.jcolor.Attribute;
import game.elements.GameState;
import game.elements.MiniMap;
import utils.Colors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    private final String[] gridAndMapArray;
    private static final String controls = "Controls :\n\n"
            + "| Escape : Go on the menu  \n"
            + "\nMove in the room : \n| Z : Up | Q : Left | S : Down | D : Right \n"
            + "\nShortcut Potion : \n| V : Potion Health | B : Elixir | N : XpBottle\n\n"
            + "| I : Open Inventory | M : Open Minimap | Escape : Close Inventory or Minimap \n\n"
            + "| E : Interact with the world (merchant, chest ...) \n"
            + "\nIn fight :\n"
            + "| CapsLock : Lock the player to change direction | W : Pass your turn\n"
            + "| LEFT and RIGHT : Select another spell"
            + "| A : Use a spell\n"
            + "\n\n";
    private static final String miniHelp = "Escape : Go on the menu\n\n";

    private static final String legendMinimap =
            "| "+colorize("@", Attribute.BOLD(), Colors.PINK.textApply())+ ": Player location "
            + "| "+colorize("*", Attribute.BOLD(), Colors.GREEN.textApply())+ ": Start room "
            + "| "+colorize("^", Attribute.BOLD(), Colors.BROWN.textApply())+ ": Stairs room"
            + "| "+colorize("B", Attribute.BOLD(), Colors.ORANGE.textApply())+ ": Boss room\n"
            + "| "+colorize("M", Attribute.BOLD(), Colors.RED.textApply())+ ": Monster room "
            + "| "+colorize("$", Attribute.BOLD(), Colors.YELLOW.textApply())+ ": Treasure room "
            + "| "+colorize("~", Attribute.BOLD(), Colors.CYAN.textApply())+ ": Merchant room \n";


    /**
     * Constructor of the Object Renderer UI
     * Initialize the array gridAndMapArray with its size
     *
     * @param gs : the actual GameState
     */
    public RendererUI(GameState gs) {
        this.gs = gs;

        gs.getGridMap().updateDisplayGridMap();
        this.gridAndMapArray = new String[gs.getDungeon().getMaxRoomHeight()*4];

        // Create an array for the String of GridMap and Minimap
        // size = maximum height of a room * 2 (a tile is print on 2 line) * 2 (lines of the minimap)
        // gridAndMapArray contain the gridMap on even index and the minimap on uneven index

        updateGrid(gs.getGridMap());
        updateMap(gs.getMiniMap());
    }


    /**
     * Return the string to print all the element of the game
     *
     * @return String
     */
    public String toStringGame(){
        StringBuilder globalSB = new StringBuilder();

        globalSB.append(miniHelp);

        switch (gs.getState()){
            case MAP: // Print the map of the dungeon
                globalSB.append("\t\t###################################\n" + "\t\t#              MAP                #\n")
                        .append("\t\t###################################\n");
                globalSB.append("Floor : ")
                        .append(colorize(String.valueOf(gs.getDungeon().getFloor()), Attribute.BOLD(), Colors.RED.textApply()))
                        .append("\n");
                globalSB.append(gs.getMiniMap().toStringMap());
                globalSB.append(legendMinimap);
                break;

            case INVENTORY: // Print the inventory of the dungeon plus the HUD
                globalSB.append(gs.getPlayer().getInventory().toStringInventory(gs));
                globalSB.append(gs.getDescriptor().toString());
                break;

            case NORMAL: // Print the HUD, the room and the minimap
                globalSB.append(gs.getHud().toString());
                globalSB.append(midRenderer());
                globalSB.append(gs.getDescriptor().toString());
                break;

            case FIGHT: // Print the HUD, the room, the minimap and the information about the fight
                globalSB.append(gs.getHud().toString());
                globalSB.append(midRenderer());
                globalSB.append(gs.getHud().getSpellBar());
                globalSB.append(gs.getFighting().toString());
                globalSB.append(gs.getDescriptor().toString());
                break;
            case SHOP:
                globalSB.append(gs.merchant.getMerchantInventory().toStringInventory(gs));
                globalSB.append(gs.getDescriptor().toString());
                break;
            default:
                break;
        }

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
     * @param gs gameState to update
     */
    public void updateAll(GameState gs){
        updateGrid(gs.getGridMap());
        updateMap(gs.getMiniMap());
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


    public static String getControls(){
        return controls;
    }

    /**
     * In order to have a better visibility, this function clear the console
     * Use it before re-print the renderer
     *
     */
    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                String s;
                Process p;
                try {
                    p = Runtime.getRuntime().exec("printf \\33c");
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
                    while ((s = br.readLine()) != null)
                        System.out.print(s);
                    p.waitFor();
                    p.destroy();
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    /**
     * Return the string to print the gridMap and the minimap
     *
     * @return String
     */
    private String midRenderer(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gridAndMapArray.length; i++){           // concatenate the string of gridMapAndArray which represente the gridMap and minimap
            sb.append(gridAndMapArray[i]);
            if (i>27 && i%2 !=0 && (!gridAndMapArray[i - 1].equals(""))) {  // put a \n after the uneven line without print of the minimap, "" (the strings of minimap already have a \n)
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
