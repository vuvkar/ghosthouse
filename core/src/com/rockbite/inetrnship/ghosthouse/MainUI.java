package com.rockbite.inetrnship.ghosthouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainUI extends Stage {
    private GhostHouse ghostHouse;
    public Group inventory =  new Group();
    public int numOfItems=0;
    public Group arrows=new Group();
public Vector2 inventoryInd=new Vector2(0,0);
MyActor actor;
    public MainUI(GhostHouse ghostHouse) {
        this.ghostHouse = ghostHouse;
        setArrows();


       // actor=new MyActor();
      //  this.addActor(actor);
        Gdx.input.setInputProcessor(this);
       // this.setKeyboardFocus(actor);

        this.addActor(inventory);
        this.addActor(arrows);

    }

    @Override
    public void act() {

    }

    @Override
    public void draw() {
        super.draw();
    }

public void setArrows(){
    arrows.addActor(new Image(new Texture(Gdx.files.internal("UI/right.png"))));
    arrows.addActor(new Image(new Texture(Gdx.files.internal("UI/left.png"))));
    arrows.getChildren().get(0).setPosition(700, 0);
    arrows.getChildren().get(0).setBounds(700, 0, 100, 56);
    arrows.getChildren().get(0).setTouchable(Touchable.enabled);
arrows.getChildren().get(0).addListener(new ClickListener(){

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(numOfItems>5) {
            inventory.moveBy(-100, 0);
            System.out.println(inventory.getX());
            event.handle();
            return true;
        }
        else{

            return false; }
    }
});

    arrows.getChildren().get(1).setPosition(0, 0);
    arrows.getChildren().get(1).setBounds(0, 0, 100, 56);
    arrows.getChildren().get(1).setTouchable(Touchable.enabled);
    arrows.getChildren().get(1).addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if(numOfItems>5 && inventory.getX()!=0) {
                inventory.moveBy(100, 0);
                event.handle();
                return true;
            }
            else{

                return false; }
        }
    });
}


}

class MyActor extends Actor {

    Sprite sprite =new Sprite(new Texture(Gdx.files.internal("textures/badlogic.jpg")));

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    protected void positionChanged() {

    }

    public MyActor(){
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);

        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("VAY");

                event.handle();//the Stage will stop trying to handle this event
                return true; //the inputmultiplexer will stop trying to handle this touch
            }
        });


    }
}