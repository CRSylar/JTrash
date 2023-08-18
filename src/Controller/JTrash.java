package Controller;

import Model.Profile;
import Utilities.FileWriterReader;
import Utilities.Utils;
import View.StartingScreen;

import java.io.File;
import java.io.IOException;

public class JTrash {
    public static void main(String[] args) throws IOException {
        File saved = new File("savedGames.dat");
        saved.createNewFile();
        Utils.readFileAndLoadProfile();
        StartingScreen start = new StartingScreen();
        StartingScreenController startingScreenController = new StartingScreenController(start);
    }


}