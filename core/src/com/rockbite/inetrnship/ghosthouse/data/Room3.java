package com.rockbite.inetrnship.ghosthouse.data;

import com.rockbite.inetrnship.ghosthouse.MiniGames.KillTheBugs.KillBugs;

public class Room3 extends Room {

    final int POLKA = 25;
    final int TUMBCHKA = 34;
    final int TAPOR_PART1 = 35;
    final int TAPOR_PART2 = 36;
    final int GLUE = 37;
    final int GLUED_TAPOR = 38;
    final int TAPOR = 39;
    final int DOOR = 10;
    final int DOOR_LEFT = 11;
    final int BUG = 40;

    @Override
    public void roomStarted() {
        moveGhostTo(DOOR_LEFT);
        setItemStatus(BUG, ItemType.NONTAKEABLE);
    }

    @Override
    public void itemWasClicked(int itemID) {
        switch (itemID) {
            case POLKA:
                switch (getItemStatus(POLKA)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        setItemStatus(POLKA, ItemType.STATIC);
                        addToInventory(TAPOR_PART1);
                        if (getItemStatus(TUMBCHKA) == ItemType.STATIC) {
                            //TR
                        }
                        break;
                }
            case TUMBCHKA:
                switch (getItemStatus(TUMBCHKA)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(TUMBCHKA, ItemType.STATIC);
                        addToInventory(TAPOR_PART2);
                        break;
                    case NONTAKEABLE:
                        break;
                }
            case BUG:
                switch (getItemStatus(BUG)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        miniGame = new KillBugs();
                        openMiniGame();
                        break;
                }
        }

    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory == TAPOR && fromInventory == DOOR) {
            removeFromInventory(TAPOR);
            setItemStatus(DOOR, ItemType.STATIC);
            changeTexture(DOOR, "");
        }
    }

    @Override
    public void itemWasMoved(int fromInventory, int toInventoryItem) {
        if ((fromInventory == TAPOR_PART1 && fromInventory == GLUE) || (fromInventory == GLUE && fromInventory == TAPOR_PART1)) {
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(GLUED_TAPOR);
        }
        if ((fromInventory == GLUED_TAPOR && fromInventory == TAPOR_PART2) || (fromInventory == TAPOR_PART2 && fromInventory == GLUED_TAPOR)) {
            removeFromInventory(fromInventory);
            removeFromInventory(toInventoryItem);
            addToInventory(TAPOR);
        }
    }

    @Override
    public void miniGameWasClosed(boolean hasWon) {

    }

    @Override
    public void itemWasClickedOnInventory(int itemID) {

    }
}
