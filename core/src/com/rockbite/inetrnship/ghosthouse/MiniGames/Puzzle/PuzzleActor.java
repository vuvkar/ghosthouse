package com.rockbite.inetrnship.ghosthouse.MiniGames.Puzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rockbite.inetrnship.ghosthouse.MainGame;

public class PuzzleActor extends Actor {
    private Sprite tile;
    private int i;
    private int j;

    public PuzzleActor(Texture texture, float x, float y, final int i, final int j, String actorType) {
        this.i = i;
        this.j = j;
        tile = new Sprite(texture);
        setBounds(x, y, tile.getWidth(), tile.getHeight());
        setTouchable(Touchable.enabled);

        if (actorType.equals("tile")) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    findFreePlace();
                }
            });
        } else if (actorType.equals("close")) {
            addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("close");
                    for (int i = 0; i < getStage().getActors().size; i++) {
                        getStage().getActors().get(i).setVisible(false);

                    }
                    MainGame.miniGameOn = false;
                    Gdx.input.setInputProcessor(MainGame.multiplexer);
                }
            });
        }
    }

    private void findFreePlace() {
        int i = this.i;
        int j = this.j;

        if (i > 0 && Puzzle.board[i - 1][j] == 0) {
            animate(-1, 0);
            Puzzle.board[i - 1][j] = Puzzle.board[i][j];
            Puzzle.board[i][j] = 0;
            this.i--;
        }
        if (i < 2 && Puzzle.board[i + 1][j] == 0) {
            animate(1, 0);
            Puzzle.board[i + 1][j] = Puzzle.board[i][j];
            Puzzle.board[i][j] = 0;
            this.i++;
        }
        if (j > 0 && Puzzle.board[i][j - 1] == 0) {
            Puzzle.board[i][j - 1] = Puzzle.board[i][j];
            Puzzle.board[i][j] = 0;
            this.j--;
            animate(0, -1);
        }
        if (j < 2 && Puzzle.board[i][j + 1] == 0) {
            Puzzle.board[i][j + 1] = Puzzle.board[i][j];
            Puzzle.board[i][j] = 0;
            this.j++;
            animate(0, 1);
        }
    }

    private void animate(float right, float up) {
        MoveByAction mba = new MoveByAction();
        mba.setAmount((tile.getWidth() + 3) * right, (tile.getHeight() + 3) * up);
        mba.setDuration(0.1f);
        PuzzleActor.this.addAction(mba);
    }

    @Override
    protected void positionChanged() {
        tile.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tile.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
