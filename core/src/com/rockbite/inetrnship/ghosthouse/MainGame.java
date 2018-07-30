package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuildingMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;

public class MainGame {

    private GhostHouse gh;
    private Engine engine;
    private CameraSystem cameraSystem;

    Entity Cam;
    public CameraInputController camController;

    private GhostBuilding building;
    private GhostBuildingMesh buildingMesh;




    public void act(float delta){
        engine.update(delta);
    }

    public MainGame(GhostHouse gh) {
        this.gh = gh;

        engine = new Engine();

        engine = new Engine();
        cameraSystem = new CameraSystem();

        Cam = new Entity();
        Cam.add(new CameraComponent());
        engine.addSystem(cameraSystem);
        engine.addEntity(Cam);



        camController = new CameraInputController(cameraSystem.Cam);

        Gdx.input.setInputProcessor(camController);

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        float height = 5;
        float width = 5;

        Array<Room> rooms = new Array<Room>();

                Room room = new Room(0, new Vector2(  0, 0), width, height);
                rooms.add(room);
         // }


        building = new GhostBuilding(rooms);
        buildingMesh = new GhostBuildingMesh(building.getAllRects());

    }

    public void render () {
        cameraSystem.Cam.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // this all shuld eventually be rendered to FBO actually

        // first render sky
        // TODO: render sky

        // then render building walls
        // TODO: render building
        buildingMesh.render(cameraSystem.Cam);

        // then render decorations/characters and items
        // TODO: render the rest

        // DO postprocessing of FBO and render it to screen
        // TODO: final render

    }

    public void dispose () {

    }
}
