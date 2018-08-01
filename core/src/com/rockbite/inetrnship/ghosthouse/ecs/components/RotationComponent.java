package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;

public class RotationComponent implements Component {
    float byX;
    float byY;
    float byZ;

    public RotationComponent(float byX, float byY, float byZ) {
        this.byX = byX;
        this.byY = byY;
        this.byZ = byZ;
    }
}
