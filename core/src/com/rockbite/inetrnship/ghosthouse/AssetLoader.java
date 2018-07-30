package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.util.RoomParser;

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

        return null;
    }


    public Array<Room> processRoomData(int[][] rawPixelData) {
        //TODO: hi Liana
        int index = 0;///////////////////////////////////////////////////////////////////////////////
        RoomParser roomParser = new RoomParser();
        Array<Room> roomArray = new Array<Room>();

        roomParser.search(rawPixelData);

        for (int i = 0; i < index - 2; ++i) {
            Vector2 bottomLeft = roomParser.bottomLeftCorner(rawPixelData, index);
            Vector2 topRight = roomParser.topRightCorner(rawPixelData, index);

            float height = roomParser.getRoomHeight(rawPixelData, index, bottomLeft, topRight);
            float width = roomParser.getRoomWidth(rawPixelData, index, bottomLeft, topRight);
        }

        return roomArray;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

}
