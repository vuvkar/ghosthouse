package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuildingMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;

public class MainGame {

    private GhostHouse gh;
    private Engine engine;
    private CameraSystem cameraSystem;

    public CameraInputController camController;

    private GhostBuilding building;
    private GhostBuildingMesh buildingMesh;

    PerspectiveCamera camera;

    public void act(float delta){
        engine.update(delta);
    }

    public MainGame(GhostHouse gh) {
        this.gh = gh;

        engine = new Engine();

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 3f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        camController = new CameraInputController(camera);

        Gdx.input.setInputProcessor(camController);

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        Array<Room> rooms = new Array<Room>();
        Room r1 = new Room(0, new Vector2(1, 5), 1, 1);
        Room r2 = new Room(1, new Vector2(3, 2), 1, 4);
        Room r3 = new Room(2, new Vector2(5, 3), 2, 2);
        Room r4 = new Room(3, new Vector2(8, 1), 4, 5);
        Room r5 = new Room(4, new Vector2(1, 3), 1, 1);
        rooms.add(r1, r2, r3, r4);
        rooms.add(r5);

        building = new GhostBuilding(rooms);
        buildingMesh = new GhostBuildingMesh(building.getAllRects());

    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // this all shuld eventually be rendered to FBO actually

        // first render sky
        // TODO: render sky

        // then render building walls
        // TODO: render building
        buildingMesh.render(camera);

        // then render decorations/characters and items
        // TODO: render the rest

        // DO postprocessing of FBO and render it to screen
        // TODO: final render

    }

    public void dispose () {

    }
}
