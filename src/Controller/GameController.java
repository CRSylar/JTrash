package Controller;

import Model.Card;
import Model.ModelManager;
import Utilities.Pair;
import Model.Player;
import Utilities.Utils;
import View.GameManager;
import View.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GameController {

    private final ModelManager model;
    private final GameManager view;

    // Latch usato solo per il giocatore "Umano" per disattivare i listener
    CountDownLatch p0Latch = new CountDownLatch(1);
    // Latch principale che permette di sincronizzare tutto
    CountDownLatch mainLatch = new CountDownLatch(1);
    MyMouseListener ms = new MyMouseListener(this);

    CountDownLatch l;

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
                l = new CountDownLatch(model.getNumberOfPlayers());
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
                    l.await();
                    playTurn(model.getNumberOfPlayers());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                view.resetTable();
            }
            System.out.println("Game won by Player "+ (model.getWinner()) );
            // TODO - animazione che annuncia il vincitore + salvataggio risultato
            disposeGame();
        });
        gameThread.start();

    }

    private void disposeGame() {
        new MenuPanel();
        view.dispose();
    }

    /**
     * Loop del singolo turno, viene "estratto" il player che inizia per primo.
     * Ogni giocatore (tranne il primo) puo pescare dal mazzo o dagli scarti.
     * Quando un giocatore fa Trash viene effettuato un "ultimo giro" fino a tornare
     * al giocatore in questione, gli eventuali Trash degli altri giocatori vengono
     * registrati nell'array playersThatGetOneCardLessNextRound
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

    private void executeCpuTurn(int playerTurn){
        // Se ci sono scarti faccio il Peek della cima, se è una carta con un valore che a me manca me la prendo
        // I casi speciali (Jolly e carte Jolly) sono sempre presi
        if (model.getDiscardPile().size() > 0 && Utils.tryToDrawFromDiscard(model.getDiscardPile().peek(), model.getPlayers()[playerTurn].hand))
            drawFromDiscard(playerTurn);
        else
            drawFromDeck(playerTurn);
    }

    private void executePlayerTurn(MouseListener ms) {
        // setting up the listeners
        view.getDeckPanel().addMouseListener(ms);
        view.getDiscardPanel().addMouseListener(ms);
    }

    private void fillPlayerHand(Player player) {

        new Timer(550, e -> {
            if (player.hand.canHoldMoreCard()) {
                model.fillPlayerHand(player);
            } else {
                Timer self = (Timer) e.getSource();
                self.stop();
                l.countDown();
            }
        }).start();
    }

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
        if (playerTurn == 0)
            p0Latch.countDown();
    }

    public void drawFromDiscard(int playerTurn) {
        if (model.getDiscardPile().size() == 0)
            throw new IllegalStateException("Discard pile is empty");
        //System.out.println("Drawing from the discard");
        view.getDiscardPanel().removeTop();
        subsequentDraws(playerTurn, model.getDiscardPile().drawFromPile());
        if (playerTurn == 0)
            p0Latch.countDown();
    }

    private void subsequentDraws(int playerTurn, Card card) {
        Card c = model.notifyDraw(card);
        new Timer(1200, e -> {
            view.getDrawnCardPanel().setVisible(false);
            view.getDrawnCardPanel().removeAll();
            Pair<Card, Boolean> status = model.computeTurn(playerTurn, c);
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
