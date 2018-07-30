package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
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
        int[][] pixelData = readPixelData();

        rooms = processRoomData(pixelData);
    }

    public int[][] readPixelData() {
        // TODO: courtesy of Dave

        return null;
    }


    public Array<Room> processRoomData(int[][] rawPixelData) {
        //TODO: hi Liana
        RoomParser roomParser = new RoomParser();

        Array<Room> roomArray = new Array<Room>();

        roomParser.search(rawPixelData);

        int roomID = roomParser.getRoomCount();
        for (int i = 0; i < roomID; ++i) {

            Vector2 bottomLeft = roomParser.bottomLeftCorner(rawPixelData, roomID);
            Vector2 topRight = roomParser.topRightCorner(rawPixelData, roomID);

            float height = roomParser.getRoomHeight(rawPixelData, roomID, bottomLeft, topRight);
            float width = roomParser.getRoomWidth(rawPixelData, roomID, bottomLeft, topRight);
            Room room = new Room(roomID, bottomLeft, width, height);
            roomArray.add(room);
        }

        return roomArray;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

}
