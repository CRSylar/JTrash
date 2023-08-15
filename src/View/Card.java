package View;

import Model.SUITS;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

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

    public Card(int value, SUITS suit) {
        setBackground(new Color(0,102,0));
        add(new JLabel(new ImageIcon(AssetLoader.getInstance().getCard(value, suit))));
    }
}
