package Controller;

import Model.Profile;
import Utilities.FileWriterReader;
import Utilities.GameResult;
import Utilities.Utils;
import View.Sounds;
import View.StartingScreen;

import java.io.IOException;

public class StartingScreenController {

    StartingScreen startingScreen;
    public StartingScreenController(StartingScreen startingScreen) {
        this(startingScreen,null);
    }
    public StartingScreenController(StartingScreen startingScreen, GameResult lastGame) {
        if (lastGame != null)
            updatePlayerScore(lastGame.getResult());
        this.startingScreen = startingScreen;

        if (!Profile.isProfileLoaded()) {
           String name = this.startingScreen.showProfileCreationDialog();
           Profile.createNewProfile(name);
           Utils.save();
        }

        initListeners();
        Sounds.getInstance().stop();
    }

    private void updatePlayerScore(GameResult.RESULT result) {
        Profile.getProfile().updateProfile(result);
        Utils.save();
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
        Sounds.getInstance().play("assets/sounds/ambient.wav", true);
        gm.start();
        // dispose chiude la schermo attuale, lasciando attivo quello creato
        // da GameController
        startingScreen.dispose();
    }

    private void showProfile() {
        System.out.println(Profile.getProfile());
    }
}
