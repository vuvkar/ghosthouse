package com.rockbite.inetrnship.ghosthouse.MiniGames.Lock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.MainGame;
import com.rockbite.inetrnship.ghosthouse.MainUI;
import com.rockbite.inetrnship.ghosthouse.MiniGames.MiniGame;

public class Lock extends MiniGame {
    public static Array<Integer> combination = new Array<Integer>();
    public static Array<Integer> correctCombination = new Array<Integer>();

    private BitmapFont font;
    private Label.LabelStyle label1Style;
    public static Label code;
    public static boolean KEY = false;
    public static boolean isDoorOpen = false;
    public static LockActor light1;
    public static LockActor light2;

    public Lock() {
        create();
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        correctCombination.add(2);
        correctCombination.add(7);
        correctCombination.add(5);
        correctCombination.add(8);

        font = new BitmapFont(Gdx.files.internal("MiniGame/font.fnt"));
        label1Style = new Label.LabelStyle();
        label1Style.font = font;
        Color c = new Color(202/255f, 240/255f, 1,1);
        label1Style.fontColor = c;

        // create environment
        LockActor fog = new LockActor(new Texture(Gdx.files.internal("MiniGame/fog.png")), 0,0, "fog", 0);
        stage.addActor(fog);

        Texture background = new Texture(Gdx.files.internal("MiniGame/lockBG.png"));
        float width = Gdx.graphics.getWidth()/2f-background.getWidth()/2f;
        float height = Gdx.graphics.getHeight()/2f-background.getHeight()/2f;
        LockActor bg = new LockActor(background, width, height, "bg", 0);
        stage.addActor(bg);
        LockActor codeBG = new LockActor(new Texture(Gdx.files.internal("MiniGame/codeBG.png")), Gdx.graphics.getWidth()/2f-125, Gdx.graphics.getHeight()/2f+140, "bar", 0);
        stage.addActor(codeBG);

        final LockActor close = new LockActor(new Texture(Gdx.files.internal("MiniGame/bugsBtnExit.png")), width+background.getWidth()-45, height+background.getHeight()-40, "close", 0);
        close.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return true; }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    for (int i = 0; i < close.getStage().getActors().size; i++) {
                        close.getStage().getActors().get(i).setVisible(false);
                    }
                    end();
                }
            });
        stage.addActor(close);

        light1 = new LockActor(new Texture(Gdx.files.internal("MiniGame/lightR.png")), width+background.getWidth()-130, height+background.getHeight()-60, "light", 0);
        light2 = new LockActor(new Texture(Gdx.files.internal("MiniGame/lightG.png")), width+background.getWidth()-130, height+background.getHeight()-60, "light", 0);
        stage.addActor(light1);
        stage.addActor(light2);


        light2.setVisible(false);

        //create code
        int a = 9;
        for (int i=0; i<3; i++) {
            for (int j=3; j>0; j--) {
                LockActor btn = new LockActor(new Texture("MiniGame/lockBtn_"+a+"_normal.png"),105*j+width+background.getWidth()/2f-265,105*i+height+background.getHeight()/2f-190,"button", a);
                a--;
                stage.addActor(btn);
            }
        }

        code = new Label("",label1Style);
        code.setPosition(Gdx.graphics.getWidth()/2f-90, height+background.getHeight()-code.getHeight()-25);
        stage.addActor(code);

    }

    @Override
    public void load() {
        if(!KEY)
            DialogSystem.dialogSystem.startDialog("You don't have enough info yet,\nlet's leave this for later.", 5, 0.6f, 0);
    }

    @Override
    public void render() {
        if(Gdx.input.isKeyPressed(Input.Keys.K))
            KEY = true;
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
