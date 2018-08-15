package com.rockbite.inetrnship.ghosthouse.MiniGames;

import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle.Puzzle;

public abstract class MiniGame {
    public abstract  void create();
    public abstract  void load();
    public abstract void render();
    public  void start() {
        MainGame.miniGameOn = true;
        load();
    }
    public  void end() {
        MainGame.miniGameOn = false;
    }
}
