package View;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel che rappresenta la UI di una Carta
 */
public class Card extends JPanel {

    /**
     * Costruisce una Carta coperta, in verticale (false) o ruotata in orizzontale(true)
     * @param rotated l'orientamento della carta
     */
    public Card(boolean rotated) {
        JLabel cardUI;
        if (rotated)
            cardUI = new JLabel(new ImageIcon(AssetLoader.getInstance()
                    .getRotatedCard(AssetLoader.getInstance().getCardBack())));
        else
            cardUI = new JLabel(new ImageIcon(AssetLoader.getInstance().getCardBack()));

        setBackground(new Color(0,102,0));
        add(cardUI);
    }

    /**
     * Costruisce una carta Scoperta, in verticale (false) o in orizzontale (true)
     * @param value il valore della carta (0-13)
     * @param suit il seme della carta
     * @param rotated l'orientamento della carta
     */
    public Card(int value, Model.Card.SUITS suit, boolean rotated) {
        setBackground(new Color(0,102,0));
        AssetLoader al = AssetLoader.getInstance();
        ImageIcon cardUI = new ImageIcon();
        if (rotated)
            cardUI.setImage(al.getRotatedCard(al.getCard(value, suit)));
        else
            cardUI.setImage(al.getCard(value, suit));
        add(new JLabel(cardUI));
    }
}
