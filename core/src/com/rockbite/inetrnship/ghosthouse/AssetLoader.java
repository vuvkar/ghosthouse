package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AssetLoader extends AssetManager {

    private Array<Room> rooms;

    public AssetLoader() {
        loadGameData();
    }

    public void loadGameData() {
        int[][] pixelData = readPixelData();

        rooms = processRoomData(pixelData);
    }

    public int[][] readPixelData() {
        // TODO: courtesy of Dave

        int[][] matrix = new int[0][0];
        String fileName = "shit.pbm";
        String line = null;
        try {
            FileReader filereader = new FileReader(fileName);
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
            while (i < height && j < width) {
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
        return (matrix);
    }

    public Array<Room> processRoomData(int[][] rawPixelData) {
        //TODO: hi Liana

        RoomParser roomParser = new RoomParser();

        Array<Room> roomArray = new Array<Room>();

        roomParser.search(rawPixelData);

        int roomID = roomParser.getRoomCount();

        for (int i = 0; i < roomID; ++i) {

            Vector2 bottomLeft = roomParser.bottomLeftCorner(rawPixelData, i);
            Vector2 topRight = roomParser.topRightCorner(rawPixelData, i);

            System.out.println(bottomLeft + " " + topRight);

            float height = roomParser.getRoomHeight(rawPixelData, i, bottomLeft, topRight);
            float width = roomParser.getRoomWidth(rawPixelData, i, bottomLeft, topRight);

            System.out.println(width + " " + height);
            System.out.println();

            Room room = new Room(i, bottomLeft, height, width);
            roomArray.add(room);
        }

        return roomArray;
    }

    public Array<Room> getRooms() {
        return rooms;
    }
}