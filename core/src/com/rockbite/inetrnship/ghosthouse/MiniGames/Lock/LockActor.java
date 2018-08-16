package com.rockbite.inetrnship.ghosthouse.MiniGames.Lock;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rockbite.inetrnship.ghosthouse.MainGame;

public class LockActor extends Actor {
    private Sprite actor;

    public LockActor(Texture texture, float x, float y, String actorType, final int value) {
        actor = new Sprite(texture);
        setBounds(x,y,actor.getWidth(),actor.getHeight());
        setTouchable(Touchable.enabled);

        if(actorType.equals( "button" )) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    actor.setTexture(new Texture("MiniGame/lockBtn_"+value+"_pressed.png"));
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    actor.setTexture(new Texture("MiniGame/lockBtn_"+value+"_normal.png"));
                    if(!Lock.isDoorOpen) {
                        if (Lock.combination.size >= 4) {
                            Lock.combination.removeRange(0, 3);
                            Lock.code.setText("");
                        }

                        Lock.combination.add(value);
                        Lock.code.setText(Lock.code.getText() +"" + value + "   ");
                        System.out.println(Lock.combination);

                        if (Lock.combination.size >= 4 && Lock.combination.equals(Lock.correctCombination)) {
                            Lock.isDoorOpen = true;
                            Lock.light1.setVisible(false);
                            Lock.light2.setVisible(true);
                        }
                    }
                }
            });
        }
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