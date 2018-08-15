package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMusic {


    public TextureAtlas atlas;
    public Stage stage;
    float scalex = Gdx.graphics.getWidth() / 1920f;
    float scaley = Gdx.graphics.getHeight() / 1080f;
    float distscalex=Gdx.graphics.getWidth()/800f;
    float distscaley=Gdx.graphics.getHeight()/450f;
    public SettingsMusic(){
        stage = new Stage(new ScreenViewport());
        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));

        Table settingsMenu=new Table();
        Table closeSettings=new Table();
        Table replayButton=new Table();
        Table openMenu=new Table();

        settingsMenu.setFillParent(true);
        closeSettings.setFillParent(true);
        replayButton.setFillParent(true);
        openMenu.setFillParent(true);

        Image settingsMenuImage = new Image(atlas.findRegion("SettingsMenu"));
        Image replay = new Image(atlas.findRegion("repeat"));
        Image menu=new Image(atlas.findRegion("buttonofmenu"));
        Image close=new Image(atlas.findRegion("exidbutton"));
        close.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                MainUI.settingson=false;
                Gdx.input.setInputProcessor(MainGame.multiplexer);
                MainUI.settingsMusic=null;
                return true;
            }
        });

        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scalex).height(settingsMenuImage.getPrefHeight() * scaley);

        replayButton.add(replay).width(replay.getPrefWidth() * scalex).height(replay.getPrefHeight() * scaley).padBottom(92*distscaley).padLeft(300*distscalex);
        replayButton.bottom().left();

        openMenu.add(menu).width(menu.getPrefWidth()*scalex).height(menu.getPrefHeight()*scaley).padBottom(92*distscaley).padRight(298*distscalex);
        openMenu.bottom().right();

        closeSettings.add(close).width(close.getPrefWidth()*scalex).height(close.getPrefHeight()*scaley).padTop(70*distscaley).padRight(168*distscalex);
        closeSettings.top().right();



        NinePatch patch = new NinePatch(atlas.createPatch("black"));
        patch.setMiddleWidth(Gdx.graphics.getWidth() - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(Gdx.graphics.getHeight() - patch.getTotalHeight() + patch.getMiddleHeight());
        Image Patch = new Image(patch);

        stage.addActor(Patch);
        stage.addActor(settingsMenu);
        stage.addActor(replayButton);
        stage.addActor(openMenu);
        stage.addActor(closeSettings);

        Gdx.input.setInputProcessor(stage);
    }


    public void draw() {
        stage.draw();
    }



}
