package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Json;
import com.rockbite.inetrnship.ghosthouse.data.GhostBuilding;
import com.rockbite.inetrnship.ghosthouse.data.Room;

public class MainUI extends Stage implements InputProcessor {
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
            TextureAtlas.AtlasRegion imgName = atlas.getRegions().get(index);
            Image img = new Image(imgName);
            float scale = 0.8f;

            Json json = new Json();
            String itemName = json.prettyPrint(imgName.name);
            FileHandle file = Gdx.files.local("JSON/items.json");
            file.writeString(itemName, true);
            System.out.println(imgName.name);

            LianChu.addActor(img);
            LianChu.getChildren().get(index).setBounds(0, 0, atlas.getRegions().get(index).originalWidth * scale, atlas.getRegions().get(index).originalHeight * scale);
            LianChu.getChildren().get(index).setTouchable(Touchable.enabled);

            LianChu.getChildren().get(index).addListener(new ActorGestureListener() {
                public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                    Vector2 pos = new Vector2();
                    pos.set(x, y);
                        float positionX = Gdx.input.getX();
                        float positionY = Gdx.graphics.getHeight() - Gdx.input.getY();
                        event.getListenerActor().setPosition(positionX, positionY);
                        System.out.println(x + " " + y);
                }

                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Json json = new Json();
                    String positionX = json.prettyPrint(x);
                    String positionY = json.prettyPrint(y);
                    FileHandle file = Gdx.files.local("JSON/items.json");
                    file.writeString(positionX, true);
                    file.writeString(positionY, true);
                }

            });

            if (index == atlas.getRegions().size) {
                index = 0;
            } else {
                index++;
            }
        }
    }
    public boolean scrolled (int index) {
        //LianChu.getChildren().get(index).getTouchable();
        LianChu.getChildren().first().setZIndex(1);
        return false;
    }

    @Override
    public void draw() {
        super.draw();
    }
//
//    private void check (String text, Object object) {
//        Object object2 = json.fromJson(object.getClass(), text);
//        equals(object, object2);
//    }
//
//    private void equals (Object a, Object b) {
//        if (!a.equals(b)) throw new RuntimeException("Fail!\n" + a + "\n!=\n" + b);
//    }
}