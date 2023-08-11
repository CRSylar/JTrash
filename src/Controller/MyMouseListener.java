package Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {

    private final GameController gameController;
    public MyMouseListener(GameController gm) {
        this.gameController = gm;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().getClass().getSimpleName().equals("DeckPanel"))
            gameController.drawFromDeck();
        else
            gameController.drawFromDiscard();
    }
}
