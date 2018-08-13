package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMusic extends Stage {


    public TextureAtlas atlas;
    float scale = 800f / 1920f;
    public void act() {

    }

    @Override
    public void draw() {
        super.draw();
    }
public void SettingsMusic(){

        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));
        setSettingsMusic();
}
    public void setSettingsMusic(){
        Table settingsMenu=new Table();
        Image settingsMenuImage = new Image(atlas.findRegion("settingsMenu"));
        settingsMenu.setFillParent(true);
        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scale).height(settingsMenuImage.getPrefHeight() * scale).padTop(5).padRight(14+Math.abs(Gdx.graphics.getWidth()-800)/2);
        this.addActor(settingsMenu);
        Gdx.input.setInputProcessor(this);
    }

}
