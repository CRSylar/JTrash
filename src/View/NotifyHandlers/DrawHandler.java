package View.NotifyHandlers;

import View.AssetLoader;
import View.NotifiyHandler;
import View.Sounds;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;

public class DrawHandler implements NotifiyHandler {

    Model.Card c;
    JPanel drawnCardPanel;
    public DrawHandler(Model.Card c, JPanel drawnCardPanel) {
        this.c = c;
        this.drawnCardPanel = drawnCardPanel;
    }

    @Override
    public void handle() {
        Image img = new ImageIcon(AssetLoader.getInstance().getCard(c.getValue(), c.getSuit()))
                .getImage().getScaledInstance(144,192, Image.SCALE_SMOOTH);
        drawnCardPanel.add(
                new JLabel(
                        new ImageIcon(img)
                )
        );
        Sounds.getInstance().play("assets/sounds/flipcard.wav", false);
        drawnCardPanel.setVisible(true);
    }
}
