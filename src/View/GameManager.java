package View;

import Model.Hand;
import Model.Notification;
import Model.Player;
import Utilities.Pair;

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

        for (int i=0; i < numberOfPlayers; i++) {
            playerPanels[i] = createPlayerPanel(i-1);

        }
        // UI Nord
        InitializeNorthPlayer();
        // UI Sud
        InitializeSouthPlayer();
        if (numberOfPlayers > 2) {
            //UI Ovest
            InitializeWestPlayer();
            if (numberOfPlayers > 3)
                // UI est
                InitializeEastPlayer();
        }

        // setting up the Drawn panel
        drawnCardPanel.setBackground(Color.white);
        drawnCardPanel.setBounds(350, 250,144,192);
        drawnCardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        drawnCardPanel.setVisible(false);
        tablePanel.add(drawnCardPanel, valueOf(5));

        add(tablePanel, BorderLayout.CENTER);
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

    private JPanel createPlayerPanel(int direction) {
        JPanel playerPanel;
        if (direction >= 1)
            playerPanel = new JPanel(new GridLayout(5, 2));
        else
            playerPanel  = new JPanel(new GridLayout(2, 5));

        playerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        playerPanel.setBackground(new Color(0,102,0));

        return playerPanel;
    }

    private void InitializeSouthPlayer() {
        playerPanels[0].setBounds(420,430,440,250);
        this.tablePanel.add(playerPanels[0], valueOf(0));
    }
    private void InitializeNorthPlayer() {
        playerPanels[1].setBounds(420,10,440,250);
        this.tablePanel.add(playerPanels[1], valueOf(0));
    }

    private void InitializeWestPlayer() {
        playerPanels[2].setBounds(25,150,250,440);
        this.tablePanel.add(playerPanels[2], valueOf(0));
    }

    private void InitializeEastPlayer() {
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

    @Override
    public void update(Observable o, Object arg) {
        Notification n = (Notification) arg;

        switch (n.getType()) {
            case FILLHAND -> {
                int id = ((Player)n.getObj()).getId();
                playerPanels[id].setVisible(false);
                playerPanels[id].add(new Card(id > 1));
                playerPanels[id].setVisible(true);
            }
            case DRAW -> {
                Model.Card c = ((Model.Card) n.getObj());
                Image img = new ImageIcon(AssetLoader.getInstance().getCard(c.getValue(), c.getSuit()))
                        .getImage().getScaledInstance(144,192, Image.SCALE_SMOOTH);
                drawnCardPanel.add(
                        new JLabel(
                                new ImageIcon(img)
                        )
                );
                drawnCardPanel.setVisible(true);
            }
            case HAND -> {
                Pair<Integer, Hand> p = ((Pair<Integer, Model.Hand>)n.getObj());
                int playerId = p.getLeft();
                Model.Hand h = p.getRight();
                playerPanels[playerId].setVisible(false);
                playerPanels[playerId].removeAll();
                for (int i=0; i< h.getHandSize(); i++) {
                    if (h.getCard(i).isHide())
                        playerPanels[playerId].add(new Card(playerId > 1));
                    else
                        playerPanels[playerId].add(new Card(h.getCard(i).getValue(),h.getCard(i).getSuit()));
                }
                playerPanels[playerId].setVisible(true);

            }
            case DISCARD -> {
                Model.Card c = ((Model.Card)n.getObj());
                Image img = new ImageIcon(AssetLoader.getInstance().getCard(c.getValue(), c.getSuit()))
                        .getImage();
                discardPanel.add(
                        new JLabel(
                                new ImageIcon(img)
                        )
                );
            }
        }
    }
}
