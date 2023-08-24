package View.NotifyHandlers;

import View.AssetLoader;
import View.NotifiyHandler;
import View.Sounds;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
/**
 * Handler dell'evento DRAW gestito
 * con lo strategy Pattern.
 * Gestisce l'animazione della pescata
 */
public class DrawHandler implements NotifiyHandler {

    /**
     * La carta pescata
     */
    Model.Card c;
    /**
     * Il pannello della pescata dove aggiungere la carta
     * da scartare
     */
    JPanel drawnCardPanel;
    /**
     * Costruisce un handler
     * @param c la carta da scartare
     * @param drawnCardPanel il pannello su cui visualizzare l'animazione
     */
    public DrawHandler(Model.Card c, JPanel drawnCardPanel) {
        this.c = c;
        this.drawnCardPanel = drawnCardPanel;
    }

    /**
     * Aggiunge la carta pescata al pannello relativo
     * e riproduce il suono di una carta pescata
     */
    @Override
    public void handle() {
        Image img = new ImageIcon(AssetLoader.getInstance().getCard(c.getValue(), c.getSuit()))
                .getImage().getScaledInstance(144,192, Image.SCALE_SMOOTH);
        drawnCardPanel.add(
                new JLabel(
                        new ImageIcon(img)
                )
        );
        Sounds.getInstance().play(Sounds.CLIPTYPE.DRAW, false);
        drawnCardPanel.setVisible(true);
    }
}
