package View;

import javax.swing.*;
import java.awt.*;

public class StartingScreen  extends JFrame {

    JButton profile,exit, twoPlayers, threePlayers, fourPlayers;
    JWindow win = new JWindow();

    public StartingScreen() {
        this(false);
    }
    public StartingScreen(boolean backToMenu) {
        if (!backToMenu)
            displaySplashScreen(win);
        displayMenuScreen();
        setVisible(true);
        win.setVisible(false);
        win.dispose();
    }

    private void displayMenuScreen() {
        setTitle("JTrash Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        MenuPanel contentPanel = new MenuPanel();

        Dimension buttonDimension = new Dimension(200, 50);

        GridBagConstraints buttonsGbs = new GridBagConstraints();
        buttonsGbs.gridx = 0;
        buttonsGbs.gridy = 1;
        buttonsGbs.gridwidth = 3;
        buttonsGbs.insets = new Insets(5, 5, 5, 5);
        twoPlayers = new JButton("Start game vs CPU");
        twoPlayers.setPreferredSize(buttonDimension);
        contentPanel.add(twoPlayers, buttonsGbs);

        threePlayers = new JButton("Start a 3 Players Game");
        threePlayers.setPreferredSize(buttonDimension);
        buttonsGbs.gridx = 5;
        contentPanel.add(threePlayers, buttonsGbs);

        fourPlayers = new JButton("Start a 4 Players Game");
        fourPlayers.setPreferredSize(buttonDimension);
        buttonsGbs.gridx = 10;
        contentPanel.add(fourPlayers, buttonsGbs);

        profile = new JButton("Profile");
        profile.setPreferredSize(buttonDimension);
        buttonsGbs.gridy = 5;
        buttonsGbs.gridx = 5;
        contentPanel.add(profile, buttonsGbs);

        exit = new JButton("Exit and Close the Game");
        exit.setPreferredSize(buttonDimension);
        buttonsGbs.gridy = 10;
        contentPanel.add(exit, buttonsGbs);

        add(contentPanel);
    }

    private void displaySplashScreen(JWindow win) {
        win.setSize(800,600);
        win.setLocationRelativeTo(null);
        try {
            ImageIcon img = new ImageIcon("assets/splashScreen.gif");
            JLabel splashImage = new JLabel();
            splashImage.setIcon(img);
            win.getContentPane().add(splashImage);
            win.setVisible(true);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JButton getProfileButton () {return profile;}
    public JButton getExitButton () {return exit;}
    public JButton getTwoPlayersButton () {return twoPlayers;}
    public JButton getThreePlayersButton () {return threePlayers;}
    public JButton getFourPlayersButton () {return fourPlayers;}
}
