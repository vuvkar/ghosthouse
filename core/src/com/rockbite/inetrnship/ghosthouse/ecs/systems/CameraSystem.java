package com.rockbite.inetrnship.ghosthouse.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;

import static jdk.nashorn.internal.objects.NativeBoolean.valueOf;

public class CameraSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);

    Entity entity;
    CameraComponent Cameracom;

    public PerspectiveCamera Cam;


    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room to room in all 3 directions
    public boolean ispressed = false;

    Interpolation a = new Interpolation.SwingIn(2);
    Interpolation z = new Interpolation.Pow(2);
    float Time;
    float t = 0f;
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

        Cam.position.set(rooms[0].Pos.x + rooms[0].dim.x / 2f, rooms[0].Pos.y + rooms[0].dim.y / 2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), rooms[0].dim.x + rooms[0].dim.x / 3f) + 3); //Set camera position in the first room


        CameraComponent Cameracom = new CameraComponent();

    }

    public void update(float deltaTime) {

        Entity entity = entities.get(0);
        Cameracom = cm.get(entity);

        inputhandle();
        interpolhandle();

    }

    public void inputhandle() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            Cam.position.x += 0.5;

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            Cam.position.y += 0.5;

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(1, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);


        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {

            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(0, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(2, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(3, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);

        }
    }

    public void interpolhandle() {
        if (ispressed) {
            if (Cam.position.equals(Cameracom.targetvec)) {
                ispressed = false;
                t = 0;
            } else {
                if (t < T) {
                    t += Gdx.graphics.getDeltaTime() * 18;

                    Cam.position.set(Cameracom.LL.x + dist.x * t / T, Cameracom.LL.y + dist.y * a.apply(t / T), Cameracom.LL.z + dist.z * z.apply(t / T));
                } else if (t >= T) {
                    t = T;
                    Cam.position.set(Cameracom.LL.x + dist.x * t / T, Cameracom.LL.y + dist.y * a.apply(t / T), Cameracom.LL.z + dist.z * z.apply(t / T));
                }
            }
        }
    }

    public static Vector3 move(int ind, room[] rooms) {

        Vector3 VEC = new Vector3(rooms[ind].Pos.x + rooms[ind].dim.x / 2f, rooms[ind].Pos.y + rooms[ind].dim.y / 2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), rooms[ind].dim.x + rooms[ind].dim.x / 3f) + 3);

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


class room {
    Vector2 Pos;
    Vector2 dim;

    public room(Vector2 p, Vector2 d) {
        this.Pos = p;
        this.dim = d;
    }
}