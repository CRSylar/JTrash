package View;

import javax.swing.*;
import java.awt.*;

public class DeckPanel extends JPanel {
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
        //
        setBackground(Color.WHITE);
    }
}
