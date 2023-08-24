package View.NotifyHandlers;

import View.Card;
import View.NotifiyHandler;
import View.Sounds;

import javax.swing.*;

/**
 * Handler dell'evento FillHand gestito
 * con lo strategy Pattern.
 * Gestisce l'animazione dello scarto
 */
public class FillHandHandler implements NotifiyHandler {

    /**
     * L'id del giocatore
     */
    int id;
    /**
     * Il Pannello del giocatore
     */
    JPanel playerPanel;
    /**
     * Il numero dei giocatori
     */
    int numberOfPlayers;

    /**
     * Costruisce un handler
     * @param id del giocatore
     * @param playerPanel pannello del giocatore
     * @param numberOfPlayers numero di giocatori
     */
    public FillHandHandler(int id, JPanel playerPanel, int numberOfPlayers) {
        this.id = id;
        this.playerPanel = playerPanel;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Aggiunge la carta al pannello e riproduce il suono della carta flippata
     */
    @Override
    public void handle() {
        boolean rotated = numberOfPlayers > 2 && (id == 1 || id == 3);
        playerPanel.setVisible(false);
        playerPanel.add(new Card(rotated));
        playerPanel.setVisible(true);
        Sounds.getInstance().play("assets/sounds/flipcard.wav", false);
    }
}
