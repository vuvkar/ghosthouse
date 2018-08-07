package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.rockbite.inetrnship.ghosthouse.data.ItemType;

public class ItemTypeComponent implements Component {
    ItemType type;

    public ItemTypeComponent(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
