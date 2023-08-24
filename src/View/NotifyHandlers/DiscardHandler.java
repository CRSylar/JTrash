package View.NotifyHandlers;

import View.AssetLoader;
import View.NotifiyHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Handler dell'evento DISCARD gestito
 * con lo strategy Pattern.
 * Gestisce l'animazione dello scarto
 */
public class DiscardHandler implements NotifiyHandler {

    /**
     * La carta da scartare
     */
    Model.Card c;
    /**
     * Il pannello degli scarti dove aggiungere la carta
     * da scartare
     */
    JPanel discardPanel;

    /**
     * Costruisce un handler
     * @param c la carta da scartare
     * @param discardPanel il pannello su cui visualizzare l'animazione
     */
    public DiscardHandler(Model.Card c, JPanel discardPanel) {
        this.c = c;
        this.discardPanel = discardPanel;
    }

    /**
     * Aggiunge la carta da scartare al pannello degli scarti
     */
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
