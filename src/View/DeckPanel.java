package View;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel che rappresenta il Mazzo di carte, è composto da
 * due Label sovrapposte, la prima mostra un immagine di "Mazzo vuoto"
 * mentre la seconda il mazzo vero e proprio.
 *
 */
public class DeckPanel extends JPanel {
    /**
     * Costruisce il Pannello che ospiterà il mazzo di carte
     * impostandone le dimensioni e il layoutManager,
     * dopo di che aggiunge le Icone del mazzo e della pila vuota
     * al componente
     */
    public DeckPanel() {
        setPreferredSize(new Dimension(78, 114));
        setLayout(new CardLayout());
        JLabel deckEmptyLabel = new JLabel();
        deckEmptyLabel.setIcon(new ImageIcon(AssetLoader.getInstance().getEmptyPile()));
        JLabel deckBackLabel = new JLabel();
        deckBackLabel.setIcon(new ImageIcon(AssetLoader.getInstance().getCardBack()));
        add(deckEmptyLabel, "Empty");
        add(deckBackLabel, "CardBack");
        // Flipping per mostrare la carta piu "in alto" nello stack
        CardLayout cl = (CardLayout)getLayout();
        cl.last(this);
        setBackground(Color.WHITE);
    }
}
