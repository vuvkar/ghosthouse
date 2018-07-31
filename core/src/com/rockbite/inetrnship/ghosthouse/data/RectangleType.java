package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.Color;

public enum RectangleType {
    ROOM, BUILDING, WALL;

    public Color vertexColor() {
        switch (this) {
            case ROOM:
                return Color.RED;
            case WALL:
                return Color.FOREST;
            case BUILDING:
                return Color.BLUE;
        }
        return null;
    }
}
