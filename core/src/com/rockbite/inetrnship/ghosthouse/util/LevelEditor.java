package com.rockbite.inetrnship.ghosthouse.util;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.rockbite.inetrnship.ghosthouse.AssetLoader;
import com.rockbite.inetrnship.ghosthouse.GhostHouse;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.data.FromAtlasItem;
import com.rockbite.inetrnship.ghosthouse.data.GhostMesh;
import com.rockbite.inetrnship.ghosthouse.data.Item;
import com.rockbite.inetrnship.ghosthouse.ecs.components.TextureComponent;
import com.sun.tools.javac.jvm.Items;
import javafx.scene.Group;

public class LevelEditor implements ApplicationListener {
    Texture texture;
   // SpriteBatch batch;
    OrthographicCamera camera;
    //CameraController controller;
    GestureDetector gestureDetector;
    FromAtlasItem loader;
    GhostHouse house;
    Group inventory;
    MainUI mainUI;
    TextureComponent textureComponent;
    Vector3 target = new Vector3(0, 0, 0);

    Array<Item> items;
    MainGame mainGame;


    // Main
    @Override
    public void create() {
        //texture = new Texture("textures/tumbSHka.png");
        house = new GhostHouse();
        house.setM_ui(new MainUI(house));
        mainGame = new MainGame(house);
        mainUI = new MainUI(house);

        texture = new Texture(Gdx.files.internal("packed/r1Items.png"));
      //  batch = new SpriteBatch();

        inventory = new Group();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //controller = new CameraController();
        //gestureDetector = new GestureDetector(20, 0.5f, 2, 0.15f, controller);

        // Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainGame.render();

        mainUI.draw();
        camera.update();
        mainUI.act();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        texture.dispose();
        //batch.dispose();
    }
}
