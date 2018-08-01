package com.rockbite.inetrnship.ghosthouse.ecs.components;

import com.badlogic.ashley.core.Component;

public class RoomObjectComponent implements Component {
    int roomID;

    public RoomObjectComponent(int roomID) {
        this.roomID = roomID;
    }
}

