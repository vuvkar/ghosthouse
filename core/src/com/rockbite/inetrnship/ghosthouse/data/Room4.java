package com.rockbite.inetrnship.ghosthouse.data;

import com.rockbite.inetrnship.ghosthouse.DialogSystem;

public class Room4 extends Room {
    @Override
    public void roomStarted() {
        //tipadialog
        DialogSystem.dialogSystem.startDialog(InGameTexts.startr1 + "\n" + InGameTexts.startr2, 3f, 0.5f, 0.3f);
    }

    @Override
    public void itemWasClicked(int itemID) {

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
