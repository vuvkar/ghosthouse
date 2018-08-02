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
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;


public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    AssetLoader assetLoader = new AssetLoader();
    Array<Room> Rooms = assetLoader.getRooms();
    CameraComponent cameraComponent;
    int target = 0;
    public PerspectiveCamera Cam;
Stage stage=new Stage();
    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room to room in all 3 directions
    public boolean isPressed = false;

    Interpolation a = new Interpolation.SwingIn(2); //Interpolation of y
    Interpolation z = new Interpolation.Pow(2); //Interpolation of z
    public CameraInputController camController;
    float t = 0f; // step time
    final float T = 10f; //Total time for moving from room to room


    public void addedToEngine(Engine engine) {

        entities = engine.getEntitiesFor(Family.all(CameraComponent.class).get());

        Cam = new PerspectiveCamera();
        Cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Cam.position.set(Rooms.get(0).getOrigin().x+Rooms.get(0).getWidth()/2f, Rooms.get(0).getOrigin().y+Rooms.get(0).getHeight()/2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), Rooms.get(0).getWidth()+Rooms.get(0).getWidth() / 5f) + 3);

        System.out.println(Cam.position);
        camController = new CameraInputController(Cam);

        Gdx.input.setInputProcessor(camController);
       stage.getViewport().setCamera(Cam);
       Cam.near=1;
    }

    public void update(float deltaTime) {
        Entity entity = entities.get(0);
        cameraComponent = cm.get(entity);
        //System.out.println(Gdx.input.getX());
        inputHandle();
        interpolHandle();

    }

    public void inputHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            target++;
            if (target == Rooms.size)
                target = 0;
            t = 0;
            isPressed = true;
            cameraComponent.targetVec = (move(target, Rooms));
            System.out.println(cameraComponent.targetVec);
            cameraComponent.bottomLeft.set(Cam.position);
            dist.set(cameraComponent.targetVec.x - cameraComponent.bottomLeft.x, cameraComponent.targetVec.y - cameraComponent.bottomLeft.y, cameraComponent.targetVec.z - cameraComponent.bottomLeft.z);
        }

        /*
        if(Gdx.input.justTouched()){
            Vector2 clickPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
           // System.out.println(clickPos);


float dist=(float)dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), 30);
            System.out.println(Cam.far);

            Vector3 worldCoordinates = Cam.unproject(new Vector3(clickPos, 0));
           // System.out.println(worldCoordinates);
           // System.out.println(stage.getViewport().getScreenY());
          //  System.out.println();
           System.out.println(Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY(), stage.getViewport().getScreenX(), stage.getViewport().getScreenY(), stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight()).origin.add(Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY(), stage.getViewport().getScreenX(), stage.getViewport().getScreenY(), stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight()).direction));
           System.out.println(Cam.getPickRay(Gdx.input.getX(), Gdx.input.getY()));

//Vector3 pointOnNearPlane = Cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getX(), 0f));
            //System.out.println(pointOnNearPlane);
        }
*/
    }

    public void interpolHandle() {
        if (isPressed) {
            if (Cam.position.equals(cameraComponent.targetVec)) {
                isPressed = false;
                t = 0;
            } else {
                if (t < T) {
                    Cam.position.set(cameraComponent.bottomLeft.x + dist.x * t / T, cameraComponent.bottomLeft.y + dist.y
                            * a.apply(t / T), cameraComponent.bottomLeft.z + dist.z * z.apply(t / T));
                    t += Gdx.graphics.getDeltaTime() * 20f;
                } else if (t >= T) {
                    t = T;
                    Cam.position.set(cameraComponent.bottomLeft.x + dist.x * t / T, cameraComponent.bottomLeft.y + dist.y
                            * a.apply(t / T), cameraComponent.bottomLeft.z + dist.z * z.apply(t / T));
                }

            }
        }
    }

    public static Vector3 move(int ind, Array<Room> Rooms) {
        Vector3 VEC = new Vector3(Rooms.get(ind).getOrigin().x + Rooms.get(ind).getWidth() / 2f, Rooms.get(ind).getOrigin().y + Rooms.get(ind).getHeight() / 2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), Rooms.get(ind).getWidth() + Rooms.get(ind).getWidth() / 5f) + 3);

        return VEC;
    }


    public static double angle(double width, double height, double VF) {
        double result = Math.atan((width * Math.tan((VF / 180 * Math.PI) / 2)) / height) * 2 * 180 / Math.PI;

        return result;
    }

    public static double dist(double alpha, double size) {
        double y = size / 2;
        double r = y / (Math.sin((alpha / 180) * Math.PI / 2));
        return Math.sqrt(r * r - y * y);
    }
}


