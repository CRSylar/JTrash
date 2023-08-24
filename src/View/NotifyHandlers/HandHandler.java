package View.NotifyHandlers;

import Model.Hand;
import Utilities.Pair;
import View.Card;
import View.NotifiyHandler;

import javax.swing.*;

/**
 * Handler dell'evento HAND gestito
 * con lo strategy Pattern.
 * Gestisce l'animazione di una carta inserita tra quelle
 * in mano al giocatore (scoperta)
 */
public class HandHandler implements NotifiyHandler {

    /**
     * Il giocatore che sta ricevendo una carta in mano
     */
    int playerId;
    /**
     * Il numero dei giocatori in questa partita
     */
    int numberOfPlayers;
    /**
     * La mano del giocatore
     */
    Model.Hand hand;
    /**
     * Il pannello (View) relativo al giocatore
     */
    JPanel playerPanel;

    /**
     * Costruisce un Handler, prende una Coppia formata da PlayerId e PlayerHand
     * @param pair la coppia
     * @param playerPanel il JPanel relativo al giocatore
     * @param numberOfPlayers numero di giocatori della partita in corso
     */
    public HandHandler(Pair<Integer, Hand> pair, JPanel playerPanel, int numberOfPlayers) {
        this.playerId = pair.getLeft();
        this.hand = pair.getRight();
        this.playerPanel = playerPanel;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Aggiunge la carta (scoperta) al pannello del giocatore,
     * controllando se la carta in questione va "ruotata"
     */
    @Override
    public void handle() {
        boolean rotated = numberOfPlayers > 2 && (playerId == 1 || playerId == 3);
        playerPanel.setVisible(false);
        playerPanel.removeAll();
        for (int i=0; i< hand.getHandSize(); i++) {
            if (hand.getCard(i).isHide())
                playerPanel.add(new Card(rotated));
            else
                playerPanel.add(new Card(
                        hand.getCard(i).getValue(),
                        hand.getCard(i).getSuit(),
                        rotated));
        }
        playerPanel.setVisible(true);
    }
}
