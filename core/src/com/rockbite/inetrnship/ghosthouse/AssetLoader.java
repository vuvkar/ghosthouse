package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AssetLoader extends AssetManager {

    private Array<Room> rooms;
    static TextureAtlas atlas;

    public AssetLoader() {
        loadGameData();
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxWidth = 4096;
//        settings.maxHeight = 4096;
//        TexturePacker.process(settings, "textures", "packed", "game");
//        loadJSON();
        atlas = new TextureAtlas(Gdx.files.internal("packed/game.atlas"));
    }

    public void loadGameData() {
        int[][] pixelData = readPixelData();
        rooms = processRoomData(pixelData);
    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        return atlas.findRegion(name);
    }

    public int[][] readPixelData() {
        // TODO: courtesy of Dave
        int[][] matrix = new int[0][0];
        String fileName = "PBMs/Monika_Map.pbm";
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            assert line != "P1";
            line = bufferedReader.readLine();

            while (line.charAt(0) == '#') {
                line = bufferedReader.readLine();
            }

            java.lang.String[] tmp = line.split(" ");
            int width = Integer.parseInt(tmp[0]);
            int height = Integer.parseInt(tmp[1]);

            matrix = new int[height][width];

            int i = 0;
            int j = 0;

            while (i < height && j < width) {
                line = bufferedReader.readLine();

                while (line.charAt(0) == '#') {
                    line = bufferedReader.readLine();
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
            System.out.println("Wrong picture format.");
        }
        return (matrix);
    }

    public void loadJSON() {
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.internal("JSON/trial.json"));
    }

    public Array<Room> processRoomData(int[][] rawPixelData) {
        //TODO: hi Liana

        RoomParser roomParser = new RoomParser();
        Array<Room> roomArray = new Array<Room>();
        roomParser.search(rawPixelData);

        int roomID = roomParser.getRoomCount();

        for (int i = 0; i < roomID; ++i) {
            Vector2 bottomLeft = roomParser.bottomLeftCorner(rawPixelData, i);

            float height = roomParser.getRoomHeight(rawPixelData, i);
            float width = roomParser.getRoomWidth(rawPixelData, i);

            Room room = new Room(i, bottomLeft, height, width);
            roomArray.add(room);
        }

        return roomArray;
    }

    public Array<Room> getRooms() {
        return rooms;
    }
}
