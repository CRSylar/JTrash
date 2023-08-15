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
        if (e.getSource().getClass().getSimpleName().equals("DeckPanel")) {
            System.out.println("cliccato sul deck");
            gameController.drawFromDeck(0);
        }
        else {
            System.out.println("cliccato sul pila scarti");
            gameController.drawFromDiscard(0);

        }
    }
}
