package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.data.*;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AssetLoader extends AssetManager {

    public static Array<Room> rooms;
    public static TextureAtlas atlas;

    static Texture empty;

    public static float ATLAS_HEIGHT;
    public static float ATLAS_WIDTH;

    private int room = 0;

    public void setRooms(Array<Room> rooms) {
        this.rooms = rooms;
    }


    public AssetLoader() {
//       TODO: WARNING: DO NOT ATTEMPT TO DELETEEE!!!!!!!! Thanks :)
//        TexturePacker.Settings settings = new TexturePacker.Settings();
//        settings.maxHeight = 8192;
//        settings.maxWidth = 8192;
//        settings.wrapX = Texture.TextureWrap.Repeat;
//        settings.wrapY = Texture.TextureWrap.Repeat;
//        TexturePacker.process(settings,"textures",
//                "packed", "game");
        atlas = new TextureAtlas(Gdx.files.internal ("packed/game.atlas"));
        empty = new Texture(Gdx.files.internal("textures/album1.png"));
//        ATLAS_HEIGHT = atlas.getTextures().first().getHeight();
//        ATLAS_WIDTH = atlas.getTextures().first().getWidth();
        loadGameData();
        SaveDataLoader saveLoad = new SaveDataLoader();
        saveLoad.save(room);
//        room = saveLoad.load();


//        TexturePacker.Settings settings = new TexturePacker.Settings();
//     settings.maxHeight = 4096;
//     settings.maxWidth = 4096;
//        TexturePacker.process(settings,"Uipics", "Uipacked", "UI");

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
        Room3 room3 = json.fromJson(Room3.class, Gdx.files.internal("JSON/room3.json"));
        Room4 room4 = json.fromJson(Room4.class, Gdx.files.internal("JSON/room4.json"));
        Array<Room> newRooms = new Array<Room>();
        newRooms.add(room1, room2, room3, room4);
        for(Room room: newRooms) {
            room.loadEntities();
        }
        for(Entity entity: room1.items) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            pos.setY(pos.getY() - room1.height);
        }

        for(Entity entity: room2.items) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            pos.setY(pos.getY() - room2.height);
        }

        for(Entity entity: room3.items) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            pos.setY(pos.getY() - room3.height);
        }

        for(Entity entity: room4.items) {
            PositionComponent pos = entity.getComponent(PositionComponent.class);
            pos.setY(pos.getY() -  room4.height);
        }


        rooms = newRooms;
    }

    private void configureRoomItemPosition() {

    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        if(name == "") {
            return  new TextureAtlas.AtlasRegion(empty, 0, 0, 0, 0);
        }
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
