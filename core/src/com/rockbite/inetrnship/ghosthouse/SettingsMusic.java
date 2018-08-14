package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMusic extends Stage {


    public TextureAtlas atlas;
    float scale = Gdx.graphics.getWidth() / 1920f;

    public SettingsMusic(){
        super(new ScreenViewport());
        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));

        Table settingsMenu=new Table();
        Image settingsMenuImage = new Image(atlas.findRegion("settingsMenu"));
        settingsMenu.setFillParent(true);
        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scale).height(settingsMenuImage.getPrefHeight() * scale).padTop(5).padRight(14);
        this.addActor(settingsMenu);
        Gdx.input.setInputProcessor(this);
    }
    public void act() {

    }

    @Override
    public void draw() {
        super.draw();
    }



}
