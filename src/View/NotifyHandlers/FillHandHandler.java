package View.NotifyHandlers;

import View.Card;
import View.NotifiyHandler;

import javax.swing.*;

public class FillHandHandler implements NotifiyHandler {

    int id;
    JPanel playerPanel;
    int numberOfPlayers;
    public FillHandHandler(int id, JPanel playerPanel, int numberOfPlayers) {
        this.id = id;
        this.playerPanel = playerPanel;
        this.numberOfPlayers = numberOfPlayers;
    }
    @Override
    public void handle() {
        boolean rotated = false;
        if (numberOfPlayers > 2 && (id == 1 || id == 3))
            rotated = true;
        playerPanel.setVisible(false);
        playerPanel.add(new Card(rotated));
        playerPanel.setVisible(true);
    }
}
