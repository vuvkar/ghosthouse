package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMusic {


    public TextureAtlas atlas;
    public Stage stage;
    float scale = Gdx.graphics.getWidth() / 1920f;

    public SettingsMusic(){
        stage = new Stage(new ScreenViewport());
        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));

        Table settingsMenu=new Table();
        Table closeSettings;
        settingsMenu.setFillParent(true);


        Image settingsMenuImage = new Image(atlas.findRegion("SettingsMenu"));
        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scale).height(settingsMenuImage.getPrefHeight() * scale);

        NinePatch patch = new NinePatch(atlas.createPatch("black"));
        patch.setMiddleWidth(Gdx.graphics.getWidth() - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(Gdx.graphics.getHeight() - patch.getTotalHeight() + patch.getMiddleHeight());
        Image Patch = new Image(patch);

        stage.addActor(Patch);
        stage.addActor(settingsMenu);

        Gdx.input.setInputProcessor(stage);
    }


    public void draw() {
        stage.draw();
    }



}
