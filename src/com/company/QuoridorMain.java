package com.company;

/*
The Clifton Labs' JSON.simple library for Java provides functionality for handling JSON data, including reading,
 writing, parsing, and querying. To download the library from Maven, follow these steps:

1. Open your project in IntelliJ IDEA or a similar IDE.
2. Navigate to the main menu and select "File" and then "Project Structure" (you can use the shortcut Ctrl+Alt+Shift+S).
3. In the Project Structure dialog, click on "Libraries" on the left-hand side.
4. Click on the "+" icon to add a new library.
5. Select "From Maven" from the dropdown menu.
6. In the search field, enter "com.github.cliftonlabs:json-simple:3.1.0" as the library artifact.
7. Click on the "Search" button.
8. Once the library is found, click on the "OK" button to download and add it to your project.

By following these steps, you will successfully download the JSON.simple library and be able to
utilize its features for working with JSON data in your Java project.
*/

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class QuoridorMain {
    public static Container container;
    public static JFrame gameFrame, startFrame;

    public static void startPage() {
        hideGameFrames();
        createStartFrame();
        setupStartPanel();
        setupNewGameButton();
        setupLoadGameButton();
        setupCupButton();
        showStartFrame();
    }

    private static void createStartFrame() {
        //create start page frame
        startFrame = new JFrame();
        startFrame.setTitle("Main Menu");
        startFrame.setSize(620, 500);
        startFrame.setFocusable(true);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLayout(null);
        startFrame.setResizable(false);
    }

    private static void hideGameFrames() {
        if (gameFrame != null) {
            gameFrame.setVisible(false);
        }
        if (Cup.playerFrame != null) {
            Cup.playerFrame.setVisible(false);
        }
        if (Cup.playFrame != null) {
            Cup.playFrame.setVisible(false);
        }
    }

    private static void setupStartPanel() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3, 1, 0, 5));
        startFrame.setContentPane(startPanel);
        container = startFrame.getContentPane();
    }

    private static RoundedButton createButton(String text, int fontSize, int borderRadius) {
        RoundedButton button = new RoundedButton(text, borderRadius);

        // Define the gradient colors
        Color startColor = new Color(174, 75, 255);
        Color endColor = new Color(103, 36, 193);

        // Create a gradient background with rounded corners
        button.setBackground(startColor);
        button.setGradient(startColor, endColor);

        // Add a subtle shadow effect
        button.setShadow(true);
        button.setShadowColor(Color.GRAY);
        button.setShadowOpacity(0.5f);
        button.setShadowOffsetX(2);
        button.setShadowOffsetY(2);

        // Set font size and style
        button.setFont(new Font("Arial", Font.BOLD, fontSize));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(endColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(startColor);
            }
        });

        return button;
    }

    private static void setupNewGameButton() {
        JButton newGameButton = createButton("New Game", 50, 30);
        JPanel newGameButtonPanel = createButtonPanel(newGameButton);
        newGameButton.addActionListener(e -> game());
        container.add(newGameButtonPanel);
    }

    private static void setupLoadGameButton() {
        JButton loadGameButton = createButton("Load Game", 40, 30);
        JPanel loadGameButtonPanel = createButtonPanel(loadGameButton);
        loadGameButton.addActionListener(e -> gameload());
        container.add(loadGameButtonPanel);
    }

    private static void setupCupButton() {
        JButton cupButton = createButton("Cup", 40, 30);
        JPanel cupButtonPanel = createButtonPanel(cupButton);
        cupButton.addActionListener(e -> Cup.CupChamp());
        container.add(cupButtonPanel);
    }

    private static void showStartFrame() {
        startFrame.setVisible(true);
    }

    private static JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
        );
        return buttonPanel;
    }

    public static void main(String[] args) {
        startPage();
    }

    public static void game() {
        startFrame.setVisible(false);
        gameFrame = new JFrame();
        gameFrame.setTitle("Quoridor Game");
        gameFrame.setSize(1130, 673);
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameFrame.setResizable(false);

        int numPlayers;
        final ArrayList<Player> players = new ArrayList<>();

        //create players
        Player p1 = new Player(4, 8, Player.UP, "Player 1", 10, Color.RED);
        players.add(p1);
        Player p2 = new Player(4, 0, Player.DOWN, "Player 2", 10, Color.BLUE);
        players.add(p2);
        Player p3 = new Player(0, 4, Player.RIGHT, "Player 3", 5, Color.GREEN);
        Player p4 = new Player(8, 4, Player.LEFT, "Player 4", 5, Color.YELLOW);

        numPlayers = validateNumberOfPlayers();

        if (numPlayers == 4) {
            p1.setNumWalls(5);
            p2.setNumWalls(5);
            players.add(p3);
            players.add(p4);
        }

        validatePlayerNames(players);

        //crate new board and side
        final Board board = new Board(players);
        final SidePanel side = new SidePanel(board, players);

        board.add(side);

        gameFrame.add(board, BorderLayout.CENTER);
        gameFrame.add(side, BorderLayout.EAST);

        gameFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                JFrame exitframe = new JFrame("Exit");

                JButton savebutton = new JButton("Save");
                JButton continuebutton = new JButton("Continue");
                JButton dontsavebutton = new JButton("Don't save & Exit");

                JPanel exitpanel = new JPanel();

                exitframe.add(exitpanel);
                exitpanel.add(continuebutton);
                exitpanel.add(savebutton);
                exitpanel.add(dontsavebutton);
                exitframe.setSize(350, 100);

                exitframe.setVisible(true);
                dontsavebutton.addActionListener(e1 -> System.exit(-1));

                continuebutton.addActionListener(e12 -> exitframe.setVisible(false));

                savebutton.addActionListener(e13 -> {
                    side.saveGame();
                    showMessageDialog("Successfully saved");
                    startPage();
                    exitframe.setVisible(false);
                    gameFrame.setVisible(false);
                });
                exitframe.setLocationRelativeTo(null);

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        gameFrame.setVisible(true);

    }

    private static void validatePlayerNames(ArrayList<Player> players) {
        ArrayList<String> playerNames = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            boolean isValidName = false;

            while (!isValidName) {
                String playerName = getPlayerName(i + 1);

                if (playerName.matches("^\\d.*")) {
                    showErrorDialog("The player's name must not contain just numbers or start with a number", "Error");
                } else if (playerNames.contains(playerName)) {
                    showErrorDialog("Each player should have a different name", "Error");
                } else if (playerName.trim().isEmpty()) {
                    showErrorDialog("Player's name must not be empty!", "Error");
                } else {
                    players.get(i).setName(playerName);
                    playerNames.add(playerName);
                    isValidName = true;
                }
            }
        }
    }

    private static int validateNumberOfPlayers() {
        int numPlayers;

        while (true) {
            try {
                numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter number of players (2/4)"));
                if (numPlayers == 4 || numPlayers == 2) {
                    break;
                } else {
                    showErrorDialog("The game must be 2 or 4 players", "Error");
                }
            } catch (NumberFormatException e) {
                showErrorDialog("Invalid input!", "Error");
            }
        }

        return numPlayers;
    }

    public static void gameload() {
        //set array list to extraction the data from json
        ArrayList<String> player1 = new ArrayList<>();
        ArrayList<String> player2 = new ArrayList<>();
        ArrayList<String> player3 = new ArrayList<>();
        ArrayList<String> player4 = new ArrayList<>();
        ArrayList<String> wallList = new ArrayList<>();
        boolean checkEmpty = false;
        BigDecimal playerTurn = null;

        //load JSON
        try {
            Reader reader = Files.newBufferedReader(Paths.get("saveplayer.json"));
            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

            playerTurn = (BigDecimal) parser.get("playerTurn");

            //get information of the players
            Map<Object, Object> p1 = (Map<Object, Object>) parser.get("player1");
            p1.forEach((key, value) -> player1.add(value + ""));

            Map<Object, Object> p2 = (Map<Object, Object>) parser.get("player2");
            p2.forEach((key, value) -> player2.add(value + ""));

            try {
                Map<Object, Object> p3 = (Map<Object, Object>) parser.get("player3");
                p3.forEach((key, value) -> player3.add(value + ""));

                Map<Object, Object> p4 = (Map<Object, Object>) parser.get("player4");
                p4.forEach((key, value) -> player4.add(value + ""));
            } catch (NullPointerException e) {
                player3.clear();
                player4.clear();
            }

            //get coordinates of walls
            JsonArray walls = (JsonArray) parser.get("walls");
            walls.forEach(x -> wallList.add((String) x));

            reader.close();
        } catch (JsonException ex) {
            showErrorDialog("There is no data in saved file. Please save a new game.", "Empty saved file");
            checkEmpty = true;
        } catch (IOException e2) {
            showErrorDialog("There is no saved file. Please save a new game.", "No Such File");
            checkEmpty = true;
        }

        if (!checkEmpty) {
            startFrame.setVisible(false);
            gameFrame = new JFrame();
            gameFrame.setTitle("Quoridor Game");
            gameFrame.setSize(1130, 673);
            gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            gameFrame.setResizable(false);

            final ArrayList<Player> players = new ArrayList<>();

            //create players from the array list
            Player p1 = new Player(Integer.parseInt(player1.get(2)), Integer.parseInt(player1.get(3)), Player.UP, player1.get(1), Integer.parseInt(player1.get(0)), Color.RED);
            players.add(p1);
            Player p2 = new Player(Integer.parseInt(player2.get(2)), Integer.parseInt(player2.get(3)), Player.DOWN, player2.get(1), Integer.parseInt(player2.get(0)), Color.BLUE);
            players.add(p2);
            if (player3.size() > 0 && player4.size() > 0) {
                Player p3 = new Player(Integer.parseInt(player3.get(2)), Integer.parseInt(player3.get(3)), Player.RIGHT, player3.get(1), Integer.parseInt(player3.get(0)), Color.GREEN);
                players.add(p3);
                Player p4 = new Player(Integer.parseInt(player4.get(2)), Integer.parseInt(player4.get(3)), Player.LEFT, player4.get(1), Integer.parseInt(player4.get(0)), Color.YELLOW);
                players.add(p4);
            }
            //set new board and side panel
            final Board board = new Board(players);
            final SidePanel side = new SidePanel(board, players);

            board.add(side);

            //set walls on the board with coordinates
            for (String s : wallList) {
                String[] splitCoordinates = s.split("\\s+");

                if (players.get(0).getNumWalls() == 0) {
                    players.get(0).setNumWalls(1);
                    players.get(0).placeWall(new Wall(Integer.parseInt(splitCoordinates[0]), Integer.parseInt(splitCoordinates[1]),
                            Integer.parseInt(splitCoordinates[2]), Integer.parseInt(splitCoordinates[3])), board);
                } else {
                    players.get(0).placeWall(new Wall(Integer.parseInt(splitCoordinates[0]), Integer.parseInt(splitCoordinates[1]),
                            Integer.parseInt(splitCoordinates[2]), Integer.parseInt(splitCoordinates[3])), board);
                    players.get(0).setNumWalls(players.get(0).getNumWalls() + 1);
                }
            }
            //set the player's playerTurn
            side.setPlayerTurn(Integer.parseInt(String.valueOf(playerTurn)));

            //add board and side to game frame
            gameFrame.add(board, BorderLayout.CENTER);
            gameFrame.add(side, BorderLayout.EAST);

            //Actions for exiting window
            gameFrame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    JFrame exitframe = new JFrame("Exit");
                    JButton savebutton = new JButton("Save");
                    savebutton.setFocusPainted(false);

                    JButton dontsavebutton = new JButton("Don't save & Exit");
                    dontsavebutton.setFocusPainted(false);

                    JButton continuebutton = new JButton("Continue");
                    continuebutton.setFocusPainted(false);

                    JPanel exitpanel = new JPanel();

                    exitframe.add(exitpanel);
                    exitpanel.add(continuebutton);
                    exitpanel.add(savebutton);
                    exitpanel.add(dontsavebutton);
                    exitframe.setSize(350, 100);

                    exitframe.setVisible(true);
                    dontsavebutton.addActionListener(e1 -> System.exit(-1));

                    continuebutton.addActionListener(e12 -> exitframe.setVisible(false));

                    savebutton.addActionListener(e13 -> {
                        new Thread();
                        side.saveGame();
                        showMessageDialog("Successfully saved");
                        startPage();
                        exitframe.setVisible(false);
                        gameFrame.setVisible(false);
                    });

                    exitframe.setLocationRelativeTo(null);

                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });

            gameFrame.setVisible(true);
        }
    }

    private static void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private static String getPlayerName(int playerNumber) {
        return JOptionPane.showInputDialog(
                null, "Enter Player " + playerNumber + "'s Name", "Player " + playerNumber,
                JOptionPane.INFORMATION_MESSAGE);
    }
}

