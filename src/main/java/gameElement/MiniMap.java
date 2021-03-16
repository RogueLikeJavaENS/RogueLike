package gameElement;

import utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to see the whole dungeon as a minimap.
 * You can see the corridor between the room, the number of the room and in wich room is the player
 *
 * @author Antoine Juliette
 */

public class MiniMap {
    Dungeon dungeon;


    public MiniMap(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Return the String which permit to print the minimap on the terminal
     *
     * @return string
     */
    public String toString() {
        List<String> strByLine = stringByLine(listToArray(dungeon));

        StringBuilder sb = new StringBuilder();
        for (String str : strByLine){
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * With an object dungeon, return an array which contain the room at its good coordinate
     * (example: a room at the position (1,3) will be on the array at the coordinate (1,3))
     *
     * @param dungeon
     * @return Room[][]
     */
    private Room[][] listToArray (Dungeon dungeon){
        List<Room> roomList = dungeon.getRoomList();
        int width = dungeon.getWidth();
        int height = dungeon.getHeight();

        Room[][] roomArray = new Room[height][width];

        for (Room room : roomList){
            Position roomPos = room.getPos();
            int abs = roomPos.getAbs();
            int ord = roomPos.getOrd();
            roomArray[ord][abs] = room;
        }
        return roomArray;
    }

    /**
     * Transform the array of room into a list of String which contains each line to print
     * in order to see the minimap
     *
     * @param roomArray
     * @return List<String>
     */
    private List<String> stringByLine (Room[][] roomArray){
        List<String> listOfLine= new ArrayList<>();

        for (Room[] lineRoom : roomArray){
            for (int i=0; i<4; i++){
                StringBuilder sb2 = new StringBuilder();
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
     * @param room
     * @param line
     * @return String
     */

    private String buildRoom(Room room, int line) {
        String strLine = "";

        if (room == null){
            strLine = "       ";
        }
        else {
            switch (line){
                case 0: {
                    if (room.getNorth() != -1){ return " __#__ "; }
                    else { return " _____ "; }}

                case 1: { return  "|     |"; }

                case 2:{
                    StringBuilder sb = new StringBuilder();
                    if (room.getWest() != -1) {
                        sb.append("#  ");
                    } else sb.append("|  ");

                    //if (joueur in room){sb.append(@);} //TODO
                    //else {
                        sb.append(room.getRoomNum());
                    //}

                    if (room.getEast() != -1) {
                        sb.append("  #");
                    } else sb.append("  |");
                    return  sb.toString();
                }

                 //case 2:{ return "|     |"; }

                case 3:{
                    if (room.getSouth() != -1){ return "|__#__|";}
                    else {return "|_____|";}
                }
            }
        }
        return strLine;
    }





}
