package com.rockbite.inetrnship.ghosthouse.MiniGames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;

public abstract class MiniGame {
    public Stage stage = new Stage();
    public MainGame mainGame;
    public abstract  void create();
    public abstract  void load();
    public abstract void render();

    public void start() {
        MainGame.miniGameOn = true;
        Gdx.input.setInputProcessor(stage);
        load();
    }

    public  void end(boolean hasWon) {
        MainGame.miniGameOn = false;
        Gdx.input.setInputProcessor(MainGame.multiplexer);
        mainGame.getBuilding().getCurrentRoom().miniGameWasClosed(hasWon);

    }
}
