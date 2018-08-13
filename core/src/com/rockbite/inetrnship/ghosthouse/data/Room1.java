package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.Vector2;

public class Room1 extends Room {
    public Room1(int id, Vector2 origin, float width, float height) {
        this.id = id;
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    public Room1(){}

    @Override
    public void roomStarted() {

    }

    @Override
    public void itemWasClicked(int itemID) {

    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {

    }
}
