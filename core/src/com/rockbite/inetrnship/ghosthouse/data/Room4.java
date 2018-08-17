package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;
import com.rockbite.inetrnship.ghosthouse.MainUI;

public class Room4 extends Room {
    final int PLITA = 10;

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

                      //  GhostMesh.lightColor.set(255, 0,0);

                        mainGame.ghostHouse.mainUI.addActor(mainGame.ghostHouse.mainUI.BOOM);
                        mainGame.boom=true;
                        for(int i=0; i<mainGame.ghostHouse.mainUI.getActors().size-1;i++){
                            mainGame.ghostHouse.mainUI.getActors().get(i).setVisible(false);

                        }
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
