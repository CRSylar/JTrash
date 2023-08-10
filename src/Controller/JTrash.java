package Controller;

import View.StartingScreen;

public class JTrash {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        StartingScreen start = new StartingScreen();
        StartingScreenController startingScreenController = new StartingScreenController(start);
    }
}