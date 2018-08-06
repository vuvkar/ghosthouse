package com.rockbite.inetrnship.ghosthouse.data;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.HashSet;
import java.util.Set;


public class GhostBuilding {
    private Array<Room1> rooms;

    private Array<GhostRectangle> faceWalls;
    private Array<GhostRectangle> roomWalls;
    private Array<GhostRectangle> roomConnectingWalls;
    private Array<GhostRectangle> buildingConnectingWalls;

    static public final float WALL_HEIGHT = 1.4f;

    static public final float BUILDING_DEPTH = 3f;

    private float buildingWidth;
    private float buildingHeight;
    private Vector2 buildingOrigin;

    public GhostBuilding(Array<Room1> rooms) {
        this.rooms = rooms;
        this.buildingConnectingWalls = createBuildingConnectingWalls(rooms);
        popInsideRooms();
        this.roomWalls = createRooms(rooms);
        this.faceWalls = createFaceWalls(rooms);
        this.roomConnectingWalls = createConnectingWalls(rooms);
    }

    // Pop rooms inside by WALL_HEIGHT / 2 by y = x line
    public void popInsideRooms() {
        for (Room room : rooms) {
            Vector2 newOrigin = new Vector2(room.origin.x + WALL_HEIGHT / 2.0f * MathUtils.cosDeg(45f),
                    room.origin.y + WALL_HEIGHT / 2.0f * MathUtils.sinDeg(45f));
            room.origin = newOrigin;
            room.height = room.height + WALL_HEIGHT * MathUtils.sinDeg(225f);
            room.width = room.width + WALL_HEIGHT * MathUtils.cosDeg(225f);
        }
    }

    // Returns all prepared rectangles
    public Array<GhostRectangle> getAllRects() {
        Array<GhostRectangle> rectangles = new Array<GhostRectangle>();

        rectangles.addAll(roomWalls);
        rectangles.addAll(faceWalls);
        rectangles.addAll(roomConnectingWalls);
        rectangles.addAll(buildingConnectingWalls);

        return rectangles;
    }

    // Creates rooms back walls
    Array<GhostRectangle> createRooms(Array<Room1> arr) {
        Array<GhostRectangle> result = new Array<GhostRectangle>();

        for (Room current : arr) {
            GhostRectangle rectangle = new GhostRectangle(RectangleType.ROOM);

            rectangle.setX(current.origin.x);
            rectangle.setY(current.origin.y);
            rectangle.setZ(0f);
            rectangle.setHeight(current.height);
            rectangle.setWidth(current.width);
            rectangle.setNormal(new Vector3(0f, 0f, 1f));

            result.add(rectangle);
        }

        return result;
    }

    // Finds building coordinates and creates building walls
    Array<GhostRectangle> createBuildingConnectingWalls(Array<Room1> arr) {

        Vector2 topRight = new Vector2();
        Vector2 bottomLeft = new Vector2();

        for (Room current : arr) {
            bottomLeft.x = Math.min(bottomLeft.x, current.origin.x);
            bottomLeft.y = Math.min(bottomLeft.y, current.origin.y);

            topRight.x = Math.max(topRight.x, current.origin.x + current.width);
            topRight.y = Math.max(topRight.y, current.origin.y + current.height);
        }

        topRight.x += WALL_HEIGHT / 2.0f * MathUtils.cosDeg(45f);
        topRight.y += WALL_HEIGHT / 2.0f * MathUtils.sinDeg(45f);

        bottomLeft.x += WALL_HEIGHT / 2.0f * MathUtils.cosDeg(225f);
        bottomLeft.y += WALL_HEIGHT / 2.0f * MathUtils.sinDeg(225f);

        buildingWidth = topRight.x - bottomLeft.x;
        buildingHeight = topRight.y - bottomLeft.y;

        buildingOrigin = bottomLeft;

        Room1 fakeRoom = new Room1(-1, bottomLeft, buildingWidth, buildingHeight);
        Array<Room1> rooms = new Array<Room1>();
        rooms.add(fakeRoom);

        Array<GhostRectangle> walls = createConnectingWalls(rooms);

        for (GhostRectangle wall : walls) {
            wall.setType(RectangleType.BUILDING);
            wall.getNormal().scl(-1f);
        }

        return walls;
    }

    // Creates outer walls
    Array<GhostRectangle> createFaceWalls(Array<Room1> rooms) {
        Array<GhostLine> lines = createLines(rooms);
        lines = breakLines(lines, rooms);
        Array<GhostRectangle> result = findInitialRects(lines);
        joinRects(result);

        return result;
    }

