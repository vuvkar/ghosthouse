package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Item implements Json.Serializable{
    private int id;
    private String itemName;
    private int[] position = new int[3];
    public TextureAtlas.AtlasRegion texture;

    public void write(Json json) {
        json.writeArrayStart();
        json.writeField(id, itemName);
        json.writeField(position, itemName);
        json.writeArrayEnd();
    }

    public void read(Json json, JsonValue jsonMap) {
        itemName = jsonMap.child().name;
        id = jsonMap.child().asInt();
        position = jsonMap.child.asIntArray();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        //texture = new Texture(Gdx.files.internal("items/"+itemName));
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
