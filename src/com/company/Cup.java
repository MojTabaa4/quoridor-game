package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cup {
    public static Container container;
    public static JFrame playFrame, playerFrame;
    private static Object objectLock = new Integer(1);
    private static ArrayList<Player> winners = new ArrayList<>();
    private static boolean cupMode = false;

    public static void CupChamp() {
        if (QuoridorMain.startFrame != null) {
            QuoridorMain.startFrame.setVisible(false);
        }
        cupMode = true;

        playerFrame = new JFrame();
        playerFrame.setTitle("Cup");
        playerFrame.setSize(620, 500);
        playerFrame.setFocusable(true);
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerFrame.setLayout(null);
        playerFrame.setResizable(false);

        container = playerFrame.getContentPane();

        //New Game key
        JPanel P4ButtonPanel = new JPanel();
        P4ButtonPanel.setBounds(100, 200, 400, 100);

        JButton p4Button = new JButton("4 Players");
        p4Button.setFont(new Font("Arial", Font.BOLD, 40));
        p4Button.setPreferredSize(new Dimension(400, 100));
        p4Button.setFocusPainted(false);
        p4Button.setBackground(new Color(174, 75, 255));
        p4Button.setForeground(new Color(255, 209, 247));

        JPanel P8buttonPanel = new JPanel();
        P8buttonPanel.setBounds(100, 50, 400, 100);

        JButton p8button = new JButton("8 Players");
        p8button.setFont(new Font("Arial", Font.BOLD, 40));
        p8button.setPreferredSize(new Dimension(400, 100));
        p8button.setFocusPainted(false);
        p8button.setBackground(new Color(174, 75, 255));
        p8button.setForeground(new Color(255, 209, 247));

        JPanel BackButtonPanel = new JPanel();
        BackButtonPanel.setBounds(100, 350, 400, 100);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 40));
        backButton.setPreferredSize(new Dimension(200, 100));
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(174, 75, 255));
        backButton.setForeground(new Color(255, 209, 247));


        P4ButtonPanel.add(p4Button);
        container.add(P4ButtonPanel);

        P8buttonPanel.add(p8button);
        container.add(P8buttonPanel);

        BackButtonPanel.add(backButton);
        container.add(BackButtonPanel);

        p4Button.addActionListener(e -> P4());

        p8button.addActionListener(e -> P8());

        backButton.addActionListener(e -> QuoridorMain.startPage());

        playerFrame.setVisible(true);
    }

    private static void P4() {
        winners.clear();
        playerFrame.setVisible(false);

        final ArrayList<Player> players = new ArrayList<>();
        final ArrayList<Player> player2 = new ArrayList<>();

        //create players
        Player p1 = new Player(4, 8, Player.UP, "Player 1", 10, Color.RED);
        Player p2 = new Player(4, 0, Player.DOWN, "Player 2", 10, Color.BLUE);
        Player p3 = new Player(4, 8, Player.UP, "Player 3", 10, Color.GREEN);
        Player p4 = new Player(4, 0, Player.DOWN, "Player 4", 10, Color.YELLOW);

        addPlayer(players, p1, p2, p3, p4);

        player2.add(players.get(0));
        player2.add(players.get(1));

        Thread thread = new Thread(() -> {
            synchronized (objectLock) {
                try {
                    //game1
                    JOptionPane.showMessageDialog(null,
                            "Game 1 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();
                    player2.add(players.get(2));
                    player2.add(players.get(3));

                    //game2
                    JOptionPane.showMessageDialog(null,
                            "Game 2 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();

                    winners.get(0).setY(8);
                    winners.get(0).setX(4);
                    winners.get(0).setNumWalls(10);
                    winners.get(0).setWin_place(Player.UP);

                    winners.get(1).setY(0);
                    winners.get(1).setX(4);
                    winners.get(1).setNumWalls(10);
                    winners.get(1).setWin_place(Player.DOWN);

                    player2.add(winners.get(0));
                    player2.add(winners.get(1));
                    winners.clear();

                    //game3
                    JOptionPane.showMessageDialog(null,
                            "Final Game \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + winners.get(0).getName()
                                    + ", you won in four person cup", null, JOptionPane.INFORMATION_MESSAGE);
                    cupMode = false;
                    QuoridorMain.startPage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private static void P8() {
        playerFrame.setVisible(false);
        winners.clear();

        final ArrayList<Player> players = new ArrayList<>();
        final ArrayList<Player> player2 = new ArrayList<>();

        //create players
        Player p1 = new Player(4, 8, Player.UP, "Player 1", 10, Color.RED);
        Player p2 = new Player(4, 0, Player.DOWN, "Player 2", 10, Color.BLUE);
        Player p3 = new Player(4, 8, Player.UP, "Player 3", 10, Color.GREEN);
        Player p4 = new Player(4, 0, Player.DOWN, "Player 4", 10, Color.YELLOW);
        Player p5 = new Player(4, 8, Player.UP, "Player 5", 10, Color.magenta);
        Player p6 = new Player(4, 0, Player.DOWN, "Player 6", 10, Color.cyan);
        Player p7 = new Player(4, 8, Player.UP, "Player 7", 10, Color.white);
        Player p8 = new Player(4, 0, Player.DOWN, "Player 8", 10, new Color(255, 96, 20));

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        addPlayer(players, p5, p6, p7, p8);

        player2.add(players.get(0));
        player2.add(players.get(1));

        Thread thread = new Thread(() -> {
            synchronized (objectLock) {
                try {
                    //game1
                    JOptionPane.showMessageDialog(null,
                            "Game 1 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();
                    player2.add(players.get(2));
                    player2.add(players.get(3));

                    //game2
                    JOptionPane.showMessageDialog(null,
                            "Game 2 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();
                    player2.add(players.get(4));
                    player2.add(players.get(5));

                    //game3
                    JOptionPane.showMessageDialog(null,
                            "Game 3 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();
                    player2.add(players.get(6));
                    player2.add(players.get(7));

                    //game4
                    JOptionPane.showMessageDialog(null,
                            "Game 4 \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();

                    winners.get(0).setX(4);
                    winners.get(0).setY(8);
                    winners.get(0).setNumWalls(10);
                    winners.get(0).setWin_place(Player.UP);

                    winners.get(1).setX(4);
                    winners.get(1).setY(0);
                    winners.get(1).setNumWalls(10);
                    winners.get(1).setWin_place(Player.DOWN);

                    winners.get(2).setX(4);
                    winners.get(2).setY(0);
                    winners.get(2).setNumWalls(10);
                    winners.get(2).setWin_place(Player.UP);

                    winners.get(3).setX(4);
                    winners.get(3).setY(8);
                    winners.get(3).setNumWalls(10);
                    winners.get(3).setWin_place(Player.DOWN);

                    players.clear();
                    players.add(winners.get(0));
                    players.add(winners.get(1));
                    players.add(winners.get(2));
                    players.add(winners.get(3));
                    winners.clear();

                    player2.add(players.get(0));
                    player2.add(players.get(1));

                    //game5
                    JOptionPane.showMessageDialog(null,
                            "Game 5 (semi final 1) \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    player2.clear();
                    player2.add(players.get(2));
                    player2.add(players.get(3));

                    //game6
                    JOptionPane.showMessageDialog(null,
                            "Game 6 (semi final 2) \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);

                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);

                    winners.get(0).setX(4);
                    winners.get(0).setY(8);
                    winners.get(0).setNumWalls(10);
                    winners.get(0).setWin_place(Player.UP);

                    winners.get(1).setX(4);
                    winners.get(1).setY(0);
                    winners.get(1).setNumWalls(10);
                    winners.get(1).setWin_place(Player.DOWN);


                    player2.clear();
                    player2.add(winners.get(0));
                    player2.add(winners.get(1));
                    winners.clear();

                    //game7
                    JOptionPane.showMessageDialog(null,
                            "Final Game \n" + player2.get(0).getName() + " VS " + player2.get(1).getName(),
                            null, JOptionPane.INFORMATION_MESSAGE);
                    playGame(player2, winners);
                    objectLock.wait();
                    playFrame.setVisible(false);
                    JOptionPane.showMessageDialog(null,
                            "Congratulations,Player "
                                    + winners.get(0).getName()
                                    + ", you won in Eight person cup", null, JOptionPane.INFORMATION_MESSAGE);
                    cupMode = false;
                    QuoridorMain.startPage();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void playGame(ArrayList<Player> players, ArrayList<Player> winners) {
        playFrame = new JFrame();
        playFrame.setTitle("IT PIONEER");
        playFrame.setSize(1130, 673);
        playFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        playFrame.setResizable(false);
        playFrame.setFocusable(true);

        final Board board = new Board(players);
        final SidePanel side = new SidePanel(board, players);

        board.add(side);

        playFrame.add(board, BorderLayout.CENTER);
        playFrame.add(side, BorderLayout.EAST);

        playFrame.setVisible(true);
    }

    private static void addPlayer(ArrayList<Player> players, Player p5, Player p6, Player p7, Player p8) {
        players.add(p5);
        players.add(p6);
        players.add(p7);
        players.add(p8);

        ArrayList<String> copy_names = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            boolean b = true;
            while (b) {
                players.get(i).setName(JOptionPane.showInputDialog(
                        null, "Enter Player " + (i + 1) + "'s Name", "player " + (i + 1),
                        JOptionPane.INFORMATION_MESSAGE));
                try {
                    players.get(i).setName(players.get(i).getName().replaceAll("\\W+", ""));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null, "The player's name must not be just a number/none alphabets!"
                            , "Error", JOptionPane.ERROR_MESSAGE);
                    i--;
                    break;
                }

                for (int j = 0; j < copy_names.size(); j++) {
                    if (copy_names.get(j).equals(players.get(i).getName())) {
                        JOptionPane.showMessageDialog(
                                null, "Each player should have a different name", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        i--;
                        break;
                    }
                }
                if (players.get(i).getName().equals("")) {
                    JOptionPane.showMessageDialog(
                            null, "Player's name must not be empty!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    i--;
                    break;
                }

                copy_names.add(players.get(i).getName());
                b = false;

            }
        }
    }

    public static void setCupMode(boolean cupMode) {
        Cup.cupMode = cupMode;
    }

    public static boolean isCupMode() {
        return cupMode;
    }

    public static Object getObjectLock() {
        return objectLock;
    }

    public static ArrayList<Player> getWinners() {
        return winners;
    }

}
