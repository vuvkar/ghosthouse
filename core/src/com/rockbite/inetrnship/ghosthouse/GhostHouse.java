package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    MainUI m_ui;
    MainGame m_game;

    @Override
    public void create() {
        assetLoader = new AssetLoader();
        m_ui = new MainUI(this);
        m_game = new MainGame(this);
    }

    @Override
    public void render() {
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        m_game.act(delta);
        m_ui.act(delta);

        m_game.render();
        m_ui.draw();
    }

    @Override
    public void dispose() {
        m_game.dispose();
        m_ui.dispose();
    }
}
