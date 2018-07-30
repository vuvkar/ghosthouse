package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    MainUI ui;
    MainGame game;

    @Override
    public void create() {
        assetLoader = new AssetLoader();
        ui = new MainUI(this);
        game = new MainGame(this);
    }

    @Override
    public void render() {
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        game.act(delta);
        ui.act(delta);

        game.render();
        ui.draw();
    }

    @Override
    public void dispose() {
        game.dispose();
        ui.dispose();
    }
}
