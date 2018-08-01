package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.ecs.components.CameraComponent;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;


public class MainGame {

    private GhostHouse ghostHouse;
    private Engine engine;
    private CameraSystem cameraSystem;
    private AssetLoader assetLoader;

    Entity Cam;
    public CameraInputController camController;

    private GhostBuilding building;
    private GhostMesh buildingMesh;

    public void act(float delta) {
        engine.update(delta);
    }

    public MainGame(GhostHouse ghostHouse) {
        this.ghostHouse = ghostHouse;

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

        assetLoader = new AssetLoader();
        building = new GhostBuilding(assetLoader.getRooms());
        buildingMesh = new GhostMesh(building.getAllRects());
    }

    public void render() {
        cameraSystem.Cam.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        Gdx.gl.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        Gdx.gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
        // this all should eventually be rendered to FBO actually

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

    public void dispose() {
    }
}
