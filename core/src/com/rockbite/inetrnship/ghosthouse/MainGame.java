package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.data.Room;
import com.rockbite.inetrnship.ghosthouse.ecs.systems.CameraSystem;

public class MainGame {

    private GhostHouse gh;
    private Engine engine;
    private CameraSystem cameraSystem;

    private Mesh building;

    public MainGame(GhostHouse gh) {
        this.gh = gh;

        //dummyComment

        preProcessRoomData(gh.assetLoader.getRooms());

        engine = new Engine();

        cameraSystem = new CameraSystem();
    }

    public void preProcessRoomData(Array<Room> rooms) {
        //Todo: Hi Vahe
    }

    public void act(float delta) {
        engine.update(delta);
    }

    public void render() {
        // this all shuld eventually be rendered to FBO actually

        // first render sky
        // TODO: render sky

        // then render building walls
        // TODO: render building

        // then render decorations/characters and items
        // TODO: render the rest

        // DO postprocessing of FBO and render it to screen
        // TODO: final render
    }

    public void dispose() {

    }
}
