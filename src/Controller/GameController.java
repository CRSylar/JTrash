package Controller;

import Model.Card;
import Model.ModelManager;
import Utilities.GameResult;
import Utilities.Pair;
import Model.Player;
import Utilities.Utils;
import View.GameManager;
import View.Sounds;
import View.StartingScreen;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Controller che gestisce la finestra della partita,
 * coordinando Model e View con il pattern OO
 * e sincronizzandoli utilizzando dei CountDownLatch
 */
public class GameController {

    /**
     * Istanza del modello, un Observable
     */
    private final ModelManager model;
    /**
     * Istanza della View, un Observer
     */
    private final GameManager view;

    /**
     * Latch usato solo per il giocatore "Umano" per disattivare i listener
     * dei "Bottoni" per pescare le carte da Mazzo e Scarti
     */
    CountDownLatch p0Latch = new CountDownLatch(1);
    /**
     * Latch principale che permette di mantenere
     * sincronizzati View e Model durante il turno di
     * un giocatore (umano o CPU)
     */
    CountDownLatch mainLatch = new CountDownLatch(1);
    /**
     * MouseListener in ascolto del click sui componenti
     * della View (da parte del giocatore umano)
     */
    MyMouseListener ms = new MyMouseListener(this);

    /**
     * Latch che tiene sincronizzati View e Model
     * durante la fase di "dare carte" ai giocatori
     * che altrimenti inizierebbero a giocare prima di
     * aver ricevuto tutte le carte in mano
     */
    CountDownLatch openingHandsLatch;

    /**
     * Costruisce un Controller e prepara Model e View per
     * la partita che sta per iniziare, riceve il numero di giocatori
     * che partecipano alla partita
     * @param players il numero di giocatori per la partita da impostare
     */
    public GameController(int players) {
        // create the ModelManager
        model = new ModelManager(players);
        // create the ViewManager
        view = new GameManager(players);
        model.addObserver(view);
        view.setVisible(true);


    }

