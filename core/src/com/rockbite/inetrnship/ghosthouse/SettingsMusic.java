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
    float soundPos[]=new float[11];
    int soundGhostInd=6;
    float musicPos[]=new float[11];
    int musicGhostInd=5;
    public SettingsMusic(){

        for(int i=0; i<11; i++){
            soundPos[i]=284*distscalex+i*22*distscalex;
            musicPos[i]=274*distscalex+i*24*distscalex;
        }

        stage = new Stage(new ScreenViewport());
        atlas = new TextureAtlas(Gdx.files.internal("Uipacked/UI.atlas"));

        Table settingsMenu=new Table();
        Table closeSettings=new Table();
        Table replayButton=new Table();
        Table openMenu=new Table();
        Table soundMinus=new Table();
        Table soundPlus=new Table();
        Table musicMinus=new Table();
        Table musicPlus=new Table();
        Table musicGhost=new Table();
        final Table soundGhost=new Table();

        settingsMenu.setFillParent(true);
        closeSettings.setFillParent(true);
        replayButton.setFillParent(true);
        openMenu.setFillParent(true);
        soundMinus.setFillParent(true);
        soundPlus.setFillParent(true);
        musicMinus.setFillParent(true);
        musicPlus.setFillParent(true);
        musicGhost.setFillParent(true);
        soundGhost.setFillParent(true);

        Image settingsMenuImage = new Image(atlas.findRegion("SettingsMenu"));
        Image replay = new Image(atlas.findRegion("repeat"));
        Image menu=new Image(atlas.findRegion("buttonofmenu"));
        Image close=new Image(atlas.findRegion("exidbutton"));
        Image sminus=new Image(atlas.findRegion("soundminus"));
        Image splus=new Image(atlas.findRegion("soundplus"));
        Image mminus=new Image(atlas.findRegion("musi_minus"));
        Image mplus=new Image(atlas.findRegion("musicplus"));
        final Image sghost=new Image(atlas.findRegion("ghost"));
        final Image mghost=new Image(atlas.findRegion("ghost"));

        close.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                MainUI.settingson=false;
                Gdx.input.setInputProcessor(MainGame.multiplexer);
                MainUI.settingsMusic=null;
                return true;
            }
        });

        settingsMenu.add(settingsMenuImage).width(settingsMenuImage.getPrefWidth() * scalex).height(settingsMenuImage.getPrefHeight() * scaley);
        settingsMenu.center();

        replayButton.add(replay).width(replay.getPrefWidth() * scalex).height(replay.getPrefHeight() * scaley).padBottom(92*distscaley).padLeft(300*distscalex);
        replayButton.bottom().left();

        openMenu.add(menu).width(menu.getPrefWidth()*scalex).height(menu.getPrefHeight()*scaley).padBottom(92*distscaley).padRight(298*distscalex);
        openMenu.bottom().right();

        closeSettings.add(close).width(close.getPrefWidth()*scalex).height(close.getPrefHeight()*scaley).padTop(70*distscaley).padRight(168*distscalex);
        closeSettings.top().right();

        soundMinus.add(sminus).width(sminus.getPrefWidth()*scalex).height(sminus.getPrefHeight()*scaley).padLeft(255*distscalex).padBottom(214*distscaley);
        soundMinus.bottom().left();

        sghost.setWidth(sghost.getPrefWidth()*scalex);
        sghost.setHeight(sghost.getPrefHeight()*scaley);
        sghost.setPosition(soundPos[soundGhostInd],204*distscaley );

        soundMinus.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

               if(soundGhostInd>0) {
                   soundGhostInd--;
                   sghost.setX(soundPos[soundGhostInd]);
               }
                return true;
            }
        });

        soundPlus.add(splus).width(splus.getPrefWidth()*scalex).height(splus.getPrefHeight()*scaley).padRight(245*distscalex).padBottom(214*distscaley);
        soundPlus.bottom().right();
        soundPlus.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(soundGhostInd<10) {
                    soundGhostInd++;
                    sghost.setX(soundPos[soundGhostInd]);
                }
                return true;
            }
        });

        mghost.setWidth(mghost.getPrefWidth()*scalex);
        mghost.setHeight(mghost.getPrefHeight()*scaley);
        mghost.setPosition(musicPos[musicGhostInd],268*distscaley );

        musicMinus.add(mminus).width(mminus.getPrefWidth()*scalex).height(mminus.getPrefHeight()*scaley).padLeft(242*distscalex).padTop(163*distscaley);
        musicMinus.top().left();
        musicMinus.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(musicGhostInd>0) {
                    musicGhostInd--;
                    mghost.setX(musicPos[musicGhostInd]);
                }
                return true;
            }
        });
        musicPlus.add(mplus).width(mplus.getPrefWidth()*scalex).height(mplus.getPrefHeight()*scaley).padRight(239*distscalex).padTop(156*distscaley);
        musicPlus.top().right();
        musicPlus.getChildren().get(0).addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(musicGhostInd<10) {
                    musicGhostInd++;
                    mghost.setX(musicPos[musicGhostInd]);
                }
                return true;
            }
        });




        NinePatch patch = new NinePatch(atlas.createPatch("black"));
        patch.setMiddleWidth(Gdx.graphics.getWidth() - patch.getTotalWidth() + patch.getMiddleWidth());
        patch.setMiddleHeight(Gdx.graphics.getHeight() - patch.getTotalHeight() + patch.getMiddleHeight());
        Image Patch = new Image(patch);

        stage.addActor(Patch);
        stage.addActor(settingsMenu);
        stage.addActor(replayButton);
        stage.addActor(openMenu);
        stage.addActor(closeSettings);
        stage.addActor(soundMinus);
        stage.addActor(soundPlus);
        stage.addActor(musicMinus);
        stage.addActor(musicPlus);
        stage.addActor(sghost);
        stage.addActor(mghost);
        Gdx.input.setInputProcessor(stage);
    }


    public void draw() {
        stage.draw();
    }



}
