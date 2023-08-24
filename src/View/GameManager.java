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

/**
 * Frame che implementa l'interfaccia Observer e che mostra tutto il frame di gioco.
 * Istanzia e alloca tutti gli elementi interni al frame del gioco, riproduce file audio in accordo con lo svolgimento
 * della partita, riceve notifiche dall'Observable per gli eventi.
 */
@SuppressWarnings("deprecation")
public class GameManager extends JFrame implements Observer {

    /**
     * LayeredPane che rappresenta il tavolo da gioco
     */
    private final JLayeredPane tablePanel;
    /**
     * Pannello che rappresenta il Mazzo di carte
     */
    private final DeckPanel deckPanel;
    /**
     * Pannello che rappresenta la pila degli scarti
     */
    private final DiscardPanel discardPanel;
    /**
     * Pannello che rappresenta lo spazio dove la carta
     * pescata (o scambiata) viene mostrata all'utente
     * per una UX fluida
     */
    private final JPanel drawnCardPanel;
    /**
     * Array di Pannelli che rappresentano lo spazio dedicato a ciascun giocatore
     * sul tavolo
     */
    private final JPanel[] playerPanels;

    /**
     * Costruisce la classe a partire dal numero di giocatori
     * imposta la finestra, la sua dimensione e posizione, il titolo
     * e inizializza i componenti al suo interno.
     * Nota questo Frame NON utilizza un LayoutManager, i componenti
     * sono disposti secondo precisi calcoli.
     * Non è quindi possibile fare il resize della finestra di gioco
     * @param numberOfPlayers numero di giocatori
     */
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

    /**
     * Inizializza il pannello del giocatore uno in caso di partita
     * a due giocatori, ovvero il pannello in alto
     * (quello in basso è sempre il pannello del giocatore "Umano")
     */
    private void initTwoPlayerGame() {
        InitializeNorthPlayer(1);
    }

    /**
     * Inizializza due pannelli per una partita a tre giocatori.
     * Notare che in questa configurazione il giocatore uno diventa quello
     * a SX, mentre quello in alto è il giocatore due, questo per garantire
     * un corretto svolgimento dell'alternanza dei turni in senso orario
     */
    private void initTreePlayerGame() {
        InitializeNorthPlayer(2);
        InitializeWestPlayer();
    }

    /**
     * Inizializza tutti e tre gli altri pannelli per una partita da Quattro giocatori
     * in questa configurazione tutti i lati sono occupati, ma il giocatore umano occupa sempre la posizione
     * in basso.
     */
    private void initFourPlayerGame() {
        InitializeWestPlayer();
        InitializeNorthPlayer(2);
        InitializeEastPlayer();
    }

    /**
     * @param idx indice del pannello desiderato
     * @return pannello del giocatore
     */
    public JPanel getPlayerPanel(int idx) {return this.playerPanels[idx];}

    /**
     * Inizializza il Pannello del tavolo, impostandone il colore di Background
     */
    private void InitializeTable() {
        // Table Panel initialization
        tablePanel.setBounds(0,0,1280,700);
        tablePanel.setBackground(new Color(0,102,0));
        tablePanel.setOpaque(true);
    }

    /**
     * Inizializza i pannelli di Mazzo e pila degli scarti,
     * assegnandone la posizione all'interno del Frame e impostando i ToolTip
     * per una corretta accessibilità
     */
    private void InitializeDeckNDiscard() {
        discardPanel.setToolTipText("Click to draw the Top Card");
        discardPanel.setBounds(557, 285, 78, 114);
        tablePanel.add(discardPanel, valueOf(1));

        deckPanel.setToolTipText("Click to draw a Card");
        deckPanel.setBounds(645, 285, 78, 114);
        tablePanel.add(deckPanel, valueOf(1));
    }

    /**
     * Inizializza un Pannello del giocatore, in posizione verticale od orizzontale
     * @param rotated true -> pannello in verticale, false -> pannello in orizzontale
     * @return il Pannello creato
     */
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

    /**
     * Inizializza il pannello del giocatore umano,
     * sempre disposto nella parte bassa dello schermo
     */
    private void InitializeSouthPlayer() {
        playerPanels[0] = createPlayerPanel(false);
        playerPanels[0].setBounds(420,430,440,250);
        this.tablePanel.add(playerPanels[0], valueOf(0));
    }

    /**
     * Inizializza il pannello del giocatore in alto, che puo essere
     * sia il giocatore uno che il due a seconda del numero di giocatori totali
     * @param pos la posizione che il giocatore ricopre nel totale
     */
    private void InitializeNorthPlayer(int pos) {
        playerPanels[pos] = createPlayerPanel(false);
        playerPanels[pos].setBounds(420,10,440,250);
        this.tablePanel.add(playerPanels[pos], valueOf(0));
    }

    /**
     * Inizializza il pannello del giocatore di sinistra, se presente è sempre il giocatore uno
     */
    private void InitializeWestPlayer() {
        playerPanels[1] = createPlayerPanel(true);
        playerPanels[1].setBounds(25,150,250,440);
        this.tablePanel.add(playerPanels[1], valueOf(0));
    }

    /**
     * Inizializza il pannello del giocatore a destra, se presente è sempre il giocatore 3
     */
    private void InitializeEastPlayer() {
        playerPanels[3] = createPlayerPanel(true);
        playerPanels[3].setBounds(1000,150,250,440);
        this.tablePanel.add(playerPanels[3],  valueOf(0));
    }

    /**
     *
     * @return il pannello del Deck
     */
    public DeckPanel getDeckPanel() {
        return deckPanel;
    }

    /**
     *
     * @return Il pannello degli scarti
     */
    public DiscardPanel getDiscardPanel() {
        return discardPanel;
    }

    /**
     *
     * @return il pannello di appoggio della carta pescata
     */
    public JPanel getDrawnCardPanel() {
        return drawnCardPanel;
    }

    /**
     * Resetta i pannelli dei Players e degli scarti
     * utilizzato per fare una transizione da un turno a quello successivo
     */
    public void resetTable() {
        discardPanel.resetPanel();
        for (JPanel playerPanel : playerPanels)
            playerPanel.removeAll();
    }

    /**
     * Override del metodo di Observer, viene invocato quando si riceve un messaggio.
     * Nel nostro caso l' arg è sempre un oggetto di tipo Notification, creato da me
     * appositamente per rappresentare il messaggio in modo chiaro e per poter implementare
     * l'utilizzo del pattern Strategy
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method. in this runtime will be always of type Notification
     */
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
