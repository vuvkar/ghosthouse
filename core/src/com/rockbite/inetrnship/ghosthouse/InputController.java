package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;


public class InputController {
    // TODO: Monika's shit
    public Vector3 targetPosition=new Vector3(0,0,0);
    public Ray ray=new Ray();

    public Vector3 getInputCoordinates() {
        return new Vector3(Gdx.input.getX(), Gdx.input.getY(), 10);
    }


    // TODO: Liana's Megashit
    public void moveCharacter() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        final float velocity = 10;

        //targetPosition = getInputCoordinates();

        Entity current = new Entity();
        current.add(new PositionComponent(0, 0, 0));

        ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
        PositionComponent position = pm.get(current);


    }

    void update(GhostMesh mesh, PerspectiveCamera Cam) {

        if(Gdx.input.justTouched()){
            ray = Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());

            if (Intersector.intersectRayTriangles(ray, mesh.buildingVertices, mesh.buildingIndices, 8, targetPosition)) {
                moveCharacter();
            }


        }

    }


}
