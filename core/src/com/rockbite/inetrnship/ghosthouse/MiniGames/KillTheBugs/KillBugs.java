package com.rockbite.inetrnship.ghosthouse.MiniGames.KillTheBugs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rockbite.inetrnship.ghosthouse.MiniGames.MiniGame;

public class KillBugs extends MiniGame {

    private Stage stage;
    private int numOfBugs = 10 ;
    private Texture bugImage;
    private float width;
    private float height;
    public static float bgWidth;
    public static float bgHeight;
    public static float timer = 0;
    private boolean bugsCreated = false;
    public static int killedBugs = 0;
    public static boolean gameStarted = false;
    public static boolean gameOver = false;
    public static boolean win = false;
    public static boolean restart = false;

    private Array<BugsActor> bugs = new Array<BugsActor>();
    private BugsActor bg;
    private BugsActor msg;
    private BugsActor youlost;
    private BugsActor restartBtn;
    private BugsActor closeBtn;
    boolean tmp1 = true;
    Label.LabelStyle label1Style;
    public static Label killBugsText;
    public static Label timerText;
    BitmapFont font;

    public KillBugs() {
        start();
    }

    private void start() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        bugImage = new Texture(Gdx.files.internal("MiniGame/bug_1_1.png"));

        font = new BitmapFont(Gdx.files.internal("MiniGame/font.fnt"));
        label1Style = new Label.LabelStyle();
        label1Style.font = font;
        label1Style.fontColor = Color.WHITE;

        createGame();
    }

    private void createGame() {
        gameStarted = false;
        win = false;
        gameOver = false;
        killedBugs = 0;
        timer = 0;

        // create environment
        BugsActor fog = new BugsActor(new Texture(Gdx.files.internal("MiniGame/fog.png")), 0,0, "fog", 1);
        stage.addActor(fog);

        Texture background = new Texture(Gdx.files.internal("MiniGame/bugsBG.png"));
        KillBugs.bgWidth = background.getWidth();
        KillBugs.bgHeight = background.getHeight();
        width = Gdx.graphics.getWidth()/2-bgWidth/2;
        height = Gdx.graphics.getHeight()/2-bgHeight/2-40;
        bg = new BugsActor(background, width, height, "bg", 1);
        stage.addActor(bg);
        bg.setVisible(false);

        //create time bar
        BugsActor timeBar = new BugsActor(new Texture("MiniGame/bugsTimeBar.png"), width-85, height+bgHeight-10, "bar", 1);
        stage.addActor(timeBar);

        //killed bugs text
        killBugsText = new Label("0/10",label1Style);
        killBugsText.setPosition(width+110, height+bgHeight+20);
        stage.addActor(killBugsText);

        //timer text
        timerText = new Label("7",label1Style);
        timerText.setPosition(width+bgWidth-100, height+bgHeight+20);
        stage.addActor(timerText);

        //message box
        msg = new BugsActor(new Texture(Gdx.files.internal("MiniGame/bugsScreenKill.png")), width-55, height, "message", 1);
        stage.addActor(msg);
    }

    @Override
    public void render () {
        timer+=Gdx.graphics.getDeltaTime();

        if(gameStarted && !bugsCreated) {
            createBugs();
            bugsCreated = true;
            startTimer();
            bg.setVisible(true);

            //close button
            closeBtn = new BugsActor(new Texture("MiniGame/bugsBtnExit.png"), width+msg.getWidth()-125, height+msg.getHeight()-105, "close", 1);
            stage.addActor(closeBtn);

        }

        //win
        if(killedBugs>=numOfBugs && !gameOver && !win) {
            System.out.println("YOU WIN!!!");
            BugsActor youwin = new BugsActor(new Texture(Gdx.files.internal("MiniGame/bugsScreenWin.png")), width-55, height, "win", 1);
            stage.addActor(youwin);
            win = true;
            bg.setVisible(false);
            closeBtn.toFront();
        }

        if(gameOver && tmp1) {
            System.out.println("GAME OVER!!!");
            youlost = new BugsActor(new Texture(Gdx.files.internal("MiniGame/bugsScreenGameOver.png")), width-55, height, "die", 1);
            stage.addActor(youlost);
            closeBtn.toFront();
            restartBtn = new BugsActor(new Texture(Gdx.files.internal("MiniGame/bugsBtnRestart.png")), width+80, height + 110, "gameover", 1);
            stage.addActor(restartBtn);
            bg.setVisible(false);
            tmp1 = false;
        }

        //restart game
        if(restart) {
            restartGame();
            restart = false;
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void restartGame() {
        removeBugs();
        youlost.remove();
        restartBtn.remove();
        win = false;
        gameOver = false;
        gameStarted = true;
        createBugs();
        bugsCreated = true;
        killedBugs = 0;
        startTimer();
        tmp1 = true;
        killBugsText.setText("0/10");
        timerText.setText("7");
        bg.setVisible(true);
    }

    private void startTimer() {
        BugsActor timerImg = new BugsActor(new Texture(Gdx.files.internal("MiniGame/transparent.png")), 0,0, "timer", 1);
        stage.addActor(timerImg);
    }

    private void createBugs() {
        int dir = 1;
        for (int i=0; i<numOfBugs; i++) {
            if(Math.random()>0.5f)
                dir*=-1;
            BugsActor bug;
            if(dir == 1)
                bug = new BugsActor(bugImage, width, height+(bgHeight-bugImage.getHeight())*(float)Math.random(), "bug", dir);
            else
                bug = new BugsActor(bugImage, width+bgWidth, height+(bgHeight-bugImage.getHeight())*(float)Math.random(), "bug", dir);
            bugs.add(bug);
            stage.addActor(bug);
        }
    }

    private void removeBugs() {
        for (int i=0; i<bugs.size; i++) {
            bugs.get(i).remove();
        }
    }

}