    // Main algorithm for creating face walls
    Array<GhostLine> createLines(Array<Room1> rooms) {
        Set<GhostLine> lines = new HashSet<GhostLine>();

        for (Room room : rooms) {
            lines.add(new GhostLine(room.origin.y + room.height, buildingOrigin.x, buildingWidth));
            lines.add(new GhostLine(room.origin.y, buildingOrigin.x, buildingWidth));
        }

        lines.add(new GhostLine(buildingOrigin.y, buildingOrigin.x, buildingWidth));
        lines.add(new GhostLine(buildingOrigin.y + buildingHeight, buildingOrigin.x, buildingWidth));
        Array<GhostLine> uniquesLines = new Array<GhostLine>();

        for (GhostLine line : lines) {
            uniquesLines.add(line);
        }

        return uniquesLines;
    }

    // Creates rooms inner side walls + ceiling/floor
    Array<GhostRectangle> createConnectingWalls(Array<Room1> rooms) {
        Array<GhostRectangle> result = new Array<GhostRectangle>();

        for (Room room : rooms) {
            //top
            GhostRectangle top = new GhostRectangle(RectangleType.CEILING);
            top.setWidth(room.width);
            top.setHeight(BUILDING_DEPTH);
            Vector3 topNormal = new Vector3(0, -1, 0);
            top.setNormal(topNormal);

            top.setX(room.origin.x);
            top.setY(room.origin.y + room.height);
            result.add(top);

            //bottom
            GhostRectangle bottom = new GhostRectangle(RectangleType.FLOOR);
            bottom.setWidth(room.width);
            bottom.setHeight(BUILDING_DEPTH);
            Vector3 bottomNormal = new Vector3(0, 1, 0);
            bottom.setNormal(bottomNormal);
            bottom.setX(room.origin.x);
            bottom.setY(room.origin.y);
            result.add(bottom);

            //left
            GhostRectangle left = new GhostRectangle(RectangleType.ROOM);
            left.setWidth(BUILDING_DEPTH);
            left.setHeight(room.height);
            Vector3 leftNormal = new Vector3(1, 0, 0);
            left.setNormal(leftNormal);
            left.setX(room.origin.x);
            left.setY(room.origin.y);
            result.add(left);

            //right
            GhostRectangle right = new GhostRectangle(RectangleType.ROOM);
            right.setWidth(BUILDING_DEPTH);
            right.setHeight(room.height);
            Vector3 rightNormal = new Vector3(-1, 0, 0);
            right.setNormal(rightNormal);
            right.setX(room.origin.x + room.width);
            right.setY(room.origin.y);
            result.add(right);
        }

        return result;

    }

    // Main algorithm (step 2)
    Array<GhostLine> breakLines(Array<GhostLine> lines, Array<Room1> rooms) {
        rooms.sort();
        int size = lines.size;

        for (int i = 0; i < size; i++) {
            GhostLine current = lines.get(i);

            for (Room room : rooms) {
                float temp = current.getY() - (int) room.origin.y;

                if (temp >= 0 && temp < room.height) {
                    GhostLine fragment = new GhostLine(current.getY(), current.getX(),
                            room.origin.x - current.getX());
                    lines.add(fragment);
                    current.setX(room.origin.x + room.width);
                    current.setLength(current.getLength() - fragment.getLength());
                    current.setLength(current.getLength() - room.width);
                }

            }
        }

        return lines;
    }

    // Main algorithm (step 3)
    Array<GhostRectangle> findInitialRects(Array<GhostLine> lines) {
        lines.sort();
        Array<GhostRectangle> result = new Array<GhostRectangle>();
        float currentLevel = buildingHeight + buildingOrigin.y;

        for (int i = 1; i < lines.size; i++) {
            GhostLine current = lines.get(i);

            GhostRectangle rect = new GhostRectangle(RectangleType.WALL);

            rect.setNormal(new Vector3(0f, 0f, 1f));
            rect.setWidth(current.getLength());
            rect.setHeight(currentLevel - current.getY());
            rect.setX(current.getX());
            rect.setY(current.getY());
            rect.setZ(BUILDING_DEPTH);

            result.add(rect);

            if (i + 1 != lines.size) {
                if (current.getX() >= lines.get(i + 1).getX()) {
                    currentLevel = current.getY();
                }
            }
        }

        return result;
    }

    // Main algorithm (step 4)
    void joinRects(Array<GhostRectangle> rectangles) {
        rectangles.sort();
        for (int i = 0; i < rectangles.size - 1; i++) {
            GhostRectangle current = rectangles.get(i);
            GhostRectangle next = rectangles.get(i + 1);

            if (current.getX() == next.getX() && current.getWidth() == next.getWidth()
                    && current.getHeight() + current.getY() == next.getY()) {
                current.setHeight(current.getHeight() + next.getHeight());
                rectangles.removeIndex(i + 1);
                i--;
            }
        }
    }
}