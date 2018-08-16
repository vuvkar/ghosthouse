package com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rockbite.inetrnship.ghosthouse.MiniGames.MiniGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;


public class Puzzle extends MiniGame {
    public static int[][] board;
    public static int[][] winningBoard;
    private Texture[] tileTextures;
    private float tileWidth;
    private Actor code;

    public Puzzle() {
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());

        // create environment
        PuzzleActor fog = new PuzzleActor(new Texture(Gdx.files.internal("MiniGame/fog.png")), 0, 0, 0, 0, "fog");
        stage.addActor(fog);
        stage.setKeyboardFocus(fog);


        Texture background = new Texture(Gdx.files.internal("MiniGame/background2.png"));
        float width = Gdx.graphics.getWidth() / 2 - background.getWidth() / 2;
        float height = Gdx.graphics.getHeight() / 2 - background.getHeight() / 2;
        PuzzleActor bg = new PuzzleActor(background, width, height, 0, 0, "bg");

        PuzzleActor code = new PuzzleActor(new Texture(Gdx.files.internal("MiniGame/code.png")), width - 20, height, 0, 0, "code");
        this.code = code;
        stage.addActor(code);
        code.setVisible(false);

        stage.addActor(bg);
        stage.setKeyboardFocus(bg);

        PuzzleActor closeBtn = new PuzzleActor(new Texture(Gdx.files.internal("MiniGame/close.png")), width + background.getWidth() - 60, height + background.getHeight() - 60, 0, 0, "close");
        stage.addActor(closeBtn);
        stage.setKeyboardFocus(closeBtn);

        // create and fill the textures array
        tileTextures = new Texture[8];
        for (int i = 1; i < 9; i++) {
            tileTextures[i - 1] = new Texture(Gdx.files.internal("MiniGame/tile-" + i + ".png"));
        }
        tileWidth = tileTextures[0].getWidth() + 3;

        //generate random board
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++)
            numbers.add(i);
        Collections.shuffle(numbers);

        int tmp = 0;
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = numbers.get(tmp);
                tmp++;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    PuzzleActor tile = new PuzzleActor(tileTextures[board[i][j] - 1], i * tileWidth + width + background.getWidth() / 2 - tileWidth * 1.5f, j * tileWidth + height + background.getHeight() / 2 - tileWidth * 1.5f, i, j, "tile");
                    stage.addActor(tile);
                }
            }
        }

        winningBoard = new int[][]{{7, 4, 1}, {8, 5, 2}, {0, 6, 3}};

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                win();
            }
        }, 1000);
    }

    @Override
    public void load() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        // check for winner
        if (board[0][0] == winningBoard[0][0] && board[1][0] == winningBoard[1][0] && board[2][0] == winningBoard[2][0] &&
                board[0][1] == winningBoard[0][1] && board[1][1] == winningBoard[1][1] && board[2][1] == winningBoard[2][1] &&
                board[0][2] == winningBoard[0][2] && board[1][2] == winningBoard[1][2] && board[2][2] == winningBoard[2][2]) {
            win();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void  win() {
        for(Actor actor: stage.getActors()) {
            actor.setVisible(false);
        }
        code.setVisible(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                end(true);
            }
        }, 2000);
    }
}
