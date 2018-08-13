package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;
import com.rockbite.inetrnship.ghosthouse.data.Room1;

public class GhostHouse extends ApplicationAdapter {

    SaveDataLoader saveData;
    AssetLoader assetLoader;

    MainUI mainUI;
    MainGame mainGame;
    public static SettingsMusic settingsMusic;
    @Override
    public void create() {
        assetLoader = new AssetLoader();
        mainUI = new MainUI(this);
        mainGame = new MainGame(this);
        //room1=new Room1(0, new Vector2(assetLoader.getRooms().get(0).origin.x, assetLoader.getRooms().get(0).origin.y),assetLoader.getRooms().get(0).width, assetLoader.getRooms().get(0).height);

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
