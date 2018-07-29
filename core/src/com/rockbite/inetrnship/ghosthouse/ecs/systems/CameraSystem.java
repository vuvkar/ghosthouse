package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class CameraSystem extends EntitySystem {

    public Camera cam = new PerspectiveCamera();

    public void moveToRoom(int roomNum) {

    }

    @Override
     public void update(float delta) {

    }
}
