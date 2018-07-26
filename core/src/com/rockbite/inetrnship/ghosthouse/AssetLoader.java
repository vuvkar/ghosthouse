package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;

public class AssetLoader extends AssetManager {

    private Array<Room> rooms;

    public AssetLoader() {
        loadGameData();
    }

    public void loadGameData() {
        int[] pixelData = readPixelData();

        rooms = processRoomData(pixelData);
    }

    public int[] readPixelData() {
        // TODO: courtesy of Dave

        return null;
    }


    public Array<Room> processRoomData(int[] rawPixelData) {
        //TODO: hi Liana

        return null;
    }

    public Array<Room> getRooms() {
        return rooms;
    }

}
