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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;


public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    public AssetLoader assetLoader;
    Array<Room> rooms;
    CameraComponent cameraComponent;
    public static Vector3 interpolColor = new Vector3(0, 0, 0);
    public static int target = 0;
    public static Array<Integer> sequence;
    public PerspectiveCamera cam;
    Stage stage = new Stage();
    private Vector3 dist = new Vector3(0, 0, 0); //Distance to cover when moving from room to room in all 3 directions
    public boolean isPressed = false;
    public TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));
    Interpolation a = new Interpolation.SwingIn(2); //Interpolation of y
    Interpolation z = new Interpolation.Pow(2); //Interpolation of z
    public CameraInputController camController;
    float t = 0f; // step time
    final float T = 10f; //Total time for moving from room to room
    float scalex = Gdx.graphics.getWidth() / 1920f;
    float scaley = Gdx.graphics.getHeight() / 1080f;

    public void addedToEngine(Engine engine) {
        rooms = assetLoader.getRooms();
        sequence = new Array<Integer>();
        sequence.add(0);
        entities = engine.getEntitiesFor(Family.all(CameraComponent.class).get());
        cam = new PerspectiveCamera(87, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(rooms.get(0).origin.x + rooms.get(0).width / 2f, rooms.get(0).origin.y + rooms.get(0).height / 2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), rooms.get(0).width + rooms.get(0).width / 5f) + 3);

        cameraComponent = new CameraComponent();
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
        stage.getViewport().setCamera(cam);
        cam.near = 1;
    }

    public void moveToNextRoom() {
        for (int i = 0; i < rooms.size; i++) {
            if (rooms.get(i).id == sequence.size) {
                target = i;
                sequence.add(i);
                break;
            }
        }
        // MainUI.Text.setText("Room " + (sequence.size ) + "");
        changeRoomNum(sequence.size);
        leaveRoom();
    }

    public void update(float deltaTime) {
        Entity entity = entities.get(0);
        cameraComponent = cm.get(entity);

        inputHandle();
        interpolHandle();
    }

    public void inputHandle() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            leaveRoom();
        }
    }

    public void leaveRoom() {
        if (target == rooms.size)
            target = 0;
        t = 0;
        isPressed = true;
        cameraComponent.targetVec = (move(target, rooms));

        cameraComponent.bottomLeft.set(cam.position);
        dist.set(cameraComponent.targetVec.x - cameraComponent.bottomLeft.x,
                cameraComponent.targetVec.y - cameraComponent.bottomLeft.y,
                cameraComponent.targetVec.z - cameraComponent.bottomLeft.z);
    }

    public void interpolHandle() {
        if (isPressed) {
            if (cam.position.equals(cameraComponent.targetVec)) {
                isPressed = false;
                t = 0;
            } else {
                float timeRatio = t / T;
                float rDistance = rooms.get(target).light[0] - rooms.get(sequence.get(sequence.size - 2)).light[0];
                float gDistance = rooms.get(target).light[1] - rooms.get(sequence.get(sequence.size - 2)).light[1];
                float bDistance = rooms.get(target).light[2] - rooms.get(sequence.get(sequence.size - 2)).light[2];
                if (t < T) {
                    cam.position.set(cameraComponent.bottomLeft.x + dist.x * t / T, cameraComponent.bottomLeft.y + dist.y
                            * a.apply(t / T), cameraComponent.bottomLeft.z + dist.z * z.apply(t / T));
                    t += Gdx.graphics.getDeltaTime() * 10f;
                    Vector3 roomColor = new Vector3(rooms.get(sequence.get(sequence.size - 2)).light[0] + timeRatio * rDistance, rooms.get(sequence.get(sequence.size - 2)).light[1] + timeRatio * gDistance, rooms.get(sequence.get(sequence.size - 2)).light[2] + timeRatio * bDistance);
                    GhostMesh.lightColor.set(roomColor);
                } else if (t >= T) {
                    GhostMesh.lightColor.set(rooms.get(target).light);
                    t = T;
                    cam.position.set(cameraComponent.bottomLeft.x + dist.x * t / T, cameraComponent.bottomLeft.y + dist.y
                            * a.apply(t / T), cameraComponent.bottomLeft.z + dist.z * z.apply(t / T));
                }

            }
        }
    }

    public static Vector3 move(int ind, Array<Room> Rooms) {
        Vector3 VEC = new Vector3(Rooms.get(ind).origin.x + Rooms.get(ind).width / 2f, Rooms.get(ind).origin.y + Rooms.get(ind).height / 2f, (float) dist(angle(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 67), Rooms.get(ind).width + Rooms.get(ind).width / 5f) + 3);

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

    public void changeRoomNum(int i) {
        if (i < 5) {
            MainUI.Text.removeActor(MainUI.Text.getChildren().get(0));
            MainUI.Text.addActor(new Image(atlas.findRegion("Room" + i)));
            MainUI.Text.getChildren().get(0).setScale(scalex, scaley);
        }
    }
}