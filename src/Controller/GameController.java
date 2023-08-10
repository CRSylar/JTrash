package Controller;

import Model.ModelManager;
import View.GameManager;

public class GameController {

    private final ModelManager model;
    private final GameManager view;
    public GameController(int players) {
        // create the ModelManager
        model = new ModelManager(players);
        // create the ViewManager
        view = new GameManager(players);
        model.addObserver(view);
        view.setVisible(true);

    }

    public void start() {
        model.startGame();
    }
}
