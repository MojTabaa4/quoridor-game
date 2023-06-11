package com.company;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SidePanel extends JPanel {
    private int playerTurn = 1;
    private final ArrayList<Player> players;
    private final Board board;

    private final JLabel name, wallsLeft;
    private final JButton up, down, left, right,
            up_right, up_left, down_left, down_right,
            blankCenter;


    public SidePanel(Board b, ArrayList<Player> p) {
        board = b;
        players = p;

        //Make buttons
        up = new JButton("UP");
        up.setFont(new Font("Arial", Font.BOLD, 18));
        up.setFocusPainted(false);
        up.setBorder(null);
        up.setBackground(players.get(playerTurn - 1).getColor());
        up.setForeground(new Color(6, 1, 4));
        up.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("UP", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations, Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }

            board.repaint();
        });

        down = new JButton("DOWN");
        down.setFont(new Font("Arial", Font.BOLD, 18));
        down.setFocusPainted(false);
        down.setBorder(null);
        down.setBackground(players.get(playerTurn - 1).getColor());
        down.setForeground(new Color(6, 1, 4));
        down.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("DOWN", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }
                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }

            board.repaint();
        });

        left = new JButton("LEFT");
        left.setFont(new Font("Arial", Font.BOLD, 18));
        left.setFocusPainted(false);
        left.setBorder(null);
        left.setBackground(players.get(playerTurn - 1).getColor());
        left.setForeground(new Color(6, 1, 4));
        left.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("LEFT", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }

            board.repaint();
        });

        right = new JButton("RIGHT");
        right.setFont(new Font("Arial", Font.BOLD, 18));
        right.setFocusPainted(false);
        right.setBorder(null);
        right.setBackground(players.get(playerTurn - 1).getColor());
        right.setForeground(new Color(6, 1, 4));
        right.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("RIGHT", board);
            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }
            board.repaint();
        });

        up_right = new JButton("Up Right");
        up_right.setFocusPainted(false);
        up_right.setBackground(players.get(playerTurn - 1).getColor().darker());
        up_right.setForeground(new Color(6, 1, 4));
        up_right.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("U_R", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);
                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }
            board.repaint();
        });

        up_left = new JButton("Up Left");
        up_left.setFocusPainted(false);
        up_left.setBackground(players.get(playerTurn - 1).getColor().darker());
        up_left.setForeground(new Color(6, 1, 4));
        up_left.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("U_L", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }
            board.repaint();
        });

        down_left = new JButton("Down Left");
        down_left.setFocusPainted(false);
        down_left.setBackground(players.get(playerTurn - 1).getColor().darker());
        down_left.setForeground(new Color(6, 1, 4));
        down_left.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("D_L", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }
            board.repaint();
        });

        down_right = new JButton("Down Right");
        down_right.setFocusPainted(false);
        down_right.setBackground(players.get(playerTurn - 1).getColor().darker());
        down_right.setForeground(new Color(6, 1, 4));
        down_right.addActionListener(e -> {
            boolean moved = players.get(playerTurn - 1).MoveBead("D_R", board);

            if (moved) {
                if (players.get(playerTurn - 1).checkWin()) {
                    board.repaint();
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + players.get(playerTurn - 1).getName()
                                    + ", you won", null, JOptionPane.INFORMATION_MESSAGE);

                    Cup.getWinners().add(players.get(playerTurn - 1));
                    if (Cup.isCupMode()) {
                        synchronized (Cup.getObjectLock()) {
                            Cup.getObjectLock().notify();
                        }
                    } else {
                        System.exit(-1);
                    }
                }

                playerTurn++;
                if (playerTurn > players.size())
                    playerTurn = 1;
            }
            board.repaint();
        });

        //make space between keys
        blankCenter = new JButton(" ");
        blankCenter.setFocusPainted(false);
        blankCenter.setBorder(null);
        blankCenter.setBackground(players.get(playerTurn - 1).getColor());
        blankCenter.setForeground(new Color(6, 1, 4));

        JPanel move = new JPanel();
        move.setLayout(new GridLayout(3, 3));

        //add buttons to move panel
        move.add(up_left);
        move.add(up);
        move.add(up_right);
        move.add(left);
        move.add(blankCenter);
        move.add(right);
        move.add(down_left);
        move.add(down);
        move.add(down_right);

        JPanel PlayerInfo = new JPanel();
        PlayerInfo.setLayout(new GridLayout(2, 1));

        name = new JLabel("Name: " + players.get(playerTurn - 1).getName());
        name.setFont(new Font("Arial", Font.BOLD, 18));
        name.setHorizontalAlignment(SwingConstants.CENTER);

        //add listener to buttons for name label
        class NameListener implements ChangeListener {
            public void stateChanged(ChangeEvent event) {
                name.setText("Name: " + players.get(playerTurn - 1).getName());
            }
        }
        ChangeListener listenerName = new NameListener();
        up.addChangeListener(listenerName);
        down.addChangeListener(listenerName);
        left.addChangeListener(listenerName);
        right.addChangeListener(listenerName);
        up_left.addChangeListener(listenerName);
        up_right.addChangeListener(listenerName);
        down_left.addChangeListener(listenerName);
        down_right.addChangeListener(listenerName);

        JLabel color = new JLabel("Color:");
        color.setFont(new Font("Arial", Font.BOLD, 18));
        color.setHorizontalAlignment(SwingConstants.CENTER);

        wallsLeft = new JLabel("Walls Left: " + players.get(playerTurn - 1).getNumWalls());
        wallsLeft.setFont(new Font("Arial", Font.BOLD, 18));
        wallsLeft.setHorizontalAlignment(SwingConstants.CENTER);

        //add listener to buttons for wall label
        class WallListener implements ChangeListener {
            public void stateChanged(ChangeEvent event) {
                wallsLeft.setText("Walls Left: " + players.get(playerTurn - 1).getNumWalls());
            }
        }
        ChangeListener listenerWall = new WallListener();
        up.addChangeListener(listenerWall);
        down.addChangeListener(listenerWall);
        left.addChangeListener(listenerWall);
        right.addChangeListener(listenerWall);
        up_left.addChangeListener(listenerWall);
        up_right.addChangeListener(listenerWall);
        down_left.addChangeListener(listenerWall);
        down_right.addChangeListener(listenerWall);

        //add listener to buttons for color of player
        class ColorListener implements ChangeListener {
            public void stateChanged(ChangeEvent event) {
                up.setBackground(players.get(playerTurn - 1).getColor());
                down.setBackground(players.get(playerTurn - 1).getColor());
                left.setBackground(players.get(playerTurn - 1).getColor());
                right.setBackground(players.get(playerTurn - 1).getColor());
                up_left.setBackground(players.get(playerTurn - 1).getColor().darker());
                up_right.setBackground(players.get(playerTurn - 1).getColor().darker());
                down_left.setBackground(players.get(playerTurn - 1).getColor().darker());
                down_right.setBackground(players.get(playerTurn - 1).getColor().darker());
                blankCenter.setBackground(players.get(playerTurn - 1).getColor());
            }
        }
        ChangeListener listenerColor = new ColorListener();
        up.addChangeListener(listenerColor);
        down.addChangeListener(listenerColor);
        left.addChangeListener(listenerColor);
        right.addChangeListener(listenerColor);
        up_left.addChangeListener(listenerColor);
        up_right.addChangeListener(listenerColor);
        down_left.addChangeListener(listenerColor);
        down_right.addChangeListener(listenerColor);

        PlayerInfo.add(name);
        PlayerInfo.add(wallsLeft);

        JPanel labelImage = new JPanel();
        ImageIcon pic = new ImageIcon("src/com/company/quoridor.jpg");
        Image resizedImage = pic.getImage().getScaledInstance(470, 130, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        labelImage.add(new JLabel(resizedIcon));

        JPanel labelMove = new JPanel();
        labelMove.setLayout(new GridLayout(3, 1));
        labelMove.add(new JLabel("________________________________", SwingConstants.CENTER));

        JLabel movement = new JLabel("Arrow keys", SwingConstants.CENTER);
        movement.setFont(new Font("Arial", Font.BOLD, 25));
        labelMove.add(movement);
        labelMove.add(new JLabel("Please utilize the provided buttons to navigate your pawn.", SwingConstants.CENTER));

        JPanel labelInfo = new JPanel();
        labelInfo.setLayout(new GridLayout(3, 1));
        labelInfo.add(new JLabel("________________________________", SwingConstants.CENTER));

        JLabel information = new JLabel("Player Information's Turn", SwingConstants.CENTER);
        information.setFont(new Font("Arial", Font.BOLD, 25));
        labelInfo.add(information);

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new GridLayout(2, 2));

        JButton saveButton = new JButton("Save Game");
        saveButton.setFont(new Font("Arial", Font.BOLD, 30));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> saveGame());
        if (Cup.isCupMode()) {
            saveButton.setEnabled(false);
            saveButton.setText("Cup Mode");
        }
        JButton menuButton = new JButton("Main Menu");
        menuButton.setFont(new Font("Arial", Font.BOLD, 30));
        menuButton.setFocusPainted(false);
        menuButton.addActionListener(e -> {
            QuoridorMain.startPage();
            Cup.setCupMode(false);
        });

        JLabel blank = new JLabel("", SwingConstants.CENTER);
        JLabel blank1 = new JLabel("", SwingConstants.CENTER);

        savePanel.add(blank);
        savePanel.add(blank1);
        savePanel.add(menuButton);
        savePanel.add(saveButton);

        this.setLayout(new GridLayout(6, 1));
        this.add(labelImage);
        this.add(labelInfo);
        this.add(PlayerInfo);
        this.add(labelMove);
        this.add(move);
        this.add(savePanel);
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int i) {
        playerTurn = i;
    }

    public void NextPlayer() {
        playerTurn++;
    }

    public void RefreshInfo() {
        name.setText("Name: " + players.get(playerTurn - 1).getName());

        wallsLeft.setText("Walls Left: " + players.get(playerTurn - 1).getNumWalls());

        up.setBackground(players.get(playerTurn - 1).getColor());
        down.setBackground(players.get(playerTurn - 1).getColor());
        left.setBackground(players.get(playerTurn - 1).getColor());
        right.setBackground(players.get(playerTurn - 1).getColor());
        up_left.setBackground(players.get(playerTurn - 1).getColor().darker());
        up_right.setBackground(players.get(playerTurn - 1).getColor().darker());
        down_left.setBackground(players.get(playerTurn - 1).getColor().darker());
        down_right.setBackground(players.get(playerTurn - 1).getColor().darker());
        blankCenter.setBackground(players.get(playerTurn - 1).getColor());
    }

    public void saveGame() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("saveplayer.json"));
            //save player 1 information
            JsonObject player1Details = new JsonObject();
            player1Details.put("name", players.get(0).getName());
            player1Details.put("wallsLeft", players.get(0).getNumWalls());
            player1Details.put("x", players.get(0).getX());
            player1Details.put("y", players.get(0).getY());

            //save player 2 information
            JsonObject player2Details = new JsonObject();
            player2Details.put("name", players.get(1).getName());
            player2Details.put("wallsLeft", players.get(1).getNumWalls());
            player2Details.put("x", players.get(1).getX());
            player2Details.put("y", players.get(1).getY());

            //save player's turn
            JsonObject gameDetail = new JsonObject();
            gameDetail.put("playerTurn", this.getPlayerTurn());
            gameDetail.put("player1", player1Details);
            gameDetail.put("player2", player2Details);


            if (players.size() == 4) {
                //save player 3 information
                JsonObject player3Details = new JsonObject();
                player3Details.put("name", players.get(2).getName());
                player3Details.put("wallsLeft", players.get(2).getNumWalls());
                player3Details.put("x", players.get(2).getX());
                player3Details.put("y", players.get(2).getY());

                //save player 4 information
                JsonObject player4Details = new JsonObject();
                player4Details.put("name", players.get(3).getName());
                player4Details.put("wallsLeft", players.get(3).getNumWalls());
                player4Details.put("x", players.get(3).getX());
                player4Details.put("y", players.get(3).getY());

                gameDetail.put("player3", player3Details);
                gameDetail.put("player4", player4Details);
            }

            //save walls coordinates x1, y1, x2, y2
            JsonArray wallarray = new JsonArray();
            for (int i = 0; i < board.getNumWalls(); i++) {
                wallarray.add(Math.round(board.getWall(i).getX1()) + " " + Math.round(board.getWall(i).getY1()) + " "
                        + Math.round(board.getWall(i).getX2()) + " " + Math.round(board.getWall(i).getY2()));
            }

            gameDetail.put("walls", wallarray);

            //serialize game information with writer
            Jsoner.serialize(gameDetail, writer);

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
