package gameElement;

import generation.RoomStructure;
import generation.Seed;
import utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to see the whole dungeon as a minimap
 *
 * @author Antoine
 */

public static class MiniMap {
    Dungeon dungeon;
    List<String>[][] miniMapString;


    public MiniMap(Dungeon dungeon) {
        this.dungeon = dungeon;

        // TODO : Create dynamically the minimap in space, and print it
/*        miniMapString = new List[2][3];
        for (int y = 0; y < 2; y++) {
            for (int x =0; x < 3; x++) {
                miniMapString[y][x] = getEmptyRoom();
            }
        }

        miniMapString[0][1] = buildRoom(roomList.get(1));
        miniMapString[1][1] = buildRoom(roomList.get(0));*/
    }

    public String toString() {
        List<String> strByLine = stringByLine(listToArray(dungeon));

        StringBuilder sb = new StringBuilder();
        for (String str : strByLine){
            sb.append(str);
            sb.append("\n");
        }
        return sb.toString();
    }

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

    private List<String> stringByLine (Room[][] roomArray){
        List<String> listofLine= new ArrayList<String>();

        List<String> affichage = new ArrayList<String>();
        for (Room[] lineRoom : roomArray){
            for (int i=0; i<5; i++){
                StringBuilder sb2 = new StringBuilder();
                for (Room room : lineRoom){
                    sb2.append(buildRoom(room,i));
                }
                String line = sb2.toString();
                listofLine.add(line);
            }
        }
        return listofLine;
    }




    private String buildRoom(Room room, int line) {
        String strLine = "";

        if (room == null){
            strLine = "       ";
        }
        else {
            switch (line){
                case 0:{
                if (room.getNorth() != -1){ strLine = " __#__ "; }
                else { strLine = " _____ "; }}

                case 1 :{ strLine = "|     |"; }

                case 2:{
                    StringBuilder sb = new StringBuilder();
                    if (room.getWest() != -1) {
                        sb.append("#  ");
                    } else sb.append("|  ");
                    sb.append(room.getRoomNum());
                    if (room.getEast() != -1) {
                        sb.append("  #");
                    } else sb.append("  |");
                    strLine = sb.toString();
                }

                 case 3:{ return "|     |"; }

                    case 4:{
                        if (room.getSouth() != -1){ strLine = "|__#__|";}
                        else {strLine = "|_____|";}
                    }
            }
        }
        return strLine;
    }





}
