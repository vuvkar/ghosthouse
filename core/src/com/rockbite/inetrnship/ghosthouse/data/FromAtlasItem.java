package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;

public class FromAtlasItem {
    TextureAtlas atlas;
    String atlasName;
    TextureRegion atlasRegion;
    FileHandle file = Gdx.files.internal("packed/r1Items.atlas");

    public FromAtlasItem() {
        atlas = new TextureAtlas(file);
        setAtlasName(atlasName);
        for (Texture texture : atlas.getTextures()) {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
    }

   public Array<Item> generateItems() {
        int id = 0;
        Array<Item> items =  new Array<Item>();
        for(TextureAtlas.AtlasRegion texture: atlas.getRegions()) {
            Item item = new Item();
            item.setId(id++);
            item.setItemName(texture.name);
            item.setPosition(new int[]{0, 0, 0});
            item.texture = texture;
            items.add(item);
        }
        return items;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public TextureRegion getAtlasRegion() {
        return atlasRegion;
    }

    public void setAtlasRegion(TextureRegion atlasRegion) {
        this.atlasRegion = atlasRegion;
    }
}
