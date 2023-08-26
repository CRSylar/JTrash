package Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Sottoclasse di MouseAdapter ridefinisce il
 * comportamento di alcune azioni
 * es. mouseClicked
 */
public class MyMouseListener extends MouseAdapter {

    /**
     * L'istanza del controller del quale chiamare i metodi
     * quando si registra l'evento sul mouse
     */
    private final GameController gameController;

    /**
     * Costruisce un MyMouseListener, ha bisogno di una istanza di GameController per
     * poter chiamare successivamente i suoi metodi
     * @param gm la classe GameController da utilizzare per la gestione degli eventi
     *           NB. NON una nuova classe, ma un riferimento a quella in uso
     */
    public MyMouseListener(GameController gm) {
        this.gameController = gm;
    }

    /**
     * Ridefinizione dell' handler, se è stato cliccato il Mazzo chiama il metodo drawFromDeck del GameController
     * se invece si clicca sulla pila degli scarti viene invocato drawFromDiscard
     * @param e the event to be processed
     */
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
