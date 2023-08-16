package View;

import Model.Hand;
import Model.Notification;
import Model.Player;
import Utilities.Pair;
import View.NotifyHandlers.DiscardHandler;
import View.NotifyHandlers.DrawHandler;
import View.NotifyHandlers.FillHandHandler;
import View.NotifyHandlers.HandHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Integer.valueOf;

@SuppressWarnings("deprecation")
public class GameManager extends JFrame implements Observer {

    private final JLayeredPane tablePanel;
    private final DeckPanel deckPanel;
    private final DiscardPanel discardPanel;

    private final JPanel drawnCardPanel;
    private final JPanel[] playerPanels;

    public GameManager(int numberOfPlayers) {
        setTitle("JTrash");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1280,720);
        setLocationRelativeTo(null);
        setLayout(null);

        // Initialize all the components
        tablePanel = new JLayeredPane();
        deckPanel = new DeckPanel();
        discardPanel = new DiscardPanel();
        playerPanels = new JPanel[numberOfPlayers];
        drawnCardPanel = new JPanel();

        // Table Panel initialization
        InitializeTable();
        InitializeDeckNDiscard();
        // UI Sud
        InitializeSouthPlayer();

        switch (numberOfPlayers) {
            case 2 -> initTwoPlayerGame();
            case 3 -> initTreePlayerGame();
            case 4 -> initFourPlayerGame();
        }

        // setting up the Drawn panel
        drawnCardPanel.setBackground(Color.white);
        drawnCardPanel.setBounds(350, 250,144,192);
        drawnCardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        drawnCardPanel.setVisible(false);
        tablePanel.add(drawnCardPanel, valueOf(5));

        add(tablePanel, BorderLayout.CENTER);
    }

    private void initTwoPlayerGame() {
        InitializeNorthPlayer(1);
    }
    private void initTreePlayerGame() {
        InitializeNorthPlayer(2);
        InitializeWestPlayer();
    }
    private void initFourPlayerGame() {
        InitializeWestPlayer();
        InitializeNorthPlayer(2);
        InitializeEastPlayer();
    }
    public JPanel getPlayerPanel(int idx) {return this.playerPanels[idx];}
    private void InitializeTable() {
        // Table Panel initialization
        tablePanel.setBounds(0,0,1280,700);
        tablePanel.setBackground(new Color(0,102,0));
        tablePanel.setOpaque(true);
    }

    private void InitializeDeckNDiscard() {
        discardPanel.setToolTipText("Click to draw the Top Card");
        discardPanel.setBounds(557, 285, 78, 114);
        tablePanel.add(discardPanel, valueOf(1));

        deckPanel.setToolTipText("Click to draw a Card");
        deckPanel.setBounds(645, 285, 78, 114);
        tablePanel.add(deckPanel, valueOf(1));
    }

    private JPanel createPlayerPanel(boolean rotated) {
        JPanel playerPanel;
        if (rotated)
            playerPanel = new JPanel(new GridLayout(5, 2));
        else
            playerPanel  = new JPanel(new GridLayout(2, 5));

        playerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        playerPanel.setBackground(new Color(0,102,0));

        return playerPanel;
    }

    private void InitializeSouthPlayer() {
        playerPanels[0] = createPlayerPanel(false);
        playerPanels[0].setBounds(420,430,440,250);
        this.tablePanel.add(playerPanels[0], valueOf(0));
    }
    private void InitializeNorthPlayer(int pos) {
        playerPanels[pos] = createPlayerPanel(false);
        playerPanels[pos].setBounds(420,10,440,250);
        this.tablePanel.add(playerPanels[pos], valueOf(0));
    }

    private void InitializeWestPlayer() {
        playerPanels[1] = createPlayerPanel(true);
        playerPanels[1].setBounds(25,150,250,440);
        this.tablePanel.add(playerPanels[1], valueOf(0));
    }

    private void InitializeEastPlayer() {
        playerPanels[3] = createPlayerPanel(true);
        playerPanels[3].setBounds(1000,150,250,440);
        this.tablePanel.add(playerPanels[3],  valueOf(0));
    }

    public DeckPanel getDeckPanel() {
        return deckPanel;
    }

    public DiscardPanel getDiscardPanel() {
        return discardPanel;
    }

    public JPanel getDrawnCardPanel() {
        return drawnCardPanel;
    }

    public void resetTable() {
        discardPanel.resetPanel();
        for (JPanel playerPanel : playerPanels)
            playerPanel.removeAll();
    }

    @Override
    public void update(Observable o, Object arg) {
        Notification n = (Notification) arg;
        // Utilizzo lo Strategy pattern
        NotifiyHandler handler = null;
        switch (n.getType()) {
            case FILLHAND -> handler = new FillHandHandler(
                    ((Player)n.getObj()).getId(),
                    playerPanels[((Player)n.getObj()).getId()],
                    n.getNumberOfPlayers()
                );
            case DRAW -> handler = new DrawHandler(((Model.Card) n.getObj()), drawnCardPanel);

            case HAND -> handler = new HandHandler(
                    (Pair<Integer, Hand>)n.getObj(),
                    playerPanels[((Pair<Integer, Hand>)n.getObj()).getLeft()],
                    n.getNumberOfPlayers()
            );
            case DISCARD -> handler = new DiscardHandler(
                    ((Model.Card)n.getObj()),
                    discardPanel
            );
        }
        handler.handle();
    }
}
