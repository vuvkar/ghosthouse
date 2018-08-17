package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;

public class Room4 extends Room {
    final int PLITA = 10;
    public ShaderProgram shaderProgram;
    @Override
    public void roomStarted() {
        //tipadialog
        moveGhostTo(2);
        setItemStatus(PLITA,ItemType.NONTAKEABLE);
        DialogSystem.dialogSystem.startDialog(InGameTexts.startr1 + "\n" + InGameTexts.startr2, 3f, 0.5f, 0.3f);
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
                        shaderProgram.setUniformf("u_lightColor",255,0,0);
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
