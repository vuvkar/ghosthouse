package com.rockbite.inetrnship.ghosthouse.MiniGames.Lock;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LockActor extends Actor {
    private Sprite actor;

    public LockActor(Texture texture, float x, float y, String actorType, final int value) {
        actor = new Sprite(texture);
        setBounds(x,y,actor.getWidth(),actor.getHeight());
        setTouchable(Touchable.enabled);


    }

    @Override
    protected void positionChanged() {
        actor.setPosition(getX(),getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        actor.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}