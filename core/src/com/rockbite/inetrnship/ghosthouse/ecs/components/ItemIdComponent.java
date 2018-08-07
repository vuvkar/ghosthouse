package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;

public class ItemIdComponent implements Component {
    int itemID;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public ItemIdComponent(int itemID) {
        this.itemID = itemID;
    }
}
