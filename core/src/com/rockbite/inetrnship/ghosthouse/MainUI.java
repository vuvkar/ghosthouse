package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.rockbite.inetrnship.ghosthouse.data.Room;

public class MainUI extends Stage {
    private GhostHouse ghostHouse;
    public Group LianChu = new Group();
    public TextureAtlas atlas;
    public AssetLoader assetLoader = new AssetLoader();
    int index = 0;

    public MainUI(GhostHouse ghostHouse) {
        atlas = new TextureAtlas(Gdx.files.internal("packed/r1Items.atlas"));
        Gdx.input.setInputProcessor(this);

        this.ghostHouse = ghostHouse;
        this.addActor(LianChu);
    }


    public void act() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Image img = new Image(atlas.getRegions().get(index));
            float scale = 0.8f;
            LianChu.addActor(img);
            LianChu.getChildren().get(index).setBounds(0, 0, atlas.getRegions().get(index).originalWidth * scale, atlas.getRegions().get(index).originalHeight * scale);
            LianChu.getChildren().get(index).setTouchable(Touchable.enabled);
            LianChu.getChildren().get(index).addListener(new ActorGestureListener() {
                public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                    event.getListenerActor().setPosition(Gdx.input.getX() - 25, Gdx.graphics.getHeight() - Gdx.input.getY() - 25);
                    System.out.println(x + " " + y);
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }
            });
            index++;
        }
    }

    @Override
    public void draw() {
        super.draw();
    }
}