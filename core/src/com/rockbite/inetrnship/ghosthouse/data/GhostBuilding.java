package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;

public class GhostBuilding {
    private Array<Room> rooms;

    private Array<GhostRectangle> faceWalls;
    private Array<GhostRectangle> roomWalls;
    private Array<GhostRectangle> roomConnectingWalls;
    private Array<GhostRectangle> buildingConnectingWalls;

    private final float WALL_HEIGHT = 2f;
    private final float BUILDING_DEPTH = 10f;

    private float buildingWidth;
    private float buildingHeight;

    public GhostBuilding(Array<Room> rooms) {
        this.rooms = rooms;
        this.buildingConnectingWalls = createBuildingConnectingWalls(rooms);
        this.roomWalls = createRooms(rooms);
        this.faceWalls = createFaceWalls(rooms);
        this.roomConnectingWalls = createConnectingWalls(rooms);
    }

    public Array<GhostRectangle> getAllRects() {
        Array<GhostRectangle> rects = new Array<GhostRectangle>();
        rects.addAll(roomWalls);
        rects.addAll(faceWalls);
        rects.addAll(roomConnectingWalls);
        rects.addAll(buildingConnectingWalls);
        return rects;
    }

    Array<GhostRectangle> createRooms(Array<Room> arr) {
        Array<GhostRectangle> result = new Array<GhostRectangle>();
        for(Room current: arr) {
            GhostRectangle rectangle = new GhostRectangle();
            rectangle.setX(current.getOrigin().x + WALL_HEIGHT);
            rectangle.setY(current.getOrigin().y + WALL_HEIGHT);
            rectangle.setZ(0);
            rectangle.setHeight(current.getHeight() - 2 * WALL_HEIGHT);
            rectangle.setWidth(current.getWidth() - 2 * WALL_HEIGHT);
            rectangle.setType(RectangleType.ROOM);
            rectangle.setNormal(new Vector3(0, 0, 1));

            result.add(rectangle);
        }
        return result;
    }

    Array<GhostRectangle> createBuildingConnectingWalls(Array<Room> arr) {

        Vector2 topRight = new Vector2();
        Vector2 bottomLeft = new Vector2();

        for(Room current: arr) {
            bottomLeft.x = Math.min(bottomLeft.x, current.getOrigin().x - WALL_HEIGHT);
            bottomLeft.y = Math.min(bottomLeft.y, current.getOrigin().y - WALL_HEIGHT);

            topRight.x = Math.max(topRight.x, current.getOrigin().x + current.getWidth() + WALL_HEIGHT);
            topRight.y = Math.max(topRight.y, current.getOrigin().y + current.getHeight() + WALL_HEIGHT);
        }

        buildingWidth = topRight.x - bottomLeft.x;
        buildingHeight = topRight.y - bottomLeft.y;

        Room fakeRoom = new Room(-1, new Vector2(bottomLeft.x - WALL_HEIGHT, bottomLeft.y - WALL_HEIGHT),
                buildingWidth + 2 * WALL_HEIGHT, buildingHeight + 2 * WALL_HEIGHT);
        Array<Room> rooms = new Array<Room>();
        rooms.add(fakeRoom);

        Array<GhostRectangle> walls = createConnectingWalls(rooms);
        for(GhostRectangle wall: walls) {
            wall.setType(RectangleType.BUILDING);
            wall.getNormal().scl(-1);
        }
        for(int i = 0; i < 2; i++) {
            GhostRectangle rect = new GhostRectangle();
            rect.setType(RectangleType.BUILDING);
            rect.setNormal(new Vector3(0, 0, 1));
            rect.setX(-WALL_HEIGHT);
            rect.setY(-WALL_HEIGHT + i * (buildingHeight - WALL_HEIGHT));
            rect.setZ(BUILDING_DEPTH);
            rect.setHeight(WALL_HEIGHT);
            rect.setWidth(buildingWidth);
            walls.add(rect);
        }

        for(int i = 0; i < 2; i++) {
            GhostRectangle rect = new GhostRectangle();
            rect.setType(RectangleType.BUILDING);
            rect.setNormal(new Vector3(0, 0, 1));
            rect.setX(-WALL_HEIGHT + i * (buildingWidth - WALL_HEIGHT));
            rect.setY(0);
            rect.setZ(BUILDING_DEPTH);
            rect.setHeight(buildingHeight - 2 * WALL_HEIGHT);
            rect.setWidth(WALL_HEIGHT);
            walls.add(rect);
        }
        return walls;

    }

    Array<GhostRectangle>  createFaceWalls(Array<Room> rooms) {
        Array<GhostLine> lines = createLines(rooms);
        lines = breakLines(lines, rooms);
        Array<GhostRectangle> result = findInitialRects(lines);
        joinRects(result);
        return result;
    }

