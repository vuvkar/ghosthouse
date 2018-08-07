package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.RepeatablePolygonSprite;
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
        TextureAtlas.AtlasRegion region = null;
        switch (this) {
            case ROOM:
                region = AssetLoader.getRegion("roomTextureClouds");
                break;
            case WALL:
                region = AssetLoader.getRegion("wall");
                break;
            case BUILDING:
                region = AssetLoader.getRegion("wall");
                break;
            case FLOOR:
                region = AssetLoader.getRegion("floor");
                break;
            case CEILING:
                region = AssetLoader.getRegion("floor");
                break;
            case ITEM:
                region = AssetLoader.getRegion(texture);
                break;
        }

        return region;
    }
}
