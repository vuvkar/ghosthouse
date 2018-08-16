package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rockbite.inetrnship.ghosthouse.MiniGames.Lock.LockActor;

public class DialogSystem {

    private Stage stage;
    private BitmapFont font;
    private Label.LabelStyle label1Style;
    private Label dialogText;
    private LockActor dialogBox;
    private float time = 0;
    private float delay = 0;
    private float duration = 0;
    public boolean isDialogOn = false;
    InputProcessor zibil;
    private boolean startCountdown = false;

    public static DialogSystem dialogSystem = new DialogSystem();

    public DialogSystem() {
        stage = new Stage(new ScreenViewport());

        font = new BitmapFont(Gdx.files.internal("MiniGame/font.fnt"));
        label1Style = new Label.LabelStyle();
        label1Style.font = font;
        label1Style.fontColor = new Color(202/255f, 240/255f, 1,1);

        dialogBox = new LockActor(new Texture(Gdx.files.internal("MiniGame/dialog.png")), Gdx.graphics.getWidth()/2f-300, Gdx.graphics.getHeight() - 140f, "bar", 0);
        stage.addActor(dialogBox);
        dialogBox.setVisible(false);
        dialogText = new Label("some text",label1Style);
        dialogText.setPosition(dialogBox.getX()+20, Gdx.graphics.getWidth()/2f-30);
        dialogText.setFontScale(0.8f);
        dialogText.setVisible(false);
    }

    public void startDialog(String text, float duration, float fontSize, float delay) {
        startCountdown = true;
        this.duration = duration;
        this.delay = delay;
        dialogText.setFontScale(fontSize);
        dialogText.setText(text);
        stage.addActor(dialogText);
    }

    public void render() {
        if(time>=delay && startCountdown) {
            setDialogOn(true);
            dialogText.setVisible(true);
            dialogBox.setVisible(true);
            time = 0;
            startCountdown = false;
        }

        if(startCountdown || dialogText.isVisible())
            time+=Gdx.graphics.getDeltaTime();
        if(time>=duration && !startCountdown) {
            dialogText.setVisible(false);
            dialogBox.setVisible(false);
            time = 0;
            setDialogOn(false);
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    public void setDialogOn(boolean dialogOn) {

//        if(dialogOn) {
//            zibil = Gdx.input.getInputProcessor();
//            Gdx.input.setInputProcessor(stage);
//        }
//        else {
//            Gdx.input.setInputProcessor(zibil);
//        }

        isDialogOn = dialogOn;
    }

}