package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public class Room implements Comparable<Room> {
    private int index;
    private Vector2 origin;
    private float width;
    private float height;

    private Array<Entity> items;

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

    public void loadEntities() {
        items = new Array<Entity>();

        Json json = new Json();
        Array<GameObject> array = json.fromJson(Array.class, Gdx.files.internal("JSON/trial.json"));
        for(GameObject object: array) {
            Entity item = new Entity();
            item.add(new TextureComponent(object.texture));
            item.add(new PositionComponent(object.position[0], object.position[1], object.position[2]));
            item.add(new RoomObjectComponent(this.index));
            item.add(new ScaleComponent(object.scale[0], object.scale[1], object.scale[2]));
            item.add(new RotationComponent(object.rotation[0], object.rotation[1], object.rotation[2]));
            items.add(item);
        }
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