    /**
     * GameLoop, assegna la mano (e relativa dimensione massima) ai giocatori
     * basandosi sui valori nell'array playersThatGetOneCardLessNextRound,
     * che conterrà il valore uno nella posizione relativa al giocatore che ha fatto
     * Trash il turno precedente.
     * Dopo di che avvia un turno
     */
    public void start() {
        Thread gameThread = new Thread( () -> {
            // preparazione turno, model e view vengono resettate e preparate
            Player[] players = model.getPlayers();
            while (true) {
                Sounds.getInstance().play(Sounds.CLIPTYPE.SHUFFLE, false);
                openingHandsLatch = new CountDownLatch(model.getNumberOfPlayers());
                // mischio il mazzo
                model.getDeck().shuffle();
                // svuoto la pila degli scarti
                model.getDiscardPile().clearPile();
                // fill the players hands
                for (int i=0; i < model.getNumberOfPlayers(); i++) {
                    if (players[i] == null)
                        players[i] = new Player(i);
                    else if (model.getPlayersThatGetOneCardLessNextRound()[i] == 1)
                        players[i] = new Player(i, players[i].hand.getHandSize() - 1);
                    else
                        players[i] = new Player(i, players[i].hand.getHandSize());
                    fillPlayerHand(players[i]);
                }

                if (model.theresAWinner())
                    break;
                // svolgimento turno vero e proprio
                try {
                    openingHandsLatch.await();
                    playTurn(model.getNumberOfPlayers());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                view.resetTable();
            }
            displayEndGameDialogueAndDispose(model.getWinner());
        });
        gameThread.start();

    }

    /**
     * Mostra per 3.5 secondi il dialogue che informa il giocatore sull'esito della partita
     * in caso di vittoria riproduce anche una traccia audio
     * @param winner il giocatore che ha vinto la partita appena terminata, utilizzato per costruire
     *               un GameResult
     */
    private void displayEndGameDialogueAndDispose(int winner) {
        try {
            GameResult gr = new GameResult(winner);
            if (gr.getResult() == GameResult.RESULT.WIN) {
                Sounds.getInstance().play(Sounds.CLIPTYPE.PLAYERWIN, false);
                // Show 3.5 seconds of win screen
                showMessageDialogue("assets/you_win.gif");

            }
            else
                showMessageDialogue("assets/you_lose.png");
            disposeGame(gr);
        } catch (InvalidAttributeValueException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea e mostra una finestra contenente la stringa ricevuta per 3.5secondi
     * poi fa il dispose della finestra creata in autonomia.
     * L'utilizzo previsto richiede che la stringa sia il path a una risorsa immagine (png/gif/etc...)
     * @param s il path alla risorsa da visualizzare
     */
    private void showMessageDialogue(String s) {
        JWindow w = new JWindow();
        w.setSize(400, 200);
        w.setLocationRelativeTo(null);
        try {
            ImageIcon img = new ImageIcon(s);
            JLabel label = new JLabel(img);
            w.getContentPane().add(label);
            w.setVisible(true);
            Thread.sleep(3500);
            w.setVisible(false);
            w.dispose();
        } catch (InterruptedException e ) {
            System.out.println("Can't make the GameResult screen sit for 3.5 second, interrupted");
            e.printStackTrace();
        }

    }

    /**
     * Distrugge la View attuale (quella che mostra la partita in corso)
     * e istanzia un nuovo controller per tornare al menu principale
     * passandogli il vincitore del game attuale
     * @param result il risultato del player umano della partita attuale (win/loss/draw)
     */
    private void disposeGame(GameResult result) {
        StartingScreen sc = new StartingScreen(true);
        MainMenuController ssc = new MainMenuController(sc, result);
        view.dispose();
    }

    /**
     * Loop del singolo turno, viene "estratto" il player che inizia per primo.
     * Ogni giocatore (tranne il primo) puo pescare dal mazzo o dagli scarti.
     * Quando un giocatore fa Trash viene effettuato un "ultimo giro" fino a tornare
     * al giocatore in questione, gli eventuali Trash degli altri giocatori vengono
     * registrati nell' array playersThatGetOneCardLessNextRound
     */
    private void playTurn(int numberOfPlayers) throws InterruptedException {
        model.resetPlayersThatGetOneCardLessNextRound();
        int playerTurn = (new Random().nextInt(100)) % numberOfPlayers;
        boolean turnStatus = true;
        // Variabile che mi serve per sapere quale giocatore ha fatto Trash
        // e fare un altro giro di tavolo prima della fine del turno.
        // Inizia da -1 cosi da non creare race conditions
        int playerTrashed = -1;
        while (turnStatus) {
            playerTurn = playerTurn % numberOfPlayers;
            view.getPlayerPanel(playerTurn).setBorder(
                    BorderFactory.createLineBorder(Color.GREEN)
            );
            // Turno giocatore Umano
            if (playerTurn == 0) {
                executePlayerTurn(ms);
                // appena viene cliccato uno tra deck e scarti rimuovo i listener, per evitare eventi multipli.
                p0Latch.await();
                view.getDeckPanel().removeMouseListener(ms);
                view.getDiscardPanel().removeMouseListener(ms);
                // resetto il latch
                p0Latch = new CountDownLatch(1);
            }
            // Turno CPU (p1/2/3)
            else
                executeCpuTurn(playerTurn);

            mainLatch.await();

            if (model.getPlayers()[playerTurn].hand.handFullyVisible()) {
                model.setPlayersThatGetOneCardLessNextRound(playerTurn);
                Sounds.getInstance().play(Sounds.CLIPTYPE.TRASH, false);
                // ulteriore controllo per evitare loop infiniti nei casi limite in cui
                // in una partita a due un giocatore fa Trash e al giro successivo
                // fa Trash anche l'altro (playerTrashed si sarebbe aggiornato all' infinito)
                if (playerTrashed == -1)
                    playerTrashed = playerTurn;
            }

            // tolgo il bordo
            view.getPlayerPanel(playerTurn).setBorder(null);
            // incremento il turno, tocca al giocatore successivo
            playerTurn++;
            if (playerTrashed == (playerTurn % numberOfPlayers))
                turnStatus = false;
            mainLatch = new CountDownLatch(1);
        }
    }

    /**
     * Esegue il turno di un giocatore gestito dalla CPU
     * @param playerTurn il giocatore di cui eseguire il turno
     */
    private void executeCpuTurn(int playerTurn){
        // Se ci sono scarti faccio il Peek della cima, se è una carta con un valore che a me manca me la prendo
        // I casi speciali (Jolly e carte Jolly) sono sempre presi
        if (model.getDiscardPile().size() > 0 && Utils.tryToDrawFromDiscard(model.getDiscardPile().peek(), model.getPlayers()[playerTurn].hand))
            drawFromDiscard(playerTurn);
        else
            drawFromDeck(playerTurn);
    }

    /**
     * Esegue il turno del giocatore "Umano"
     * riceve il mouseListener per gestire i click dell'utente sulla UI
     * @param ms il mouseListener
     */
    private void executePlayerTurn(MouseListener ms) {
        // setting up the listeners
        view.getDeckPanel().addMouseListener(ms);
        view.getDiscardPanel().addMouseListener(ms);
    }

    /**
     * Aggiunge una carta alla mano del giocatore
     * questo "evento" viene eseguito dopo un Timer di 550ms,
     * il che consente di creare l'animazione della pescata
     * sulla View, quando termina il Timer viene fatto il countDown
     * del latch openingHandsLatch
     * @param player il giocatore alla cui mano aggiungere una carta
     */
    private void fillPlayerHand(Player player) {

        new Timer(550, e -> {
            if (player.hand.canHoldMoreCard()) {
                model.fillPlayerHand(player);
            } else {
                Timer self = (Timer) e.getSource();
                self.stop();
                openingHandsLatch.countDown();
            }
        }).start();
    }

    /**
     * Pesca una carta dal Deck, o se il deck è vuoto
     * la pesca dal fondo della pila degli scarti
     * e la assegna al giocatore di turno
     * @param playerTurn il giocatore di turno
     */
    public void drawFromDeck(int playerTurn) {
        // se le carte nel mazzo sono finite vengono usate
        // quelle nella pila degli scarti, messe al contrario
        // quindi per semplicità pesco da sotto
        if (model.getDeck().cardLeft() == 0){
            subsequentDraws(playerTurn, model.drawFromBottom());
        }
        else {
            //System.out.println("Drawing from the deck");
            // Draw from deck si occupa di notificare la view
            subsequentDraws(playerTurn, model.getDeck().drawCard());
        }
        // se è il turno del giocatore umano devo sbloccare il suo latch
        // per rimuovere i listener dai bottoni
        if (playerTurn == 0)
            p0Latch.countDown();
    }

    /**
     * Pesca una carta dalla pila degli scarti
     * e la assegna al giocatore di turno
     * @param playerTurn il giocatore di turno
     */
    public void drawFromDiscard(int playerTurn) {
        if (model.getDiscardPile().size() == 0)
            throw new IllegalStateException("Discard pile is empty");
        //System.out.println("Drawing from the discard");
        view.getDiscardPanel().removeTop();
        subsequentDraws(playerTurn, model.getDiscardPile().drawFromPile());
        // se è il turno del giocatore umano devo sbloccare il suo latch
        // per rimuovere i listener dai bottoni
        if (playerTurn == 0)
            p0Latch.countDown();
    }

    /**
     * Gestisce le pescate successive alla prima.
     * Chiede al Model di computare la carta pescata
     * e fa una chiamata RICORSIVA se la carta è stata giocata
     * passando la nuova carta nella ricorsione.
     * Per creare l' effetto di pescata sulla View
     * il tutto viene gestito da un Timer, che esegue la logica
     * descritta sopra dopo 1,2secondi.
     * Quando la carta ricevuta non è giocabile
     * viene fatto il countDown del mainLatch per
     * passare al giocatore successivo
     * @param playerTurn il giocatore di turno
     * @param card la carta pescata (o scambiata nelle chiamate ricorsive)
     */
    private void subsequentDraws(int playerTurn, Card card) {
        model.notifyDraw(card);
        new Timer(1200, e -> {
            view.getDrawnCardPanel().setVisible(false);
            view.getDrawnCardPanel().removeAll();
            Pair<Card, Boolean> status = model.computeTurn(playerTurn, card);
            ((Timer)e.getSource()).stop();
            if (status.getRight()) {
                model.discard(status.getLeft());
                mainLatch.countDown();
            }
            else
                subsequentDraws(playerTurn, status.getLeft());
        }).start();
    }
}
