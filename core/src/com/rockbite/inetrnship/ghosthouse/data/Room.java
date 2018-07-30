package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.Vector2;

public class Room implements Comparable<Room> {
    private int index;
    private Vector2 origin;
    private float width;
    private float height;

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    public void setHeight(float height) {
        this.height = height;
    }

    public Room(int index, Vector2 origin, float width, float height) {
        this.index = index;
        this.origin = origin;
        this.width = width;
        this.height = height;

    }

    public int getIndex() {
        return index;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public int compareTo(Room o) {
        float value = this.origin.x - o.origin.x;
        if (value == 0) {
            return 0;
        }
        return value > 0 ? 1 : -1;

    }
}
