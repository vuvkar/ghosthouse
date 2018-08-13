package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;

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
        mainUI.inventory.setDebug(true, true);
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        mainGame.act(delta);
        mainUI.act();

        mainGame.render();
        mainUI.draw();
        if (mainGame.miniGameOn) {
            mainGame.miniGame.render();
        }
        if (mainGame.inputController.isMoving) {
            mainGame.inputController.moveCharacter();
        }


    }

    @Override
    public void dispose() {
        mainGame.dispose();
        mainUI.dispose();
    }

    public void resize(int width, int height) {
        mainUI.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (MainGame.miniGameOn)
            Puzzle.stage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        System.out.println(Gdx.graphics.getWidth());

    }
}
