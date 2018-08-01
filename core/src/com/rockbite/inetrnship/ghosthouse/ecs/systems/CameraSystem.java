package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;


public class CameraSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    AssetLoader assetLoader = new AssetLoader();
    Array<Room> Rooms= assetLoader.getRooms();
    Entity entity;
    CameraComponent Cameracom;
    int target=0;
    public PerspectiveCamera Cam;

    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room
    // to room in all 3 directions
    public boolean ispressed = false;

    Interpolation a = new Interpolation.SwingIn(2); //Interpolation of y
    Interpolation z = new Interpolation.Pow(2); //Interpolation of z

    float t = 0f;
    final float T = 10f; //Total time for moving from room to room


    public void addedToEngine(Engine engine) {

        entities = engine.getEntitiesFor(Family.all(CameraComponent.class).get());

        Cam = new PerspectiveCamera();
        Cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Cam.position.set(Rooms.get(0).getOrigin().x+Rooms.get(0).getWidth()/2f, Rooms.get(0).getOrigin().y+Rooms.get(0).getHeight()/2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), Rooms.get(0).getWidth()+Rooms.get(0).getWidth() / 5f) + 3);

        System.out.println(Cam.position);
    }

    public void update(float deltaTime) {

        Entity entity = entities.get(0);
        Cameracom = cm.get(entity);

        inputhandle();
        interpolhandle();

    }

    public void inputhandle() {


        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            target++;
            if(target==Rooms.size)
                target=0;
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(target, Rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);
        }

    if(Gdx.input.justTouched()){
        Vector2 clickPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        Vector3 worldCoordinates = Cam.unproject(new Vector3(clickPos, 0), Cam.position.x, Cam.position.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        Ray ray= Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
        System.out.println(worldCoordinates.x);


        //Vector3 pointOnNearPlane = Cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getX(), 0f));
        //System.out.println(pointOnNearPlane);
    }

    }

    public void interpolhandle() {
        if (ispressed) {
            if (Cam.position.equals(Cameracom.targetvec)) {
                ispressed = false;
                t = 0;
            } else {
                if (t < T) {


                    Cam.position.set(Cameracom.LL.x + dist.x * t / T, Cameracom.LL.y + dist.y
                            * a.apply(t / T), Cameracom.LL.z + dist.z * z.apply(t / T));
                    t += Gdx.graphics.getDeltaTime() * 20f;
                } else if (t >= T) {
                    t = T;
                    Cam.position.set(Cameracom.LL.x + dist.x * t / T, Cameracom.LL.y + dist.y
                            * a.apply(t / T), Cameracom.LL.z + dist.z * z.apply(t / T));

                }

            }
        }
    }

    public static Vector3 move(int ind, Array<Room> Rooms) {

        Vector3 VEC = new Vector3(Rooms.get(ind).getOrigin().x+Rooms.get(ind).getWidth()/2f, Rooms.get(ind).getOrigin().y+Rooms.get(ind).getHeight()/2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), Rooms.get(ind).getWidth()+Rooms.get(ind).getWidth() / 5f) + 3);

        return VEC;
    }


    public static double angle(double width, double height, double VF) {
        double result = Math.atan((width * Math.tan((VF / 180 * Math.PI) / 2)) / height) * 2 * 180 / Math.PI;

        return result;
    }

    public static double dist(double alfa, double size) {
        double y = size / 2;
        double r = y / (Math.sin((alfa / 180) * Math.PI / 2));
        return Math.sqrt(r * r - y * y);
    }
}


