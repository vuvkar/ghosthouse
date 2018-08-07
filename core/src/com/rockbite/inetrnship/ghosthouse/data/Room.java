package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public class Room implements Comparable<Room> {
    private int index;
    private Vector2 origin;
    private float width;
    private float height;
//    private Vector3 lightCol1 = new Vector3(201.0f / 255.0f, 100.0f / 255.0f, 185.0f / 255.0f);
//    private Vector3 lightCol2 = new Vector3(100.0f / 255.0f, 200.0f / 255.0f, 205.0f / 255.0f);
    private float[] light;

    public Array<Entity> items;

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

    public boolean contains(Vector2 vector) {
        return this.contains(vector.add(vector));
    }

//    public Vector3 getLightCol1() {
//        return lightCol1;
//    }

    public void loadEntities() {
        items = new Array<Entity>();

        Json json = new Json();
        Array<Object> array = json.fromJson(Array.class, Gdx.files.internal("JSON/trial.json"));
        for (Object object : array.items) {
            if ((GameObject) object != null) {
                GameObject object2 = (GameObject) object;
                Entity item = new Entity();
                item.add(new TextureComponent(object2.texture));
                item.add(new PositionComponent(this.origin.x + object2.position[0],
                        this.origin.y + object2.position[1], object2.position[2]));
                item.add(new RoomObjectComponent(this.index));
                item.add(new ScaleComponent(object2.scale[0], object2.scale[1], object2.scale[2]));
                item.add(new RotationComponent(object2.rotation[0], object2.rotation[1], object2.rotation[2]));
                item.add(new SizeComponent(object2.width, object2.height));
                items.add(item);
            }
        }
        //FIXME: fix this shit
        GhostMesh.ITEM_COUNT += items.size;

    }
//    public Vector3 getLightCol2() {
//        return lightCol2;
//    }

    public Array<Entity> getItems() {
        return items;
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