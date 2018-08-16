package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.data.Room1;
import com.rockbite.inetrnship.ghosthouse.data.Room2;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AssetLoader extends AssetManager {

    public static Array<Room> rooms;
    public static TextureAtlas atlas;

    public static float ATLAS_HEIGHT;
    public static float ATLAS_WIDTH;

    public void setRooms(Array<Room> rooms) {
        this.rooms = rooms;
    }


    public AssetLoader() {
//       TODO: WARNING: DO NOT ATTEMPT TO DELETEEE!!!!!!!! Thanks :)
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxHeight = 8192;
        settings.maxWidth = 8192;
        settings.wrapX = Texture.TextureWrap.Repeat;
        settings.wrapY = Texture.TextureWrap.Repeat;
        TexturePacker.process(settings,"textures",
                "packed", "game");
        atlas = new TextureAtlas(Gdx.files.internal("packed/game.atlas"));
        ATLAS_HEIGHT = atlas.getTextures().first().getHeight();
        ATLAS_WIDTH = atlas.getTextures().first().getWidth();
        loadGameData();
//
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//     settings.maxHeight = 4096;
//     settings.maxWidth = 4096;
//        TexturePacker.process(settings,"Uipics", "Uipacked", "UI");
//
//                TexturePacker.Settings settings = new TexturePacker.Settings();
//     settings.maxHeight = 4096;
//     settings.maxWidth = 4096;
//        TexturePacker.process(settings,"MiniGame/MinigamePics", "MiniGame/AtlasGame", "PuzzleAtlas");
    }

    public void loadGameData() {
        GhostMesh.ITEM_COUNT = 0;
        int[][] pixelData = readPixelData();
        rooms = processRoomData(pixelData);
        Json json = new Json();
        Room1 room1 = json.fromJson(Room1.class, Gdx.files.internal("JSON/room1.json"));
        Room2 room2 = json.fromJson(Room2.class, Gdx.files.internal("JSON/room2.json"));
        Room1 room3 = json.fromJson(Room1.class, Gdx.files.internal("JSON/room3.json"));
        Room1 room4 = json.fromJson(Room1.class, Gdx.files.internal("JSON/room4.json"));
        Array<Room> newRooms = new Array<Room>();
        newRooms.add(room1, room2, room3, room4);
        for(Room room: newRooms) {
            room.loadEntities();
        }
        rooms = newRooms;
    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        if(name == "") {
            return  new TextureAtlas.AtlasRegion(new Texture(Gdx.files.internal("textures/album.png")), 0, 0, 0, 0);
        }
        return atlas.findRegion(name);
    }

    public int[][] readPixelData() {
        // TODO: courtesy of Dave
        int[][] matrix = new int[0][0];
        String fileName = "PBMs/Monika_Map.pbm";
        String line;
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

           // Room1 room = new Room1(i, bottomLeft, height, width);
           // roomArray.add(room);
        }

        return roomArray;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
    }
}
