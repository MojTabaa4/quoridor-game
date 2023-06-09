package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Board extends JComponent implements MouseListener, MouseMotionListener {
    private ArrayList<Player> players;
    private ArrayList<Wall> WallsList;
    private Line2D.Double mouseDragLine;
    private SidePanel side;

    public Board(ArrayList<Player> p) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        players = new ArrayList<>();
        WallsList = new ArrayList<>();
        mouseDragLine = new Line2D.Double(0, 0, 0, 0);

        players.addAll(p);
    }

    public void add(SidePanel s) {
        side = s;
    }

    //Paint board component each time (board.repaint)
    public void paintComponent(Graphics g) {
        Graphics2D graphic = (Graphics2D) g;

        int SIZE_CONST = 70;

        //draw two backgrounds that overlap each other
        graphic.setColor(new Color(81, 11, 133));
        graphic.fill(new Rectangle(0, 0, SIZE_CONST * 9 + 15, SIZE_CONST * 9 + 15));
        graphic.setColor(new Color(0, 0, 0));
        graphic.fill(new Rectangle(0, 0, SIZE_CONST * 9, SIZE_CONST * 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (Player player : players) {
                    if (player.getX() == i && player.getY() == j) {
                        //color of each bead
                        graphic.setColor(player.getColor());
                        graphic.fill(new Rectangle2D.Double(SIZE_CONST * i + (SIZE_CONST / 4), SIZE_CONST * j + (SIZE_CONST / 4),
                                SIZE_CONST / 2, SIZE_CONST / 2));
                        //border of beads
                        graphic.setColor(Color.yellow);
                        graphic.draw(new Rectangle2D.Double(SIZE_CONST * i + (SIZE_CONST / 4), SIZE_CONST * j + (SIZE_CONST / 4),
                                SIZE_CONST / 2, SIZE_CONST / 2));
                    }
                }
                //draw grooves that walls place in it
                graphic.setColor(new Color(255, 29, 170));
                graphic.draw(new Rectangle(SIZE_CONST * i, SIZE_CONST * j, SIZE_CONST - 5, SIZE_CONST - 5));
            }
        }

        //draw walls
        graphic.setColor(new Color(255, 234, 0));
        for (Wall wall : WallsList) {
            graphic.draw(new Line2D.Double(wall.getX1() * SIZE_CONST, wall.getY1() * SIZE_CONST,
                    wall.getX2() * SIZE_CONST, wall.getY2() * SIZE_CONST));
        }
        //draw number of coordinates of tiles (y)
        for (int i = 0; i < 10; i++)
            graphic.drawString(Integer.toString(i), SIZE_CONST * i, SIZE_CONST * 9 + 12);
        //draw number of coordinates of tiles (x)
        for (int i = 0; i < 9; i++)
            graphic.drawString(Integer.toString(i), SIZE_CONST * 9 + 4, SIZE_CONST * i + 8);

        //for graphical draw of wall in the board
        graphic.draw(mouseDragLine);
    }

    //GET AND SET METHODS

    public void addWall(Wall w) {
        WallsList.add(w);
    }

    public void removeWall(Wall w) {
        WallsList.remove(w);
    }

    public Wall getWall(int i) {
        return WallsList.get(i);
    }

    public int getNumWalls() {
        return WallsList.size();
    }

    public Player getPlayer(int i) {
        return players.get(i);
    }

    public int getNumPlayers() {
        return players.size();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseDragLine.setLine(0, 0, 0, 0);
    }

    // just draw a point when mouse pressed
    @Override
    public void mousePressed(MouseEvent e) {
        mouseDragLine.setLine(e.getX(), e.getY(), e.getX(), e.getY());
    }

    // just draw a line when mouse dragged
    @Override
    public void mouseDragged(MouseEvent e) {
        double x = mouseDragLine.getX1();
        double y = mouseDragLine.getY1();
        mouseDragLine.setLine(x, y, e.getX(), e.getY());

        this.repaint();
    }

    // place wall if we can
    @Override
    public void mouseReleased(MouseEvent e) {
        boolean placed = players.get(side.getPlayerTurn() - 1).placeWall
                (new Wall(this.getX1(this), this.getY1(this), this.getX2(this), this.getY2(this)), this);

        if (placed) {
            side.NextPlayer();
            if (side.getPlayerTurn() > players.size())
                side.setPlayerTurn(1);
            side.RefreshInfo();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Your wall cannot be placed in here,try again", "Wall placement error", JOptionPane.ERROR_MESSAGE);
        }

        this.repaint();
        mouseDragLine.setLine(0, 0, 0, 0);
    }

    //draw wall with mouse drag
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    //convert the coordinate of the new wall on the board between 0-9
    public int getX1(Board b) {
        return (int) Math.round(b.mouseDragLine.getX1() / 70);
    }

    public int getY1(Board b) {
        return (int) Math.round(b.mouseDragLine.getY1() / 70);
    }

    public int getX2(Board b) {
        return (int) Math.round(b.mouseDragLine.getX2() / 70);
    }

    public int getY2(Board b) {
        return (int) Math.round(b.mouseDragLine.getY2() / 70);
    }

}

