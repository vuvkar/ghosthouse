package com.rockbite.inetrnship.ghosthouse.MiniGames.KillTheBugs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class BugsActor extends Actor {
    private Sprite texture;
    private float step = 5;
    private float speed = 1;
    private float timeDelta = 0.05f;
    private float maxTime = 8;
    private float time = 0;
    private boolean isDead = false;
    private float dir = 1;
    private float dir2 = 1;
    private String actorType;
    private Texture bug1 = new Texture(Gdx.files.internal("MiniGame/bug_1_1.png"));
    private Texture bug2 = new Texture(Gdx.files.internal("MiniGame/bug_1_2.png"));
    private Texture blood = new Texture(Gdx.files.internal("MiniGame/blood.png"));
    private Texture death = new Texture(Gdx.files.internal("MiniGame/death.png"));

    public BugsActor(final Texture texture, float x, float y, String actorType, float direction) {
        this.texture = new Sprite(texture);
        this.actorType = actorType;
        this.dir = direction;
        setBounds(x, y, texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);
        KillBugs.timer = 0;

        if (actorType.equals("bug")) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (!KillBugs.gameOver && !isDead) {
                        isDead = true;
                        BugsActor.this.texture.setTexture(blood);
                        KillBugs.killedBugs++;
                        KillBugs.killBugsText.setText(KillBugs.killedBugs + "/10");
                    }
                }
            });
        }

        if (actorType.equals("message")) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    BugsActor.this.remove();
                    KillBugs.gameStarted = true;
                }
            });
        }

        if (actorType.equals("gameover")) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    KillBugs.restart = true;
                }
            });
        }

        if (actorType.equals("close")) {

        }

    }

    @Override
    protected void positionChanged() {
        texture.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        texture.draw(batch);
    }

    @Override
    public void act(float delta) {
        time += Gdx.graphics.getDeltaTime();
        //start bugs movement
        if (time >= timeDelta && !isDead) {
            time = 0;
            if (!KillBugs.gameOver && actorType == "bug") {
                animate();
            }
        } else if (time > 2)
            texture.setTexture(death);

        //timer
        if (KillBugs.timer >= 1 && KillBugs.timer <= maxTime && actorType.equals("timer") && !KillBugs.gameOver && !KillBugs.win) {
//            texture.setTexture(new Texture(Gdx.files.internal("MiniGame/tile_" + (int) KillBugs.timer + ".png")));
            KillBugs.timerText.setText(7 - (int) KillBugs.timer + "");

        }
        if (KillBugs.timer >= maxTime && actorType.equals("timer") && !KillBugs.win && !KillBugs.gameOver) { // && !KillBugs.restart
            KillBugs.gameOver = true;
            KillBugs.timer = 0;

        }
        super.act(delta);
    }

    private void animate() {
        if (actorType.equals("bug")) {
            speed = (float) Math.random();
            if (Math.random() > 0.5f)
                dir *= -1;

            //check for borders
            if (getX() >= KillBugs.bgWidth + (Gdx.graphics.getWidth() - KillBugs.bgWidth) / 2 - bug1.getWidth()) {
                dir2 *= -1;
                texture.setTexture(bug2);
                MoveByAction mba = new MoveByAction();
                mba.setAmount(-getWidth() / 5, 0);
                mba.setDuration(timeDelta);
                BugsActor.this.addAction(mba);
            } else if (getX() <= (Gdx.graphics.getWidth() - KillBugs.bgWidth) / 2) {
                dir2 *= -1;
                texture.setTexture(bug1);
                MoveByAction mba = new MoveByAction();
                mba.setAmount(getWidth() / 5, 0);
                mba.setDuration(timeDelta);
                BugsActor.this.addAction(mba);
            }
            if (getY() <= (Gdx.graphics.getHeight() - KillBugs.bgHeight) / 2) {
                MoveByAction mba = new MoveByAction();
                mba.setAmount(0, getHeight() / 5);
                mba.setDuration(timeDelta);
                BugsActor.this.addAction(mba);
            }
            if (getY() >= KillBugs.bgHeight + (Gdx.graphics.getHeight() - KillBugs.bgHeight) / 2 - bug1.getHeight()) {
                MoveByAction mba = new MoveByAction();
                mba.setAmount(0, -getHeight() / 5);
                mba.setDuration(timeDelta);
                BugsActor.this.addAction(mba);
            }

            //move bugs
            for (int i = 0; i < 10; i++) {
                MoveByAction mba = new MoveByAction();
                mba.setAmount((float) Math.random() * step * speed * dir2, 0.4f * dir);
                mba.setDuration(timeDelta);
                BugsActor.this.addAction(mba);
            }
        }
    }
}
