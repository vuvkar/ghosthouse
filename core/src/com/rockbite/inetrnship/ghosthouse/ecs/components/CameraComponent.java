package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class CameraComponent implements Component {

    public Vector3 middlePoint; //Lower-Left aka the midpoint of the room
    public Vector3 targetVec; //Midpoint of the target room


    public CameraComponent(Vector3 bottomLeft, Vector3 targetVec) {
        this.middlePoint = bottomLeft;
        this.targetVec = targetVec;
    }

    public CameraComponent() {
        this.middlePoint = new Vector3(0, 0, 0);
        this.targetVec = new Vector3(0, 0, 0);
    }
}
