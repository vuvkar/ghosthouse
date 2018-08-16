package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    static boolean isLoaded = true;

    public MainUI mainUI;
    public MainGame mainGame;
    public SaveDataLoader saveDataLoader;
    @Override
    public void create() {
        assetLoader = new AssetLoader();
        mainUI = new MainUI(this);
        mainGame = new MainGame(this);
        mainGame.startGame();
        saveDataLoader = new SaveDataLoader();
        saveDataLoader.load();
    }

    @Override
    public void render() {
        if (isLoaded) {
            //mainUI.inventory.setDebug(true, true);
            float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
            if (mainUI.gameison) {
                mainGame.act(delta);
            }

            mainUI.act();

            mainGame.render();
            mainUI.draw();

            if (mainGame.inputController.isMoving) {
                mainGame.inputController.moveCharacter();
            }
            if (MainUI.settingson) {
                MainUI.settingsMusic.draw();

            }
        }
    }

    @Override
    public void dispose() {
        mainGame.dispose();
        mainUI.dispose();
    }

    public void resize(int width, int height) {
        mainUI.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainUI.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (MainGame.miniGameOn)
            mainGame.getBuilding().getCurrentRoom().miniGame.stage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (MainUI.settingson) {
            MainUI.settingsMusic.stage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

    }
}