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

/////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Player(int x_pos, int y_pos, Line2D.Double wp, String nm, int wall, Color cl) {
        x = x_pos;
        y = y_pos;
        win_place = wp;
        name = nm;
        NumWalls = wall;
        color = cl;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
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

    //return boolean and add wall if its possible
    public boolean placeWall(Wall w, Board b) {
        boolean WallPlace = true;

        //wall size is more than 2
        if (Math.sqrt(Math.pow(w.getPt1().getX() - w.getPt2().getX(), 2)
                + Math.pow(w.getPt1().getY() - w.getPt2().getY(), 2)) != 2) {
            WallPlace = false;
        }

        //wall place outside of the board
        else if (w.getPt1().getX() < 0 || w.getPt2().getX() < 0) {
            WallPlace = false;
        } else if (w.getPt1().getY() < 0 || w.getPt2().getY() < 0) {
            WallPlace = false;
        } else if (w.getPt1().getX() > 9 || w.getPt2().getX() > 9) {
            WallPlace = false;
        } else if (w.getPt1().getY() > 9 || w.getPt2().getY() > 9) {
            WallPlace = false;
        }

        //wall is on board's edge
        else if (w.getPt1().getX() == 0 && w.getPt2().getX() == 0
                || w.getPt1().getX() == 9 && w.getPt2().getX() == 9
                || w.getPt1().getY() == 0 && w.getPt2().getY() == 0
                || w.getPt1().getY() == 9 && w.getPt2().getY() == 9) {
            WallPlace = false;
        }
        //player has no walls left
        else if (NumWalls == 0)
            WallPlace = false;

        //new wall crosses another wall
        for (int i = 0; i < b.getNumWalls(); i++) {
            if (w.getMidpoint().equals((b.getWall(i).getMidpoint())))
                WallPlace = false;
        }

        // check for overlaping walls
        // (middle of the wall + end of the wall)/2 == (middle of the wall + end of the wall)/2
        for (int i = 0; i < b.getNumWalls(); i++) {
            if ((new Point2D.Double((w.getMidpoint().getX() + w.getPt1().getX()) / 2,
                    (w.getMidpoint().getY() + w.getPt1().getY()) / 2)).equals
                    (new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                            (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) ||

                    (new Point2D.Double((w.getMidpoint().getX() + w.getPt1().getX()) / 2,
                            (w.getMidpoint().getY() + w.getPt1().getY()) / 2)).equals
                            (new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                    (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2)) ||

                    (new Point2D.Double((w.getMidpoint().getX() + w.getPt2().getX()) / 2,
                            (w.getMidpoint().getY() + w.getPt2().getY()) / 2)).equals
                            (new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt1().getX()) / 2,
                                    (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt1().getY()) / 2)) ||

                    (new Point2D.Double((w.getMidpoint().getX() + w.getPt2().getX()) / 2,
                            (w.getMidpoint().getY() + w.getPt2().getY()) / 2)).equals
                            (new Point2D.Double((b.getWall(i).getMidpoint().getX() + b.getWall(i).getPt2().getX()) / 2,
                                    (b.getWall(i).getMidpoint().getY() + b.getWall(i).getPt2().getY()) / 2))) {
                WallPlace = false;
            }
        }

        int x1, y1;
        if (WallPlace) {
            b.addWall(w);
            NumWalls--;
            CanWin = false;

            // check the player can win
            x1 = b.getPlayer(0).getX();
            y1 = b.getPlayer(0).getY();
            canWin(b.getPlayer(0), b);
            CheckCoordinates.clear();
            b.getPlayer(0).setX(x1);
            b.getPlayer(0).setY(y1);

            if (CanWin) {
                CanWin = false;
                x1 = b.getPlayer(1).getX();
                y1 = b.getPlayer(1).getY();
                canWin(b.getPlayer(1), b);
                CheckCoordinates.clear();
                b.getPlayer(1).setX(x1);
                b.getPlayer(1).setY(y1);
            }
            if (b.getNumPlayers() == 4) {
                for (int i = 2; i < 4; i++) {
                    if (CanWin) {
                        CanWin = false;
                        x1 = b.getPlayer(i).getX();
                        y1 = b.getPlayer(i).getY();
                        canWin(b.getPlayer(i), b);
                        CheckCoordinates.clear();
                        b.getPlayer(i).setX(x1);
                        b.getPlayer(i).setY(y1);
                    }
                }
            }

            if (!CanWin) {
                b.removeWall(w);
                NumWalls++;
                WallPlace = false;
            }
        }
        return WallPlace;
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void canWin(Player player, Board b) {
        int bx = player.x, by = player.y;
        if (player.MoveBead("UP", b)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, b);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("DOWN", b)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, b);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("RIGHT", b)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, b);
            }
        }
        player.x = bx;
        player.y = by;
        if (player.MoveBead("LEFT", b)) {
            if (!CheckCoordinates.contains(player.x + "" + player.y)) {
                CheckCoordinates.add(player.x + "" + player.y);
                if (player.checkWin()) {
                    CanWin = true;
                }
                canWin(player, b);
            }
        }
    }

    public boolean checkWin() {
        boolean Win = false;

        if (this.win_place.ptLineDist(new Point2D.Double(x, y)) == 0)
            Win = true;

        return Win;
    }
}
