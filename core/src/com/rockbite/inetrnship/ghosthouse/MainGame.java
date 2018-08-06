package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.data.Room1;
import com.rockbite.inetrnship.ghosthouse.ecs.components.*;

import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.RenderSystem;
import com.rockbite.inetrnship.ghosthouse.util.HelperClass;


public class MainGame {

    private GhostHouse ghostHouse;
    private Engine engine;
    private CameraSystem cameraSystem;
    private RenderSystem renderSystem;
    private AssetLoader assetLoader;
    private InputController inputController;
    private Ray ray;

    private Room currentRoom;

    private Array<Room1> rooms;
    InputMultiplexer multiplexer = new InputMultiplexer();

    Vector3[] point = new Vector3[2];

    Entity Cam;

    private GhostBuilding building;
    private GhostMesh meshok;
    private BoundingBox box = new BoundingBox();

    public void act(float delta) {
        engine.update(delta);
    }


    public MainGame(GhostHouse ghostHouse) {

        this.ghostHouse = ghostHouse;

        assetLoader = new AssetLoader();
        rooms = assetLoader.getRooms();

        building = new GhostBuilding(rooms);
        meshok = new GhostMesh(building.getAllRects());

        engine = new Engine();

        cameraSystem = new CameraSystem();
        Cam = new Entity();
        Cam.add(new CameraComponent());
        engine.addSystem(cameraSystem);
        engine.addEntity(Cam);
        inputController = new InputController(meshok, cameraSystem, ghostHouse);
        renderSystem = new RenderSystem();
        renderSystem.mesh = meshok;
        engine.addSystem(renderSystem);
        point[0] = new Vector3(-2.5f, -2.5f, 0);
        point[1] = new Vector3(30, 20, 3f);
        box.set(point);


        Vector3 ghostPosition = new Vector3();
        for (Room room : rooms) {
            for (Entity entity : room.items) {
                engine.addEntity(entity);
            }
            if(room.id == 0)
            {
                ghostPosition.set(room.origin.x + GhostBuilding.WALL_HEIGHT, room.origin.y, building.BUILDING_DEPTH);
            }
        }

        multiplexer.addProcessor( ghostHouse.m_ui);
        multiplexer.addProcessor(inputController);
        Gdx.input.setInputProcessor(multiplexer);




        Entity ghost = HelperClass.createGhost(ghostPosition);
        engine.addEntity(ghost);

    }

    public void render() {

        cameraSystem.cam.update();

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // first render sky
        // TODO: render sky

        // then render building walls
        // TODO: render building
        meshok.render(cameraSystem.cam);

        // then render decorations/characters and items
        // TODO: render the rest

        // DO postprocessing of FBO and render it to screen
        // TODO: final render


    }


    public void dispose() {
    }
}
