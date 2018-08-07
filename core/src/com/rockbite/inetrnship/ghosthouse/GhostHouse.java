package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    MainUI mainUI;
    MainGame mainGame;

    @Override
    public void create() {
        assetLoader = new AssetLoader();
        mainUI = new MainUI(this);
        mainGame = new MainGame(this);
    }

    @Override
    public void render() {
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        mainGame.act(delta);
        mainUI.act();

        mainGame.render();
        mainUI.draw();
    }

    @Override
    public void dispose() {
        mainGame.dispose();
        mainUI.dispose();
    }
}
