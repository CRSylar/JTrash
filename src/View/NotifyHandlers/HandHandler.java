package View.NotifyHandlers;

import Model.Hand;
import Utilities.Pair;
import View.Card;
import View.NotifiyHandler;

import javax.swing.*;

public class HandHandler implements NotifiyHandler {

    int playerId;
    int numberOfPlayers;
    Model.Hand hand;
    JPanel playerPanel;
    public HandHandler(Pair<Integer, Hand> pair, JPanel playerPanel, int numberOfPlayers) {
        this.playerId = pair.getLeft();
        this.hand = pair.getRight();
        this.playerPanel = playerPanel;
        this.numberOfPlayers = numberOfPlayers;
    }
    @Override
    public void handle() {
        boolean rotated = false;
        if (numberOfPlayers > 2 && (playerId == 1 || playerId == 3))
            rotated = true;
        playerPanel.setVisible(false);
        playerPanel.removeAll();
        for (int i=0; i< hand.getHandSize(); i++) {
            if (hand.getCard(i).isHide())
                playerPanel.add(new Card(rotated));
            else
                playerPanel.add(new Card(
                        hand.getCard(i).getValue(),
                        hand.getCard(i).getSuit()));
        }
        playerPanel.setVisible(true);
    }
}
