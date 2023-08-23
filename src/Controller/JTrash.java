package Controller;

import Utilities.Utils;
import View.StartingScreen;

import java.io.File;
import java.io.IOException;

/**
 * Classe che ospita il main, come da specifiche di progetto
 */
public class JTrash {
    public static void main(String[] args) throws IOException {
        File saved = new File("savedGames.dat");
        saved.createNewFile();
        Utils.readFileAndLoadProfile();
        StartingScreen start = new StartingScreen();
        MainMenuController startingScreenController = new MainMenuController(start);
    }


}