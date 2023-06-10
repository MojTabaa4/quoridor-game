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
    private ArrayList<Wall> wallsList;
    private Line2D.Double mouseDragLine;
    private SidePanel side;

    public Board(ArrayList<Player> p) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        players = new ArrayList<>();
        wallsList = new ArrayList<>();
        mouseDragLine = new Line2D.Double(0, 0, 0, 0);

        players.addAll(p);
    }

    public void add(SidePanel s) {
        side = s;
    }

    //Paint board component each time (board.repaint)
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;

        final int TILE_SIZE = 70;
        final int BOARD_SIZE = 9;

        final Color backgroundColor = new Color(81, 11, 133);
        final Color boardColor = new Color(0, 0, 0);
        final Color beadBorderColor = Color.yellow;
        final Color wallColor = new Color(255, 29, 170);
        final Color wallNumberColor = new Color(255, 234, 0);
        final float lineThickness = 6;

        //draw two backgrounds that overlap each other
        graphics.setColor(backgroundColor);
        graphics.fill(new Rectangle(0, 0, TILE_SIZE * BOARD_SIZE + 15, TILE_SIZE * BOARD_SIZE + 15));

        graphics.setColor(boardColor);
        graphics.fill(new Rectangle(0, 0, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE));

        // Draw beads
        for (Player player : players) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (player.getX() == i && player.getY() == j) {
                        graphics.setColor(player.getColor());
                        graphics.fill(new Rectangle2D.Double(
                                TILE_SIZE * i + (TILE_SIZE / 4),
                                TILE_SIZE * j + (TILE_SIZE / 4),
                                TILE_SIZE / 2, TILE_SIZE / 2)
                        );

                        graphics.setColor(beadBorderColor);
                        graphics.draw(new Rectangle2D.Double(
                                TILE_SIZE * i + (TILE_SIZE / 4),
                                TILE_SIZE * j + (TILE_SIZE / 4),
                                TILE_SIZE / 2, TILE_SIZE / 2)
                        );
                    }
                }
            }
        }

        // Draw grooves
        graphics.setColor(wallColor);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                graphics.draw(new Rectangle(TILE_SIZE * i, TILE_SIZE * j, TILE_SIZE - 5, TILE_SIZE - 5));
            }
        }
        graphics.setStroke(new BasicStroke(lineThickness));
        // Draw walls
        graphics.setColor(wallNumberColor);
        for (Wall wall : wallsList) {
            graphics.draw(new Line2D.Double(wall.getX1() * TILE_SIZE, wall.getY1() * TILE_SIZE,
                    wall.getX2() * TILE_SIZE, wall.getY2() * TILE_SIZE));
        }

        // Draw tile coordinates (y)
        for (int i = 0; i < 10; i++) {
            graphics.drawString(Integer.toString(i), TILE_SIZE * i, TILE_SIZE * BOARD_SIZE + 12);
        }

        // Draw tile coordinates (x)
        for (int i = 0; i < BOARD_SIZE; i++) {
            graphics.drawString(Integer.toString(i), TILE_SIZE * BOARD_SIZE + 4, TILE_SIZE * i + 8);
        }

        // For graphical draw of wall in the board
        graphics.draw(mouseDragLine);
    }

    //GET AND SET METHODS

    public void addWall(Wall w) {
        wallsList.add(w);
    }

    public void removeWall(Wall w) {
        wallsList.remove(w);
    }

    public Wall getWall(int i) {
        return wallsList.get(i);
    }

    public int getNumWalls() {
        return wallsList.size();
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
        try {
            double startX = mouseDragLine.getX1();
            double startY = mouseDragLine.getY1();
            double endX = e.getX();
            double endY = e.getY();
            mouseDragLine.setLine(startX, startY, endX, endY);

            repaint();
        } catch (Exception ex) {
            // Handle or log the exception appropriately
            ex.printStackTrace();
        }
    }

    // place wall if we can
    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            Wall wall = new Wall(this.convertToBoardX1(), this.convertToBoardY1(),
                    this.convertToBoardX2(), this.convertToBoardY2());

            boolean wallPlaced = players.get(side.getPlayerTurn() - 1).placeWall(wall, this);

            if (wallPlaced) {
                side.NextPlayer();
                if (side.getPlayerTurn() > players.size())
                    side.setPlayerTurn(1);
                side.RefreshInfo();
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Your wall cannot be placed here. Please try again.",
                        "Wall placement error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            this.repaint();
            mouseDragLine.setLine(0, 0, 0, 0);
        } catch (Exception ex) {
            // Handle or log the exception appropriately
            ex.printStackTrace();
        }
    }

    //draw wall with mouse drag
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private int convertToBoardX1() {
        return (int) Math.round(mouseDragLine.getX1() / 70);
    }

    private int convertToBoardY1() {
        return (int) Math.round(mouseDragLine.getY1() / 70);
    }

    private int convertToBoardX2() {
        return (int) Math.round(mouseDragLine.getX2() / 70);
    }

    private int convertToBoardY2() {
        return (int) Math.round(mouseDragLine.getY2() / 70);
    }

}

