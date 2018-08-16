package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.MiniGames.MiniGame;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;


public abstract class Room implements Comparable<Room> {
    public MainGame mainGame;
    public static float soundVolume=0.8f;
    public int id;
    public Vector2 origin;
    public float width;
    public float height;

    public String floorTexture;
    public String sideWallTexture;
    public String wallTexture;
    public String ceilingTexture;

    public MiniGame miniGame;

    public float[] light;

    ComponentMapper<ItemTypeComponent> itemTypeComponent = ComponentMapper.getFor(ItemTypeComponent.class);
    ComponentMapper<ItemIdComponent> itemIdComponentm = ComponentMapper.getFor(ItemIdComponent.class);

    public Array<Object> objects;

    public Array<Entity> items;
    public Array<Entity> models;


    public void loadEntities() {
        items = new Array<Entity>();
        models = new Array<Entity>();

        light[0] /= 255;
        light[1] /= 255;
        light[2] /= 255;

        for (Object object : objects) {
            if ((GameObject) object != null) {
                GameObject object2 = (GameObject) object;
                Entity item = new Entity();
                item.add(new TextureComponent(object2.texture));

                item.add(new PositionComponent(this.origin.x + object2.position[0],
                        this.origin.y + object2.position[1] - this.height, object2.position[2]));
                item.add(new RoomObjectComponent(this.id));
                item.add(new ItemIdComponent(object2.id));
                item.add(new ItemTypeComponent(ItemType.STATIC));
                item.add(new ScaleComponent(object2.scale[0], object2.scale[1], object2.scale[2]));
                item.add(new RotationComponent(object2.rotation[0], object2.rotation[1], object2.rotation[2]));
                item.add(new SizeComponent(object2.width, object2.height));
                item.add(new NormalMapComponent(object2.normalMap));
                items.add(item);
            }
        }

        Entity item = new Entity();
        item.add(new ModelComponent("HeiHei.obj"));
        item.add(new PositionComponent(this.origin.x + 5,
                this.origin.y + 5 - this.height, 2));
        item.add(new RoomObjectComponent(this.id));
        item.add(new ItemTypeComponent(ItemType.TAKEABLE));
        item.add(new ItemIdComponent(158));
        item.add(new ScaleComponent(1.0f, 1.0f, 1.0f));
        item.add(new RotationComponent(0f, 0f, 0f));
        item.add(new SizeComponent(1, 1));
        models.add(item);

        //FIXME: fix this shit
        GhostMesh.ITEM_COUNT += items.size;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    abstract public void roomStarted();

    abstract public void itemWasClicked(int itemID);

    abstract public void itemWasDragged(int fromInventory, int toRoomItem);

    abstract public void itemWasMoved(int fromInventory, int toInventory);

    abstract  public void miniGameWasClosed();

    public void leaveRoom() {
        mainGame.leavedRoom();
    }


    public void setItemStatus(int itemID, ItemType type) {

        for (Entity item : items) {
            if (itemIdComponentm.get(item).getItemID() == itemID) {
               itemTypeComponent.get(item).setType(type);

            }
        }
    }

    public void moveGhostTo(int itemID) {
        Entity item = getItemById(itemID);
        PositionComponent positionComponent = item.getComponent(PositionComponent.class);
       PositionComponent ghostPosition =  mainGame.ghost.getComponent(PositionComponent.class);
      // mainGame.inputController.targetPosition = new Vector3(50f, 50f, 50f);
       mainGame.inputController.moveCharacter();
       ghostPosition.setX(positionComponent.getX() - 0.6f);
       ghostPosition.setY(positionComponent.getY() - 0.6f);
    }

    public void openMiniGame() {
        this.miniGame.start();
    }

    public ItemType getItemStatus(int itemID) {
        for (Entity item : items) {
            if (itemIdComponentm.get(item).getItemID() == itemID) {

                return itemTypeComponent.get(item).getType();
            }
        }
        return null;
    }

    public void changeTexture(int itemID, String textureName) {
        Entity item = getItemById(itemID);
        TextureComponent comp = item.getComponent(TextureComponent.class);
        comp.setTexture(textureName);
    }

    public void addToInventory(int itemID) {

        mainGame.inputController.addToInventory(itemID);
    }

    public void removeFromInventory(int itemID) {
      mainGame.inputController.removeFromInventory(itemID);
    }

   public  Entity getItemById(int itemID) {
        int count = items.size;
        for(int i = 0; i < count; i++) {
            if(items != null) {
                if (itemIdComponentm.has(items.get(i))) {
                    ItemIdComponent comp = itemIdComponentm.get(items.get(i));
                    if (comp.getItemID() == itemID) {
                        return items.get(i);
                    }
                }
            }
        }
        return null;
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