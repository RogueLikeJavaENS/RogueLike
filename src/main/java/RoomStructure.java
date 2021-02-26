public class RoomStructure {
    public static Room createRoom(int current, int next, Seed seed, String direction){
        int[][] contents;
        contents = new int[16][32];
        switch (direction) {
            case "north" : contents[0][6]=3;
                break;
            case "south" : contents[15][6]=3;
                break;
            case "east" : contents[6][0]=3;
                break;
            case "west" : contents[6][31]=3;
        }
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 32; y++) {
                if (x==0 || x==15 || y==0 || y==31){
                    if(contents[x][y]!=3) {
                        contents[x][y] = 1;
                    }
                    else{
                        contents[x][y] = 3;
                    }
                }
                else{
                    contents[x][y]=2;
                }
            }
        }
        return new Room(current, next, contents, 32, 16);
    }
}
