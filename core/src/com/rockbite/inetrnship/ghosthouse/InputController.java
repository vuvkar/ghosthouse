package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.SizeComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;


public class InputController {
    // TODO: Monika's shit
    public Vector3 targetPosition = new Vector3(0, 0, 0);
    public Ray ray = new Ray();
    PositionComponent pos = new PositionComponent(0, 0, 0);
    SizeComponent size = new SizeComponent(0, 0);
    int current=0;
    float[] indexAndMax = {0, 0};

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

    void update(GhostMesh mesh, CameraSystem cameraSystem, Array<Room> rooms) {

        if (Gdx.input.justTouched()) {

            ray = cameraSystem.Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
            short[] temo = mesh.itemIndices.clone();
            for (int i = 0; i < temo.length; i++) {
                temo[i] -= 132;
                if (temo[i] < 0) {
                    temo[i] = 0;
                }
            }

            if (Intersector.intersectRayTriangles(ray, mesh.itemVertices, temo, 8, targetPosition)) {
for(int i=0; i<rooms.size; i++){
    if(rooms.get(i).getIndex()==cameraSystem.target)
        current=rooms.get(i).getIndex();
}
                for (int i = 0; i < rooms.get(current).getItems().size; i++) {
                    pos = rooms.get(current).items.get(i).getComponent(PositionComponent.class);
                    size = rooms.get(current).items.get(i).getComponent(SizeComponent.class);

                    // System.out.println("click pos: "+targetPosition +" Object origin: "+new Vector3(pos.getX(), pos.getY(), pos.getZ()));
                    System.out.println(rooms.get(current).getOrigin());
                    System.out.println(current);
                    if (isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition)) {
                        if (pos.getZ() >= indexAndMax[0]) {
                            indexAndMax[0] = pos.getZ();
                            indexAndMax[1] = i;
                        }
                        // System.out.println("YEY");
                    }

                }
                //System.out.println("Item:"+indexAndMax[1]+"in room"+cameraSystem.target);
                indexAndMax[0] = 0;
                indexAndMax[1] = 0;
            } else if (Intersector.intersectRayTriangles(ray, mesh.buildingVertices, mesh.buildingIndices, 8, targetPosition)) {
                // System.out.println(targetPosition);
            }


        }

    }

    public static boolean isInside(Vector3 origin, Vector2 size, Vector3 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }
}
