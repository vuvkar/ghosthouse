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
    AssetLoader assetLoader = new AssetLoader();
    Array<Room> rooms = assetLoader.getRooms();
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
    public boolean checkInteraction(float item) {
return true; //return true if item is interactable

    }

    void update(GhostMesh mesh, CameraSystem cameraSystem) {

        //If clicked
        if (Gdx.input.justTouched()) {

            ray = cameraSystem.Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());//casting the ray
            short[] temo = mesh.itemIndices.clone();
            for (int i = 0; i < temo.length; i++) {
                temo[i] -= 132;
                if (temo[i] < 0) {
                    temo[i] = 0;
                }
            }
//If intersects the objects' mesh
            if (Intersector.intersectRayTriangles(ray, mesh.itemVertices, temo, 8, targetPosition)) {

                for (int i = 0; i < rooms.get(cameraSystem.target).getItems().size; i++) {
                    pos = rooms.get(cameraSystem.target).items.get(i).getComponent(PositionComponent.class);
                    size = rooms.get(cameraSystem.target).items.get(i).getComponent(SizeComponent.class);

                    //which particular item is intersected
                    if (isInside(new Vector3(pos.getX(), pos.getY(), pos.getZ()), new Vector2(size.width, size.height), targetPosition)) {
                        if (pos.getZ() >= indexAndMax[0]) { //If the items are overlapping
                            indexAndMax[0] = pos.getZ();
                            indexAndMax[1] = i;
                        }
                         System.out.println("YEY to item "+i+" in room"+cameraSystem.target);
                    }

                }
                System.out.println("Item:"+indexAndMax[1]+"in room"+cameraSystem.target);
                indexAndMax[0] = 0;
                indexAndMax[1] = 0;

            }

            //If click on the building
//            else if (Intersector.intersectRayTriangles(ray, mesh.buildingVertices, mesh.buildingIndices, 8, targetPosition)) {
//                moveCharacter(); //move to a place
//            }


        }

    }

    public static boolean isInside(Vector3 origin, Vector2 size, Vector3 point) {

        if ((origin.x < point.x && point.x < origin.x + size.x) && (origin.y < point.y && point.y < origin.y + size.y))
            return true;
        else
            return false;
    }
}
