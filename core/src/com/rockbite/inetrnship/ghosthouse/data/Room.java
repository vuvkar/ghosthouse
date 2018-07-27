package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.Vector2;

public class Room {
    private int index;
    private Vector2 origin;
    private float width;
    private float height;

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
}
