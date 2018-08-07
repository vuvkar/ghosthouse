package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

public abstract class Room implements Comparable<Room> {
    public MainGame mainGame;

    public int id;
    public Vector2 origin;
    public float width;
    public float height;
    public Vector3 lightCol1 = new Vector3(201.0f / 255.0f, 100.0f / 255.0f, 185.0f / 255.0f);
    public Vector3 lightCol2 = new Vector3(100.0f / 255.0f, 200.0f / 255.0f, 205.0f / 255.0f);

   public Array<Entity> items;

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
                item.add(new RoomObjectComponent(this.id));
                item.add(new ScaleComponent(object2.scale[0], object2.scale[1], object2.scale[2]));
                item.add(new RotationComponent(object2.rotation[0], object2.rotation[1], object2.rotation[2]));
                item.add(new SizeComponent(object2.width, object2.height));
                items.add(item);
            }
        }
        //FIXME: fix this shit
        GhostMesh.ITEM_COUNT += items.size;
    }

    abstract public void roomStarted();
    abstract public void itemWasClicked(int itemID);
    abstract public void itemWasDragged(int fromInventory, int toRoomItem);

    public void leaveRoom() {
        mainGame.leavedRoom();
    }

    public void setItemStatus(int itemID) {

    }

    public void getItemStatus(int itemID) {

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