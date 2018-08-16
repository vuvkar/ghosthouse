package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;

public class NormalMapComponent implements Component {
    public String normalMap;

    public String getTexture() {
        return normalMap;
    }

    public void setTexture(String normalMap) {
        this.normalMap = normalMap;
    }

    public NormalMapComponent(String normalMap) {
        this.normalMap = normalMap;
    }
}
