package View;

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
    public JPanel[] playerPanels;

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
        tablePanel.add(drawnCardPanel, 5);

        add(tablePanel, BorderLayout.CENTER);
    }


    private void InitializeTable() {
        // Table Panel initialization
        tablePanel.setBounds(0,0,1280,700);
        tablePanel.setBackground(new Color(0,102,0));
        tablePanel.setOpaque(true);
    }

    private void InitializeDeckNDiscard() {
        discardPanel.setToolTipText("Click to draw the Top Card");
        discardPanel.setBounds(557, 293, 78, 114);
        tablePanel.add(discardPanel, valueOf(1));

        deckPanel.setToolTipText("Click to draw a Card");
        deckPanel.setBounds(645, 293, 78, 114);
        tablePanel.add(deckPanel, valueOf(1));
    }

    private JPanel createPlayerPanel(int direction) {
        JPanel playerPanel;
        if (direction >= 1)
            playerPanel = new JPanel(new GridLayout(5, 2));
        else
            playerPanel  = new JPanel(new GridLayout(2, 5));

        /*
        for (int j=0; j < 10; j++)
            playerPanel.add(new Card(direction >= 1));
         */
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        playerPanel.setBackground(new Color(0,102,0));
        //playerPanel.setVisible(true);

        return playerPanel;
    }

    private void InitializeSouthPlayer() {
        playerPanels[0].setBounds(440,453,400,235);
        this.tablePanel.add(playerPanels[0], valueOf(0));
    }
    private void InitializeNorthPlayer() {
        playerPanels[1].setBounds(440,5,400,235);
        this.tablePanel.add(playerPanels[1], valueOf(0));
    }

    private void InitializeWestPlayer() {
        playerPanels[2].setBounds(25,150,235,400);
        this.tablePanel.add(playerPanels[2], valueOf(0));
    }

    private void InitializeEastPlayer() {
        playerPanels[3].setBounds(1020,150,235,400);
        this.tablePanel.add(playerPanels[3],  valueOf(0));
    }
    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("View: Adding new Card");
        playerPanels[(Integer)arg].setVisible(false);
        playerPanels[(Integer)arg].add(new Card((Integer)arg > 1));
        playerPanels[(Integer)arg].setVisible(true);

    }
}
