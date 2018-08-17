package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;


public class ScaleComponent implements Component {
    float byX;
    float byY;
    float byZ;

    public ScaleComponent(float byX, float byY, float byZ) {
        this.byX = byX;
        this.byY = byY;
        this.byZ = byZ;
    }
}
