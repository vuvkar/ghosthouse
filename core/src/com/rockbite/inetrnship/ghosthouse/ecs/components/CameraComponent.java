package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class CameraComponent implements Component {

    public Vector3 LL; //Lower-Left aka the midpoint of the room
    public Vector3 targetvec; //Midpoint of the target room


    public CameraComponent(Vector3 bottomLeft, Vector3 targetVec) {
        this.LL = bottomLeft;
        this.targetvec = targetVec;
    }

    public CameraComponent() {
        this.LL = new Vector3(0, 0, 0);
        this.targetvec = new Vector3(0, 0, 0);
    }
}
