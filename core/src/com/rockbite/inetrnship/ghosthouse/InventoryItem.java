package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;

public class InventoryItem extends Image {

    public int ID;
    public TextureComponent texture;
    public int placeInInventory = 0;

    public InventoryItem(int ID, TextureComponent texture) {
        super(AssetLoader.getRegion(texture.texture));
        this.ID = ID;
        this.texture = texture;
        this.setTouchable(Touchable.enabled);
    }

    public InventoryItem(int ID1, int ID2, TextureComponent texture) {
        super(AssetLoader.getRegion(texture.texture));
        this.ID = ID1 + ID2 + 50;
        this.texture = texture;
        this.setTouchable(Touchable.enabled);
    }

    public InventoryItem() {
        super();
    }

    public TextureComponent getTexture() {
        return texture;
    }

    public void changeID(int ID) {
        this.ID = ID;
    }

    @Override
    protected void positionChanged() {

    }
}