package com.company;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player {
    private String name;
    private int x, y, NumWalls;
    private Color color;
    private Line2D.Double win_place;
    private static boolean CanWin = false;
    private static ArrayList<String> CheckCoordinates = new ArrayList<>();

    public void setWin_place(Line2D.Double win_place) {
        this.win_place = win_place;
    }

    // win lines locations
    public static final Line2D.Double UP = new Line2D.Double(0, 0, 9, 0);
    public static final Line2D.Double DOWN = new Line2D.Double(0, 8, 8, 8);
    public static final Line2D.Double LEFT = new Line2D.Double(0, 0, 0, 9);
    public static final Line2D.Double RIGHT = new Line2D.Double(8, 0, 8, 8);

    public Player(int x_pos, int y_pos, Line2D.Double wp, String nm, int wall, Color cl) {
        x = x_pos;
        y = y_pos;
        win_place = wp;
        name = nm;
        NumWalls = wall;
        color = cl;
    }

    //return boolean and add move bead if its possible
    public boolean MoveBead(String dir, Board b) {
        boolean CanMove = true;
        boolean WallBlocked = false;
        boolean PlayerOccupied = false;
        boolean PlayerOccupied1 = false;
        boolean dir1;
        boolean dir2;
        Line2D.Double l = new Line2D.Double();

        if ("UP".equals(dir)) {
            l.setLine(x + .5, y + .5,
                    x + .5, y - .5);

            //if for jump over the wall
            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    CanMove = false;
                    WallBlocked = true;
                }
            }
            //if the place of movement is occupied by the other player
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y - 1)) {
                    PlayerOccupied = true;
                    CanMove = false;
                }
            }
            if (!WallBlocked && !PlayerOccupied) {
                y--;

                if (y < 0) {
                    y++;
                    CanMove = false;
                }

            } else if (PlayerOccupied) {
                CanMove = jump(dir, b);
            }
        } else if ("DOWN".equals(dir)) {
            l.setLine(x + .5, y + .5,
                    x + .5, y + 1.5);

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    CanMove = false;
                    WallBlocked = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y + 1)) {
                    PlayerOccupied = true;
                    CanMove = false;
                }
            }
            if (!WallBlocked && !PlayerOccupied) {
                y++;

                if (y > 8) {
                    y--;
                    CanMove = false;
                }

            } else if (PlayerOccupied) {
                CanMove = jump(dir, b);
            }
        } else if ("LEFT".equals(dir)) {
            l.setLine(x + .5, y + .5,
                    x - .5, y + .5);

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    CanMove = false;
                    WallBlocked = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x - 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied = true;
                    CanMove = false;
                }
            }
            if (!WallBlocked && !PlayerOccupied) {
                x--;

                if (x < 0) {
                    x++;
                    CanMove = false;
                }
            } else if (PlayerOccupied) {
                CanMove = jump(dir, b);
            }
        } else if ("RIGHT".equals(dir)) {
            l.setLine(x + .5, y + .5, x + 1.5, y + .5);

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    CanMove = false;
                    WallBlocked = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x + 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied = true;
                    CanMove = false;
                }
            }
            if (!WallBlocked && !PlayerOccupied) {
                x++;
                if (x > 8) {
                    x--;
                    CanMove = false;
                }
            } else if (PlayerOccupied) {
                CanMove = jump(dir, b);
            }
        } else if ("U_R".equals(dir)) {
            CanMove = false;
            l.setLine(x + .5, y + .5, x + 1.5, y - .5);
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y - 1)) {
                    PlayerOccupied = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x + 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied1 = true;
                }
            }

            dir1 = MoveBead("UP", b);
            if (dir1) {
                MoveBead("DOWN", b);
            }
            dir2 = MoveBead("RIGHT", b);
            if (dir2) {
                MoveBead("LEFT", b);
            }

            if ((!dir1 || !dir2) && (PlayerOccupied || PlayerOccupied1)) {
                CanMove = true;
            }

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double(b.getWall(i).getMidpoint().getX(), b.getWall(i).getMidpoint().getY())) == 0) {
                    CanMove = false;
                }
            }
            if (CanMove) {
                x++;
                y--;
                if (x > 8 || y < 0) {
                    x--;
                    y++;
                    CanMove = false;
                }
            }
        } else if ("U_L".equals(dir)) {
            CanMove = false;
            l.setLine(x + .5, y + .5, x - .5, y - .5);

            dir1 = MoveBead("UP", b);
            if (dir1) {
                MoveBead("DOWN", b);
            }
            dir2 = MoveBead("LEFT", b);
            if (dir2) {
                MoveBead("RIGHT", b);
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y - 1)) {
                    PlayerOccupied = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x - 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied1 = true;
                }
            }

            if ((!dir1 || !dir2) && (PlayerOccupied || PlayerOccupied1)) {
                CanMove = true;
            }
            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double(b.getWall(i).getMidpoint().getX(), b.getWall(i).getMidpoint().getY())) == 0) {
                    CanMove = false;
                }
            }

            if (CanMove) {
                x--;
                y--;
                if (x < 0 || y < 0) {
                    x++;
                    y++;
                    CanMove = false;
                }
            }
        } else if ("D_L".equals(dir)) {
            CanMove = false;
            l.setLine(x + .5, y + .5, x - .5, y + 1.5);
            dir1 = MoveBead("DOWN", b);
            if (dir1) {
                MoveBead("UP", b);
            }
            dir2 = MoveBead("LEFT", b);
            if (dir2) {
                MoveBead("RIGHT", b);
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x - 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y + 1)) {
                    PlayerOccupied1 = true;
                }
            }
            if ((!dir1 || !dir2) && (PlayerOccupied || PlayerOccupied1)) {
                CanMove = true;
            }
            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double(b.getWall(i).getMidpoint().getX(), b.getWall(i).getMidpoint().getY())) == 0) {
                    CanMove = false;
                }
            }
            if (CanMove) {
                x--;
                y++;
                if (x < 0 || y > 8) {
                    x++;
                    y--;
                    CanMove = false;
                }
            }
        } else if ("D_R".equals(dir)) {
            CanMove = false;
            l.setLine(x + .5, y + .5, x + 1.5, y + 1.5);
            dir1 = MoveBead("DOWN", b);
            if (dir1) {
                MoveBead("UP", b);
            }
            dir2 = MoveBead("RIGHT", b);
            if (dir2) {
                MoveBead("LEFT", b);
            }

            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == (y + 1)) {
                    PlayerOccupied = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == (x + 1) && b.getPlayer(i).getY() == y) {
                    PlayerOccupied1 = true;
                }
            }
            if ((!dir1 || !dir2) && (PlayerOccupied || PlayerOccupied1)) {
                CanMove = true;
            }
            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double(b.getWall(i).getMidpoint().getX(), b.getWall(i).getMidpoint().getY())) == 0) {
                    CanMove = false;
                }
            }
            if (CanMove) {
                x++;
                y++;
                if (x > 8 || y > 8) {
                    x--;
                    y--;
                    CanMove = false;
                }
            }
        } else {
            CanMove = false;
        }

        return CanMove;
    }

    //return boolean and add wall if it is possible
    public boolean placeWall(Wall wall, Board board) {
        boolean wallPlaced = true;

        if (!hasRemainingWalls()) {
            wallPlaced = false;
        } else if (!isValidWallSize(wall)) {
            wallPlaced = false;
        } else if (!isWithinBoardBounds(wall)) {
            wallPlaced = false;
        } else if (isOnBoardEdge(wall)) {
            wallPlaced = false;
        } else if (crossesExistingWall(wall, board)) {
            wallPlaced = false;
        } else {
            board.addWall(wall);
            NumWalls--;
            CanWin = false;

            // check the player can win
            int initialPlayerX = board.getPlayer(0).getX();
            int initialPlayerY = board.getPlayer(0).getY();
            canWin(board.getPlayer(0), board);
            CheckCoordinates.clear();
            board.getPlayer(0).setX(initialPlayerX);
            board.getPlayer(0).setY(initialPlayerY);

            if (CanWin) {
                CanWin = false;
                int secondPlayerX = board.getPlayer(1).getX();
                int secondPlayerY = board.getPlayer(1).getY();
                canWin(board.getPlayer(1), board);
                CheckCoordinates.clear();
                board.getPlayer(1).setX(secondPlayerX);
                board.getPlayer(1).setY(secondPlayerY);
            }

            if (board.getNumPlayers() == 4) {
                for (int i = 2; i < 4; i++) {
                    if (CanWin) {
                        CanWin = false;
                        int currentPlayerX = board.getPlayer(i).getX();
                        int currentPlayerY = board.getPlayer(i).getY();
                        canWin(board.getPlayer(i), board);
                        CheckCoordinates.clear();
                        board.getPlayer(i).setX(currentPlayerX);
                        board.getPlayer(i).setY(currentPlayerY);
                    }
                }
            }

            if (!CanWin) {
                board.removeWall(wall);
                NumWalls++;
                wallPlaced = false;
            }
        }

        return wallPlaced;
    }

    private boolean isValidWallSize(Wall wall) {
        double wallLength = Math.sqrt(Math.pow(wall.getPt1().getX() - wall.getPt2().getX(), 2)
                + Math.pow(wall.getPt1().getY() - wall.getPt2().getY(), 2));

        return wallLength == 2;
    }

    private boolean isWithinBoardBounds(Wall wall) {
        double pt1X = wall.getPt1().getX();
        double pt1Y = wall.getPt1().getY();
        double pt2X = wall.getPt2().getX();
        double pt2Y = wall.getPt2().getY();

        return pt1X >= 0 && pt1Y >= 0 && pt2X >= 0 && pt2Y >= 0 &&
                pt1X <= 9 && pt1Y <= 9 && pt2X <= 9 && pt2Y <= 9;
    }

    private boolean isOnBoardEdge(Wall wall) {
        double pt1X = wall.getPt1().getX();
        double pt1Y = wall.getPt1().getY();
        double pt2X = wall.getPt2().getX();
        double pt2Y = wall.getPt2().getY();

        return pt1X == 0 && pt2X == 0 || pt1X == 9 && pt2X == 9 ||
                pt1Y == 0 && pt2Y == 0 || pt1Y == 9 && pt2Y == 9;
    }

    private boolean hasRemainingWalls() {
        return NumWalls > 0;
    }

    private boolean crossesExistingWall(Wall wall, Board board) {
        for (int i = 0; i < board.getNumWalls(); i++) {
            if (wall.getMidpoint().equals(board.getWall(i).getMidpoint())) {
                return true;
            }
        }

        // Check for overlapping walls
        double wallMidX1 = (wall.getMidpoint().getX() + wall.getPt1().getX()) / 2;
        double wallMidY1 = (wall.getMidpoint().getY() + wall.getPt1().getY()) / 2;
        double wallMidX2 = (wall.getMidpoint().getX() + wall.getPt2().getX()) / 2;
        double wallMidY2 = (wall.getMidpoint().getY() + wall.getPt2().getY()) / 2;

        for (int i = 0; i < board.getNumWalls(); i++) {
            Wall existingWall = board.getWall(i);
            double existingMidX1 = (existingWall.getMidpoint().getX() + existingWall.getPt1().getX()) / 2;
            double existingMidY1 = (existingWall.getMidpoint().getY() + existingWall.getPt1().getY()) / 2;
            double existingMidX2 = (existingWall.getMidpoint().getX() + existingWall.getPt2().getX()) / 2;
            double existingMidY2 = (existingWall.getMidpoint().getY() + existingWall.getPt2().getY()) / 2;

            Point2D.Double midpoint1 = new Point2D.Double(existingMidX1, existingMidY1);
            Point2D.Double midpoint2 = new Point2D.Double(existingMidX2, existingMidY2);

            if (new Point2D.Double(wallMidX1, wallMidY1).equals(midpoint1) ||
                    new Point2D.Double(wallMidX1, wallMidY1).equals(midpoint2) ||
                    new Point2D.Double(wallMidX2, wallMidY2).equals(midpoint1) ||
                    new Point2D.Double(wallMidX2, wallMidY2).equals(midpoint2)) {
                return true;
            }
        }

        return false;
    }

    //this is called when there is a player impeding the current players path
    public boolean jump(String dir, Board b) {

        boolean JumpMove = true;
        boolean WallBlock = false;
        boolean PlayerOccupied = false;
        Line2D.Double l = new Line2D.Double();

        if ("UP".equals(dir)) {
            //set the size of jump (2 tile)
            l.setLine(x + .5, y + .5, x + .5, y - 1.5);
            y -= 2;

            //for jump over the walls
            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    JumpMove = false;
                    WallBlock = true;
                }
            }
            //for tile that occupied by another player
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == y && !b.getPlayer(i).equals(this)) {
                    JumpMove = false;
                    PlayerOccupied = true;
                }
            }

            if (WallBlock || PlayerOccupied) {
                y += 2;
            } else if (y < 0) {
                y += 2;
                JumpMove = false;
            }
        } else if ("DOWN".equals(dir)) {
            l.setLine(x + .5, y + .5, x + .5, y + 2.5);

            y += 2;

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    JumpMove = false;
                    WallBlock = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == y && !b.getPlayer(i).equals(this)) {
                    JumpMove = false;
                    PlayerOccupied = true;
                }
            }

            if (WallBlock || PlayerOccupied) {
                y -= 2;
            } else if (y > 8) {
                y -= 2;
                JumpMove = false;
            }
        } else if ("LEFT".equals(dir)) {
            l.setLine(x + .5, y + .5, x - 1.5, y + .5);

            x -= 2;

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    JumpMove = false;
                    WallBlock = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == y && !b.getPlayer(i).equals(this)) {
                    JumpMove = false;
                    PlayerOccupied = true;
                }
            }

            if (WallBlock || PlayerOccupied) {
                x += 2;
            } else if (x < 0) {
                x += 2;
                JumpMove = false;
            }
        } else if ("RIGHT".equals(dir)) {
            l.setLine(x + .5, y + .5, x + 2.5, y + .5);

            x += 2;

            for (int i = 0; i < b.getNumWalls(); i++) {
                if (l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                        (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) == 0 ||

                        l.ptSegDist(new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) == 0) {
                    JumpMove = false;
                    WallBlock = true;
                }
            }
            for (int i = 0; i < b.getNumPlayers(); i++) {
                if (b.getPlayer(i).getX() == x && b.getPlayer(i).getY() == y && !b.getPlayer(i).equals(this)) {
                    JumpMove = false;
                    PlayerOccupied = true;
                }
            }

            if (WallBlock || PlayerOccupied) {
                x -= 2;
            } else if (x > 8) {
                x -= 2;
                JumpMove = false;
            }
        } else {
            JumpMove = false;
        }

        return JumpMove;
    }

    //Getters & Setters
    public void setNumWalls(int n) {
        NumWalls = n;
    }

    public void addWall() {
        NumWalls++;
    }

    public int getNumWalls() {
        return NumWalls;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public static void canWin(Player player, Board board) {
        int bx = player.x, by = player.y;
        if (player.MoveBead("UP", board)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, board);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("DOWN", board)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, board);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("RIGHT", board)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, board);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("LEFT", board)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, board);
            }
        }
    }

    public boolean checkWin() {
        return this.win_place.ptLineDist(new Point2D.Double(x, y)) == 0;
    }
}
