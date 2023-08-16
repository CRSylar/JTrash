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

    public Card(int value, SUITS suit, boolean rotated) {
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
