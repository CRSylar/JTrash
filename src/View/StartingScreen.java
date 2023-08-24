package View;

import javax.swing.*;
import java.awt.*;

/**
 * Frame iniziale della UI, lancia l'animazione iniziale,
 * contiene il MenuPanel
 */
public class StartingScreen  extends JFrame {

    /**
     * Bottoni per UI del menu principale
     */
    JButton profile,exit, twoPlayers, threePlayers, fourPlayers;
    /**
     * JWindow utilizzata per gestire la splash screen iniziale
     */
    JWindow win = new JWindow();

    /**
     * Costruisce un Frame saltando l'animazione iniziale
     * o splash screen
     */
    public StartingScreen() {
        this(false);
    }

    /**
     * Costruisce un Frame, eventualmente mostrando prima
     * la splash screen iniziale
     * @param backToMenu flag che attiva o disattiva il lancio della splash screen
     */
    public StartingScreen(boolean backToMenu) {
        if (!backToMenu)
            displaySplashScreen(win);

        displayMenuScreen();
        setVisible(true);
        win.setVisible(false);
        win.dispose();

    }

    /**
     * Mostra una finestra di dialogo che obbliga il giocatore a creare un profilo
     * inserendo un nome valido (qualsiasi sequenza di caratteri piu lunga di 3)
     * @return il nome da utilizzare per la creazione del profilo
     */
    public String showProfileCreationDialog() {
        String name = "";
        while (name.length() < 3) {
        name = (String) JOptionPane.showInputDialog(
                this,
                "Create a new Profile, please use at least 3 characters",
                "Profile Creation",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                "");
        if (name == null)
            name = "";
        }
        return name;
    }

    /**
     * Istanzia e fa il setup del MenuPanel
     * aggiungendo i bottoni per la UX.
     * Crea un frame 800x600 con titolo JTrash Menu
     * al centro dello schermo
     */
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

    /**
     * Mostra per 5secondi una splash screen animata.
     * Dimensione della splash screen 800x600
     * al centro dello schermo
     * @param win da utilizzare
     */
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
        } catch (InterruptedException e) {
            System.out.println("Can't make the splash screen sit for 5 second, interrupted");
            e.printStackTrace();
        }
    }

    /**
     *
     * @return bottone del Profilo
     */
    public JButton getProfileButton () {return profile;}

    /**
     *
     * @return bottone Exit
     */
    public JButton getExitButton () {return exit;}
    /**
     *
     * @return il bottone che fa iniziare una partita a due giocatori
     */
    public JButton getTwoPlayersButton () {return twoPlayers;}
    /**
     *
     * @return il bottone che fa iniziare una partita a tre giocatori
     */
    public JButton getThreePlayersButton () {return threePlayers;}
    /**
     *
     * @return il bottone che fa iniziare una partita a quattro giocatori
     */
    public JButton getFourPlayersButton () {return fourPlayers;}
}
