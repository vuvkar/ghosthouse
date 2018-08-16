package com.rockbite.inetrnship.ghosthouse.data;
import com.rockbite.inetrnship.ghosthouse.DialogSystem;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.awt.print.Paper;
import java.util.Set;

public class Room2 extends Room {
    final int PAPER = 6;
    final int SPRAY_BOTTLE = 1;
    final int RED_BOTTLE = 14;
    final int DRUG_BOTTLE  = 4;
    final int YELLOW_BOTTLE = 3;
    final int RIGHT_PILLOW = 9;
    final int JEANS = 8;
    final int WARDROBE = 15;
    final int EUCALYPTUS = 13;
    final int PILLOW = 9;
    final int PILLS =16;
    final int SEKATOR = 17;
    final int SILICA =18;
    final int LEAF = 19;

    @Override
    public void roomStarted() {
        DialogSystem.dialogSystem.startDialog(InGameTexts.startr1 + "\n" + InGameTexts.startr2, 1.5f, 0.7f, 1f);
        setItemStatus(PAPER,ItemType.TAKEABLE);
        setItemStatus(SPRAY_BOTTLE,ItemType.STATIC);
        setItemStatus(RED_BOTTLE,ItemType.STATIC);
        setItemStatus(DRUG_BOTTLE,ItemType.STATIC);
        setItemStatus(YELLOW_BOTTLE,ItemType.STATIC);
        setItemStatus(RIGHT_PILLOW,ItemType.STATIC);
        setItemStatus(JEANS,ItemType.STATIC);
        setItemStatus(WARDROBE,ItemType.STATIC);
        setItemStatus(EUCALYPTUS,ItemType.STATIC);
    }

    @Override
    public void itemWasClicked(int itemID) {
        System.out.println(itemID);
        switch (itemID) {
            case PAPER:
                switch (getItemStatus(PAPER)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        setItemStatus(SPRAY_BOTTLE,ItemType.TAKEABLE);
                        setItemStatus(RED_BOTTLE,ItemType.TAKEABLE);
                        setItemStatus(DRUG_BOTTLE,ItemType.TAKEABLE);
                        setItemStatus(YELLOW_BOTTLE,ItemType.TAKEABLE);
                        setItemStatus(RIGHT_PILLOW,ItemType.NONTAKEABLE);
                        setItemStatus(JEANS,ItemType.NONTAKEABLE);
                        setItemStatus(WARDROBE,ItemType.NONTAKEABLE);
                        setItemStatus(EUCALYPTUS,ItemType.NONTAKEABLE);
                        changeTexture(PAPER, "");
                        setItemStatus(PAPER,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case SPRAY_BOTTLE:
                switch (getItemStatus(SPRAY_BOTTLE)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(SPRAY_BOTTLE);
                        changeTexture(SPRAY_BOTTLE, "");
                        setItemStatus(SPRAY_BOTTLE,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case RED_BOTTLE:
                switch (getItemStatus(RED_BOTTLE)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(RED_BOTTLE);
                        changeTexture(RED_BOTTLE, "");
                        setItemStatus(RED_BOTTLE,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case DRUG_BOTTLE:
                switch (getItemStatus(DRUG_BOTTLE)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(DRUG_BOTTLE);
                        changeTexture(DRUG_BOTTLE, "");
                        setItemStatus(DRUG_BOTTLE,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case YELLOW_BOTTLE:
                switch (getItemStatus(YELLOW_BOTTLE)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(YELLOW_BOTTLE);
                        changeTexture(YELLOW_BOTTLE, "");
                        setItemStatus(YELLOW_BOTTLE,ItemType.STATIC);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case JEANS:
                switch (getItemStatus(JEANS)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        addToInventory(SILICA);
                        setItemStatus(JEANS,ItemType.STATIC);
                        break;
                }
                break;
            case WARDROBE:
                switch (getItemStatus(WARDROBE)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        addToInventory(SEKATOR);
                        setItemStatus(WARDROBE,ItemType.STATIC);
                        break;
                }
                break;
            case EUCALYPTUS:
                switch (getItemStatus(EUCALYPTUS)) {
                    case STATIC:
                        break;
                    case TAKEABLE:
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
            case PILLOW:
                switch (getItemStatus(PILLOW)){
                    case STATIC:
                        break;
                    case TAKEABLE:
                        addToInventory(PILLS);
                        break;
                    case NONTAKEABLE:
                        break;
                }
                break;
        }
    }

    @Override
    public void itemWasDragged(int fromInventory, int toRoomItem) {
        if (fromInventory==SEKATOR && toRoomItem==EUCALYPTUS){
            addToInventory(LEAF);
            setItemStatus(EUCALYPTUS,ItemType.STATIC);
            removeFromInventory(SEKATOR);
        }
    }

    @Override
    public void itemWasMoved(int fromInventory, int toInventory) {
        if (toInventory==1){
            if (fromInventory==RED_BOTTLE || fromInventory==DRUG_BOTTLE || fromInventory==YELLOW_BOTTLE || fromInventory==SILICA || fromInventory==LEAF){}
            removeFromInventory(fromInventory);
        }
    }

    @Override
    public void miniGameWasClosed()
    {

    }
}
