package gameElement;

import utils.Direction;
import utils.Position;
import java.util.ArrayList;
import java.util.List;
import utils.Colors;
import static com.diogonunes.jcolor.Ansi.colorize;

/**
 * This class is used to see the whole dungeon as a minimap.
 * You can see the corridor between the room, the number of the room and in which room is the player
 *
 * @author Antoine Juliette
 */

public class MiniMap {
    private GameState gameState;
    private List<List<Room>> structDungeon;
    private List<List<Room>> structCutMap;
    private List<String> cutMap;
    private String map;

    public MiniMap(Dungeon dungeon, GameState gameState) {
        this.gameState = gameState;
        this.structDungeon = listToArray(dungeon);
        structCutMap = new ArrayList<>();
        updateCutMap();
        updateMap();
    }

    /**
     * Return the String which permit to print the minimap on the terminal
     *
     * @return string
     */


    public void updateCutMap(){
        Position posPlayerDungeon = gameState.getCurrentRoom().getPosition();
        int absMin;
        int ordMin;

        if (posPlayerDungeon.getAbs() == 0)
            { absMin = 0; }
        else if (posPlayerDungeon.getAbs() == structDungeon.get(0).size() -1){
            absMin = structDungeon.get(0).size() -3; }
        else{
            absMin = posPlayerDungeon.getAbs() -1; }

        if (posPlayerDungeon.getOrd() == 0)
        { ordMin = 0; }
        else if (posPlayerDungeon.getOrd() == structDungeon.size() -1)
        { ordMin = structDungeon.size() -3; }
        else
        { ordMin = posPlayerDungeon.getOrd() -1; }

        for (int abs = 0; abs<3; abs++){
            for (int ord = 0; ord<3; ord++){
                structCutMap.get(ord).set(abs, structDungeon.get(ordMin+ord).get(absMin+abs));
            }
        }
        cutMap = stringByLine(structCutMap);
    }


    public void updateMap(){
        List<String> strByLine = stringByLine(structDungeon);

        StringBuilder sb = new StringBuilder();
        for (String str : strByLine){
            sb.append(str);
            sb.append("\n");
        }
        map = sb.toString();
    }





    /**
     * With an object dungeon, return an array which contain the room at its good coordinate
     * (example: a room at the position (1,3) will be on the array at the coordinate (1,3))
     *
     * @param dungeon the dungeon to print
     * @return Room[][]
     */
    private List<List<Room>> listToArray (Dungeon dungeon){
        List<Room> roomList = dungeon.getRoomList();
        int width = dungeon.getWidth();
        int height = dungeon.getHeight();

        List<List<Room>> roomArray = new ArrayList<>();

        for (Room room : roomList){
            Position roomPos = room.getPosition();
            int abs = roomPos.getAbs();
            int ord = roomPos.getOrd();
            roomArray.get(ord).set(abs,room);
        }
        for (int line=0; line<height; line++){
            int col = 0;
            while (col <= width){
                if (col == width){

                }
                else if (roomArray.get(line).get(col) != null){
                    break;
                }
            }
        }

        return roomArray;
    }

    /**
     * Transform the array of room into a list of String which contains each line to print
     * in order to see the minimap
     *
     * @return List<String>
     */
    private List<String> stringByLine (List<List<Room>> struct){
        List<String> listOfLine= new ArrayList<>();

        for (List<Room> lineRoom : struct){
            for (int i=0; i<4; i++){
                StringBuilder sb2 = new StringBuilder();
                sb2.append("\t");
                for (Room room : lineRoom){
                    sb2.append(buildRoom(room,i));
                }
                String line = sb2.toString();
                listOfLine.add(line);
            }
        }
        return listOfLine;
    }


    /**
     * For a room and a line of the room return the string to use in order to print it.
     * If there is no room it return a string full of blank
     *
     * @param room the room to print
     * @param line the line of the room to print
     * @return String
     */

    private String buildRoom(Room room, int line) {
        String strLine = "";

        if (room == null){
            strLine = "       ";
        }
        else {
            switch (line){
                case 0:
                    if (room.getRoomAt(Direction.NORTH) != -1){ return " __#__ "; }
                    else { return " _____ "; }

                case 1:
                    return  "|     |";

                case 2:
                    StringBuilder sb = new StringBuilder();
                    if (room.getRoomAt(Direction.WEST) != -1) {
                        sb.append("#  ");
                    } else {sb.append("|  ");}

                    if (gameState.getCurrentRoom().equals(room)){sb.append(colorize("@ ", Colors.RED.textApply()));}
                    else {
                        sb.append(room.getRoomNum());
                        if (room.getRoomNum() < 10) {
                            sb.append(" ");
                        }
                    }

                    if (room.getRoomAt(Direction.EAST) != -1) {
                        sb.append(" #");
                    } else sb.append(" |");
                    return  sb.toString();

                 //case 2:{ return "|     |"; }

                case 3:{
                    if (room.getRoomAt(Direction.SOUTH) != -1){ return "|__#__|";}
                    else {return "|_____|";}
                }
            }
        }
        return strLine;
    }


    public String toStringMap() { return map; }
    public List<String> getLineCutMap() { return cutMap; }




}
