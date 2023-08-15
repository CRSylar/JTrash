package View.NotifyHandlers;

import View.AssetLoader;
import View.NotifiyHandler;

import javax.swing.*;
import java.awt.*;

public class DiscardHandler implements NotifiyHandler {

    Model.Card c;
    JPanel discardPanel;
    public DiscardHandler(Model.Card c, JPanel discardPanel) {
        this.c = c;
        this.discardPanel = discardPanel;
    }

    @Override
    public void handle() {

        Image img = new ImageIcon(AssetLoader.getInstance().getCard(c.getValue(), c.getSuit()))
                .getImage();
        discardPanel.add(
                new JLabel(
                        new ImageIcon(img)
                )
        );
    }
}
