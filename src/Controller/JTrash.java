package Controller;

import View.StartingScreen;

import java.io.File;
import java.io.IOException;

public class JTrash {
    public static void main(String[] args) throws IOException {
        File saved = new File("savedGames.dat");
        saved.createNewFile();
        StartingScreen start = new StartingScreen();
        StartingScreenController startingScreenController = new StartingScreenController(start);
    }
}