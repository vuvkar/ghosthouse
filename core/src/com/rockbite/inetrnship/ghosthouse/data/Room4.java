package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.MainUI;

public class Room4 extends Room {
    final int PLITA = 10;
    Sound explosion;
    public ShaderProgram shaderProgram;

    @Override
    public void roomStarted() {
        explosion = Gdx.audio.newSound (Gdx.files.internal("sounds/explosion.mp3"));
        //tipadialog
        moveGhostTo(2);
        setItemStatus(PLITA,ItemType.NONTAKEABLE);
        DialogSystem.dialogSystem.startDialog(InGameTexts.room4 + "\n" + InGameTexts.room41, 3f, 0.5f, 0.3f);
    }

    @Override
    public void itemWasClicked(int itemID) {
        switch(itemID){
            case PLITA:
                switch (getItemStatus(PLITA)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        explosion.play(Room.soundVolume);
                      //  GhostMesh.lightColor.set(255, 0,0);

                        mainGame.ghostHouse.mainUI.addActor(mainGame.ghostHouse.mainUI.BOOM);
                        mainGame.boom=true;
                }
        }
    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {

    }

    @Override
    public void itemWasMoved(int fromInventory, int toInventory) {

    }

    @Override
    public void miniGameWasClosed(boolean hasWon) {

    }

    @Override
    public void itemWasClickedOnInventory(int itemID) {

    }
}