    Array<GhostLine> createLines(Array<Room> rooms){
        Set<GhostLine> lines = new HashSet<GhostLine>() ;
        for(Room room: rooms) {
            lines.add(new GhostLine(room.getOrigin().y + room.getHeight() - WALL_HEIGHT, 0, buildingWidth - 2 * WALL_HEIGHT));
            lines.add(new GhostLine(room.getOrigin().y + WALL_HEIGHT, 0, buildingWidth - 2 * WALL_HEIGHT));
        }
        lines.add(new GhostLine(0, 0, buildingWidth - 2 * WALL_HEIGHT));
        lines.add (new GhostLine(buildingHeight - 2 * WALL_HEIGHT, 0, buildingWidth - 2 * WALL_HEIGHT));
        Array<GhostLine> uniquesLines = new Array<GhostLine>();
        for(GhostLine line: lines) {
            uniquesLines.add(line);
        }
        return uniquesLines;
    }

    Array<GhostRectangle> createConnectingWalls(Array<Room> rooms) {
        Array<GhostRectangle> result = new Array<GhostRectangle>();
        for(Room room: rooms) {
            //top
            GhostRectangle top = new GhostRectangle();
            top.setWidth(room.getWidth() - 2 * WALL_HEIGHT);
            top.setHeight(BUILDING_DEPTH);
            Vector3 topNormal = new Vector3(0, -1, 0);
            top.setNormal(topNormal);
            top.setType(RectangleType.ROOM);
            top.setX(room.getOrigin().x + WALL_HEIGHT);
            top.setY(room.getOrigin().y + room.getHeight() - WALL_HEIGHT);
            result.add(top);

            //bottom
            GhostRectangle bottom = new GhostRectangle();
            bottom.setWidth(room.getWidth() - 2 * WALL_HEIGHT);
            bottom.setHeight(BUILDING_DEPTH);
            Vector3 bottomNormal = new Vector3(0, 1, 0);
            bottom.setNormal(bottomNormal);
            bottom.setType(RectangleType.ROOM);
            bottom.setX(room.getOrigin().x + WALL_HEIGHT);
            bottom.setY(room.getOrigin().y + WALL_HEIGHT);
            result.add(bottom);

            //left
            GhostRectangle left = new GhostRectangle();
            left.setWidth(BUILDING_DEPTH);
            left.setHeight(room.getHeight() - 2 * WALL_HEIGHT);
            Vector3 leftNormal = new Vector3(1, 0, 0);
            left.setNormal(leftNormal);
            left.setType(RectangleType.ROOM);
            left.setX(room.getOrigin().x + WALL_HEIGHT);
            left.setY(room.getOrigin().y + WALL_HEIGHT);
            result.add(left);

            //right
            GhostRectangle right = new GhostRectangle();
            right.setWidth(BUILDING_DEPTH);
            right.setHeight(room.getHeight() - 2 * WALL_HEIGHT);
            Vector3 rightNormal = new Vector3(-1, 0, 0);
            right.setNormal(rightNormal);
            right.setType(RectangleType.ROOM);
            right.setX(room.getOrigin().x + room.getWidth() - WALL_HEIGHT);
            right.setY(room.getOrigin().y + WALL_HEIGHT);
            result.add(right);


        }
        return  result;

    }

    Array<GhostLine> breakLines(Array<GhostLine> lines, Array<Room> rooms){
        rooms.sort();
        int size = lines.size;

        for(int i = 0; i < size; i++) {
            GhostLine current = lines.get(i);
            for(Room room: rooms) {
                float temp = current.getY()  - room.getOrigin().y - WALL_HEIGHT;
                if(temp >= 0 && temp < room.getHeight()-2*WALL_HEIGHT) {
                    GhostLine fragment = new GhostLine(current.getY(), current.getX(), room.getOrigin().x + WALL_HEIGHT - current.getX());
                    lines.add(fragment);
                    current.setX(room.getOrigin().x + room.getWidth() - WALL_HEIGHT);
                    current.setLength(current.getLength() - fragment.getLength() - room.getWidth() + 2 * WALL_HEIGHT);
                }

            }
        }

        return  lines;
    }

    Array<GhostRectangle> findInitialRects(Array<GhostLine> lines){
        lines.sort();
        Array<GhostRectangle> result = new Array<GhostRectangle>();
        float currentLevel = buildingHeight -  2 * WALL_HEIGHT;
        for(int i = 1; i < lines.size ; i++) {
            GhostLine current = lines.get(i);

            GhostRectangle rect = new GhostRectangle();
            rect.setType(RectangleType.WALL);
            rect.setNormal(new Vector3(0, 0, 1));
            rect.setWidth(current.getLength());
            rect.setHeight(currentLevel - current.getY());
            rect.setX(current.getX());
            rect.setY(current.getY());
            rect.setZ(BUILDING_DEPTH);
            result.add(rect);
            if(i + 1 != lines.size) {
                if (current.getX() >= lines.get(i + 1).getX()) {
                    currentLevel = current.getY();
                }
            }

        }
        return result;
    }

    void joinRects(Array<GhostRectangle> rects){
        rects.sort();
        for(int i = 0; i < rects.size - 1; i++) {
            GhostRectangle current = rects.get(i);
            GhostRectangle next = rects.get(i+1);
            if(current.getX() == next.getX() && current.getWidth() == next.getWidth() && current.getHeight() + current.getY() == next.getY()) {
                current.setHeight(current.getHeight() + next.getHeight());
                rects.removeIndex(i+1);
                i--;
            }
        }
    }
}
