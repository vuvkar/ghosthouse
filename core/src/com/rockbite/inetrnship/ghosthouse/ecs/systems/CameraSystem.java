package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;


public class CameraSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);

    Entity entity;
    CameraComponent cameraComponent;

    public PerspectiveCamera Cam;

    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room to room in all 3 directions
    public boolean isPressed = false;

    Interpolation a = new Interpolation.SwingIn(2); //x y axis
    Interpolation z = new Interpolation.Pow(2); //z axis

    float t = 0f; // step time
    final float T = 10f; //Total time for moving from room to room
    room[] rooms = new room[4];

    public void addedToEngine(Engine engine) {
        rooms[0] = new room(new Vector2(0, 0), new Vector2(11, 10));
        rooms[1] = new room(new Vector2(11, 0), new Vector2(18, 10));
        rooms[2] = new room(new Vector2(0, 10), new Vector2(14, 9));
        rooms[3] = new room(new Vector2(14, 10), new Vector2(15, 9));

        entities = engine.getEntitiesFor(Family.all(CameraComponent.class).get());

        Cam = new PerspectiveCamera();
        Cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Cam.position.set(rooms[0].position.x + rooms[0].dimension.x / 2f, rooms[0].position.y + rooms[0].dimension.y / 2f + rooms[0].dimension.y / 5, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), rooms[0].dimension.x + rooms[0].dimension.x / 3f) + 3); //Set camera position in the first room
        Cam.rotate(10, -1, 0, 0);

        CameraComponent cameraComponent = new CameraComponent();
    }

    public void update(float deltaTime) {
        Entity entity = entities.get(0);
        cameraComponent = cm.get(entity);

        inputHandle();
        interpolHandle();
    }

    public void inputHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            t = 0;
            isPressed = true;
            cameraComponent.targetVec = (move(1, rooms));
            cameraComponent.middlePoint.set(Cam.position);
            dist.set(cameraComponent.targetVec.x - cameraComponent.middlePoint.x, cameraComponent.targetVec.y - cameraComponent.middlePoint.y, cameraComponent.targetVec.z - cameraComponent.middlePoint.z);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            t = 0;
            isPressed = true;
            cameraComponent.targetVec = (move(0, rooms));
            cameraComponent.middlePoint.set(Cam.position);
            dist.set(cameraComponent.targetVec.x - cameraComponent.middlePoint.x, cameraComponent.targetVec.y - cameraComponent.middlePoint.y, cameraComponent.targetVec.z - cameraComponent.middlePoint.z);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            t = 0;
            isPressed = true;
            cameraComponent.targetVec = (move(2, rooms));
            cameraComponent.middlePoint.set(Cam.position);
            dist.set(cameraComponent.targetVec.x - cameraComponent.middlePoint.x, cameraComponent.targetVec.y - cameraComponent.middlePoint.y, cameraComponent.targetVec.z - cameraComponent.middlePoint.z);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            t = 0;
            isPressed = true;
            cameraComponent.targetVec = (move(3, rooms));
            cameraComponent.middlePoint.set(Cam.position);
            dist.set(cameraComponent.targetVec.x - cameraComponent.middlePoint.x, cameraComponent.targetVec.y - cameraComponent.middlePoint.y, cameraComponent.targetVec.z - cameraComponent.middlePoint.z);
        }
    }

    public void interpolHandle() {
        if (isPressed) {
            if (Cam.position.equals(cameraComponent.targetVec)) {
                isPressed = false;
                t = 0;
            } else {
                if (t < T) {


                    Cam.position.set(cameraComponent.middlePoint.x + dist.x * t / T, cameraComponent.middlePoint.y + dist.y * a.apply(t / T), cameraComponent.middlePoint.z + dist.z * z.apply(t / T));
                    t += Gdx.graphics.getDeltaTime() * 20f;
                } else if (t >= T) {
                    t = T;
                    Cam.position.set(cameraComponent.middlePoint.x + dist.x * t / T, cameraComponent.middlePoint.y + dist.y * a.apply(t / T), cameraComponent.middlePoint.z + dist.z * z.apply(t / T));
                }

            }
        }
    }

    public static Vector3 move(int ind, room[] rooms) {
        Vector3 VEC = new Vector3(rooms[ind].position.x + rooms[ind].dimension.x / 2f, rooms[ind].position.y + rooms[ind].dimension.y / 2f + rooms[ind].dimension.y / 5, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), rooms[ind].dimension.x + rooms[ind].dimension.x / 3f) + 3);
        System.out.println(VEC);
        return VEC;
    }


    public static double angle(double width, double height, double VF) {
        double result = Math.atan((width * Math.tan((VF / 180 * Math.PI) / 2)) / height) * 2 * 180 / Math.PI;

        return result;
    }

    public static double dist(double alpha, double size) {
        double y = size / 2; // side
        double r = y / (Math.sin((alpha / 180) * Math.PI / 2)); // radius
        return Math.sqrt(r * r - y * y);
    }
}


class room {
    Vector2 position;
    Vector2 dimension;

    public room(Vector2 p, Vector2 d) {
        this.position = p;
        this.dimension = d;
    }
}