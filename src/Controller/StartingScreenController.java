package Controller;

import Utilities.GameResult;
import View.Sounds;
import View.StartingScreen;

public class StartingScreenController {

    StartingScreen startingScreen;
    public StartingScreenController(StartingScreen startingScreen) {
        this(startingScreen,null);
    }
    public StartingScreenController(StartingScreen startingScreen, GameResult lastGame) {
        if (lastGame != null)
            updatePlayerScore(lastGame.hasHumanWon(), lastGame.isDraw());
        this.startingScreen = startingScreen;
        initListeners();
        Sounds.getInstance().stop();
    }

    private void updatePlayerScore(boolean humanWon, boolean draw) {
        // TODO - Aggiornare qui lo score del player
        // humanWon = true -> playedGames +1, win + 1, exp +50
        // humanWon = false -> playedGames +1, lose + 1, exp +0
        // draw = true -> playedGames +1, exp +25
        //
        /*
        * curva dei livelli puo essere tipo
        * lv 0 -> 1 : un game giocato
        * lv 1 -> 2 : 100xp
        * lv 2 -> 3 : 300xp
        * lv 3 -> 4 : 600xp
        * lv 4 -> 5 : 1000xp + ( rapporto w/l > 1)
        *
        * */
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
        System.out.println("Profile");
    }
}
