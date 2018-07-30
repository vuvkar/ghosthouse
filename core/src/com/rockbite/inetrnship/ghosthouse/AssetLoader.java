package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;

public class AssetLoader extends AssetManager {

    private Array<Room> rooms;
    //45
    public AssetLoader() {
        loadGameData();
    }

    public void loadGameData() {
        int[] pixelData = readPixelData();

        rooms = processRoomData(pixelData);
    }

    public int[][] readPixelData() {
        int[][] matrix = new int[0][0];
        String filenmae = "unt.pbm";
        String line = null;
        try {
            FileReader filereader = new FileReader("unt.pbm");
            BufferedReader bufferedreader = new BufferedReader(filereader);
            line = bufferedreader.readLine();
            assert line != "P1";
            line = bufferedreader.readLine();
            while (line.charAt(0) == '#') {
                line = bufferedreader.readLine();
            }
            java.lang.String[] tmp = line.split(" ");
            int width = Integer.parseInt(tmp[0]);
            int height = Integer.parseInt(tmp[1]);

            matrix = new int[width][height];
            int i = 0;
            int j = 0;
            while (i < height - 1 && j < width - 1) {
                line = bufferedreader.readLine();
                while (line.charAt(0) == '#') {
                    line = bufferedreader.readLine();
                }
                tmp = line.split(" ");
                int k = 0;
                while (k < tmp.length) {
                    matrix[i][j] = Integer.parseInt(tmp[k]);
                    k++;
                    if (j + 1 < width) {
                        j++;
                    } else {
                        j = 0;
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("dsg");
        }
        return (matrix) ;
    }


    public Array<Room> processRoomData(int[] rawPixelData) {
        //TODO: hi Liana
        RoomParser roomParser = new RoomParser();

        return null;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

}
