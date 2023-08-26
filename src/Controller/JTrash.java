package Controller;

import Utilities.Utils;
import View.StartingScreen;

import java.io.File;
import java.io.IOException;

/**
 * Classe che ospita il main, come da specifiche di progetto
 */
public class JTrash {
    /**
     * Classe Main del programma, se non esiste crea il file che ospiterà i dati salvati
     * carica eventualmente un profilo (se esiste ed è valido)
     * e costruisce il controller che mostra la finestra principale (menu) e la splash screen
     * @param args Questo programma non prevede argomenti per il momento
     * @throws IOException Lancia un'eccezione se non puo creare/scrivere o leggere sul file
     * in quanto è necessario per il corretto funzionamento del programma, in caso di errori verificare che
     * chi esegue il gioco ha i permessi necessari per creare/scrivere e leggere i file della directory dove il codice
     * sorgente risiede
     */
    public static void main(String[] args) throws IOException {
        File saved = new File("savedGames.dat");
        saved.createNewFile();
        Utils.readFileAndLoadProfile();
        StartingScreen start = new StartingScreen();
        MainMenuController startingScreenController = new MainMenuController(start);
    }


}