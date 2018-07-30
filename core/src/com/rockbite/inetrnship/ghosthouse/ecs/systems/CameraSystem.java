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

public class CameraSystem extends EntitySystem{

    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);

    Entity entity;
    CameraComponent Cameracom;

    public PerspectiveCamera Cam;


    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room to room in all 3 directions
    public boolean ispressed = false;

    Interpolation a = new Interpolation.SwingIn(2);
    Interpolation z = new Interpolation.Pow(2);

    float t=0f;
    int T=0; //Total time for moving from room to room
    room[] rooms = new room[4];

    public void addedToEngine(Engine engine) {

        rooms[0] = new room(new Vector2(0, 0), new Vector2(11, 10));
        rooms[1] = new room(new Vector2(12, 0), new Vector2(18, 10));
        rooms[2] = new room(new Vector2(0, 11), new Vector2(14, 9));
        rooms[3] = new room(new Vector2(15, 11), new Vector2(15, 9));

        entities = engine.getEntitiesFor(Family.all(CameraComponent.class).get());

        //Camera setup
        Cam=new PerspectiveCamera();
        Cam = new PerspectiveCamera(80, 20, 14);
        Cam.near = 1f;
        Cam.far = 300f;
        Cam.position.set(rooms[0].Pos.x + rooms[0].dim.x / 2f, rooms[0].Pos.y + rooms[0].dim.y / 2f, rooms[0].dim.x); //Set camera position in the first room



        CameraComponent Cameracom = new CameraComponent();

    }
    public void update(float deltaTime){

        Entity entity = entities.get(0);
        Cameracom = cm.get(entity);

        inputhandle();
        interpolhandle();

    }

    public void inputhandle(){

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(1, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);
            T=(int)(dist.len()*60/18.5f);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {

            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(0, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);
            T=(int)(dist.len()*60/18.5f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(2, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);
            T=(int)(dist.len()*60/18.5f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            t = 0;
            ispressed = true;
            Cameracom.targetvec = (move(3, rooms));
            Cameracom.LL.set(Cam.position);
            dist.set(Cameracom.targetvec.x - Cameracom.LL.x, Cameracom.targetvec.y - Cameracom.LL.y, Cameracom.targetvec.z - Cameracom.LL.z);
            T=(int)(dist.len()*60/18.5f);
        }
    }
    public void interpolhandle(){
        if (ispressed) {
            if (Cam.position.equals(Cameracom.targetvec)) {
                ispressed = false;
                t = 0;
            } else {
                if (t <= T) {
                    t += 1;
                    Cam.position.set(Cameracom.LL.x + dist.x * t / T, Cameracom.LL.y + dist.y * a.apply(t / T), Cameracom.LL.z + dist.z * z.apply(t / T));
                }
            }
        }
    }
    public static Vector3 move(int ind, room[] rooms) {

        Vector3 VEC = new Vector3(rooms[ind].Pos.x + rooms[ind].dim.x / 2f, rooms[ind].Pos.y + rooms[ind].dim.y / 2f, rooms[ind].dim.x);

        return VEC;
    }

}

class room{
    Vector2 Pos;
    Vector2 dim;

    public room(Vector2 p, Vector2 d){
        this.Pos=p;
        this.dim=d;
    }
}