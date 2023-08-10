package View;

import Model.SUITS;

import javax.swing.*;
import java.awt.*;

public class Card extends JPanel {

    public Card(boolean rotated) {
        JLabel cardUI;
        setBackground(new Color(0,102,0));
        if (rotated)
            cardUI = new JLabel(new ImageIcon(AssetLoader.getInstance()
                    .getRotatedCard(AssetLoader.getInstance().getCardBack())));
        else
            cardUI = new JLabel(new ImageIcon(AssetLoader.getInstance().getCardBack()));

        add(cardUI);
    }

    public Card(int value, SUITS suit) {
        add(new JLabel(new ImageIcon(AssetLoader.getInstance().getCard(value, suit))));
    }
}
