package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    public MainUI mainUI;
    public MainGame mainGame;
    public static SettingsMusic settingsMusic;
    @Override
    public void create() {
        assetLoader = new AssetLoader();
        mainUI = new MainUI(this);
        mainGame = new MainGame(this);
        mainGame.startGame();
    }

    @Override
    public void render() {
        //mainUI.inventory.setDebug(true, true);
        float delta = Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
        if(mainUI.gameison){
            mainGame.act(delta);
        }

        mainUI.act();

        mainGame.render();
        mainUI.draw();
//        if (mainGame.miniGameOn) {
//            mainGame.miniGame.render();
//        }
        if (mainGame.inputController.isMoving) {
            mainGame.inputController.moveCharacter();
        }
        if(MainUI.settingson){
            MainUI.settingsMusic.draw();

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
