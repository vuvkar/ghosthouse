package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.components.PositionComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.RenderSystem;
import com.rockbite.inetrnship.ghosthouse.util.HelperClass;


public class MainGame {
    public Entity ghost = HelperClass.createGhost(new Vector3(0, 0, 0));
    public GhostHouse ghostHouse;
    private Engine engine;
    public CameraSystem cameraSystem;
    private RenderSystem renderSystem;
    private AssetLoader assetLoader;
    public InputController inputController;
    private SaveDataLoader saveDataLoader;
    private Music music;

    public static boolean miniGameOn = false;
    private Array<Room> rooms;
    public static InputMultiplexer multiplexer = new InputMultiplexer();

    Entity cam;

    private GhostBuilding building;
    public GhostMesh meshok;


    public void act(float delta) {
        engine.update(delta);
    }

    public GhostBuilding getBuilding() {
        return building;
    }

    public MainGame(GhostHouse ghostHouse) {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundmusic.mp3"));
        saveDataLoader = new SaveDataLoader();

        this.ghostHouse = ghostHouse;

        assetLoader = ghostHouse.assetLoader;
        rooms = assetLoader.getRooms();

        for(Room room: rooms) {
            room.mainGame = this;
        }

        building = new GhostBuilding(rooms, assetLoader);
        meshok = new GhostMesh(building.getAllRects(), assetLoader);

        engine = new Engine();

        cameraSystem = new CameraSystem();
        cameraSystem.assetLoader = assetLoader;

        cam = new Entity();
        cam.add(new CameraComponent());
        engine.addSystem(cameraSystem);
        engine.addEntity(cam);
        inputController = new InputController(meshok, cameraSystem, ghostHouse);
        renderSystem = new RenderSystem();
        renderSystem.mesh = meshok;
        engine.addSystem(renderSystem);

        Vector3 ghostPosition = new Vector3();
        for (Room room : rooms) {
            for (Entity entity : room.items) {
                engine.addEntity(entity);
            }
            for (Entity entity : room.models) {
                engine.addEntity(entity);
            }
            if (room.id == 0) {
                ghostPosition.set(room.origin.x + GhostBuilding.WALL_HEIGHT, room.origin.y, building.BUILDING_DEPTH);
            }
        }

        ghost.getComponent(PositionComponent.class).setXYZ(ghostPosition);
        multiplexer.addProcessor(ghostHouse.mainUI);
        multiplexer.addProcessor(inputController);
        Gdx.input.setInputProcessor(multiplexer);


        engine.addEntity(ghost);

    }

    public void moveToRoom(int count) {
        for(int i = 0; i < count; i++) {
            getBuilding().getCurrentRoom().leaveRoom();
        }
    }

    public void leavedRoom() {
        cameraSystem.moveToNextRoom();
        building.moveToNextRoom();
        building.getCurrentRoom().roomStarted();
        saveDataLoader.save(building.getCurrentRoom().id);
    }

    public  void  startGame() {
        building.getCurrentRoom().roomStarted();
        music.setLooping(true);
        music.play();
       // Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                moveToRoom(3);
//            }
//        }, 1000);

    }

    public void render() {

        cameraSystem.cam.update();

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        // first render sky
        // TODO: render sky

        // then render building walls and items
        meshok.render(cameraSystem.cam);

        if (miniGameOn) {
            building.getCurrentRoom().miniGame.render();
        }

     DialogSystem.dialogSystem.render();

        // DO postprocessing of FBO and render it to screen
        // TODO: final render

    }

    public void dispose() {
        meshok.dispose();
        assetLoader.dispose();
    }
}
