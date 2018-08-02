package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;

public enum RectangleType {
    ROOM, BUILDING, WALL, FLOOR, CEILING, ITEM;

//    public Color vertexColor() {
//        switch (this) {
//            case ROOM:
//                return Color.RED;
//            case WALL:
//                return Color.FOREST;
//            case BUILDING:
//                return Color.BLUE;
//        }
//        return null;
//    }
//

    public TextureAtlas.AtlasRegion getTexture(String texture) {
        switch (this) {
            case ROOM:
                return AssetLoader.getRegion("roomTextureClouds");
            case WALL:
                return AssetLoader.getRegion("wall");
            case BUILDING:
                return AssetLoader.getRegion("wall");
            case FLOOR:
                return AssetLoader.getRegion("floor");
            case CEILING:
                return AssetLoader.getRegion("floor");
            case ITEM:
                return  AssetLoader.getRegion(texture);
        }
        return null;
    }
}
