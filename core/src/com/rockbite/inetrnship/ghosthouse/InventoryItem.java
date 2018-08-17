package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;

public class InventoryItem extends Image {

    public int ID;
    public TextureComponent texture;
    public int placeInInventory = 0;
    public Vector2 size=new Vector2(0,0);

    public InventoryItem(int ID, TextureComponent texture) {
        super(AssetLoader.getRegion(texture.texture));
        this.ID = ID;
        this.texture = texture;
        this.setTouchable(Touchable.enabled);
    }

public Vector2 getSize(){
        return this.size;
}

    public InventoryItem() {
        super();
    }

    public TextureComponent getTexture() {
        return texture;
    }



    @Override
    protected void positionChanged() {

    }
}
