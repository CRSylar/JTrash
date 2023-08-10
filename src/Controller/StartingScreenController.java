package Controller;

import View.StartingScreen;

public class StartingScreenController {

    StartingScreen startingScreen;
    public StartingScreenController(StartingScreen startingScreen) {
        this.startingScreen = startingScreen;
        initListeners();
    }

    private void initListeners() {
        startingScreen.getExitButton().addActionListener(e -> System.exit(0));
        startingScreen.getTwoPlayersButton().addActionListener(e -> startGame(2));
        startingScreen.getThreePlayersButton().addActionListener(e -> startGame(3));
        startingScreen.getFourPlayersButton().addActionListener(e -> startGame(4));
        startingScreen.getProfileButton().addActionListener(e -> showProfile());

    }

    private void startGame(int players) {
        System.out.println("Starting with "+players);
        // inserire qui nuova Schermata
        GameController gm = new GameController(players);
        gm.start();
        // dispose chiude la schermo attuale, lasciando attivo quello creato
        // da GameController
        startingScreen.dispose();
    }

    private void showProfile() {
        System.out.println("Profile");
    }
}
