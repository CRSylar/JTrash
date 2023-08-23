package Controller;

import Model.Profile;
import Utilities.GameResult;
import Utilities.Utils;
import View.*;

/**
 * Controller che gestisce il menu principale
 * necessita di una istanza di StartingScreen (la View da controllare)
 * Performa operazioni di salvataggio dati su file (profilo),
 * imposta i listener per i Bottoni
 */
public class MainMenuController {

    /**
     * L'istanza della View da utilizzare
     */
    StartingScreen startingScreen;

    /**
     * Costruisce una nuova finestra del menu
     * @param startingScreen la View da utilizzare
     */
    public MainMenuController(StartingScreen startingScreen) {
        this(startingScreen,null);
    }

    /**
     * Costruisce una nuova finestra del Menu, e aggiorna il profilo utente (nel Model)
     * con il risultato del game appena finito
     * @param startingScreen la View da utilizzare
     * @param lastGame il risultato della partita
     */
    public MainMenuController(StartingScreen startingScreen, GameResult lastGame) {
        if (lastGame != null)
            updatePlayerScore(lastGame.getResult());
        this.startingScreen = startingScreen;

        if (Profile.isProfileLoaded()) {
           String name = this.startingScreen.showProfileCreationDialog();
           Profile.createNewProfile(name);
           Utils.save();
        }

        initListeners();
        Sounds.getInstance().stop();
    }

    /**
     * Metodo privato che aggiorna l'istanza del profilo nel Model e la salva su file
     * @param result il risultato della partita da utilizzare per aggiornare il profilo
     *               puo essere GameResult.RESULT.WIN -
     *                          GameResult.RESULT.LOSS -
     *                          GameResult.RESULT.DRAW
     */
    private void updatePlayerScore(GameResult.RESULT result) {
        Profile.getProfile().updateProfile(result);
        Utils.save();
    }

    /**
     * Metodo privato che inizializza i listener dei bottoni e registra le callback
     */
    private void initListeners() {
        startingScreen.getExitButton().addActionListener(e -> System.exit(0));
        startingScreen.getTwoPlayersButton().addActionListener(e -> startGame(2));
        startingScreen.getThreePlayersButton().addActionListener(e -> startGame(3));
        startingScreen.getFourPlayersButton().addActionListener(e -> startGame(4));
        startingScreen.getProfileButton().addActionListener(e -> showProfile());

    }

    /**
     * Metodo privato chiamato dalle callBack degli actionListener settati in precedenza,
     * alloca una nuova istanza di GameController (che gestisce tutta la partita),
     * fa partire la musica di sottofondo,
     * da il via alla partita chiamando il metodo gameController.start()
     * e libera le risorse utilizzate dalla View di questa finestra con dispose()
     * @param players il numero di giocatori che partecipano alla partita
     */
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

    /**
     * Metodo privato invocato quando l'utente clicca sul bottone "Profilo"
     * allora una nuova istanza di ProfileMenuController (che gestisce la finestra del profilo)
     * e libera le risorse utilizzate dalla View di questa finestra con dispose()
     */
    private void showProfile() {
        System.out.println(Profile.getProfile());
        ProfileMenuController pmc = new ProfileMenuController(Profile.getProfile());

        startingScreen.dispose();
    }
}
