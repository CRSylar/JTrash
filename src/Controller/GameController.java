package Controller;

import Model.ModelManager;
import Model.Pair;
import Model.Player;
import View.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GameController {

    private final ModelManager model;
    private final GameManager view;

    CountDownLatch latch = new CountDownLatch(1);

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
            while (!model.theresAWinner()) {
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
                // queste due righe servono a "bloccare" tutto mentre aspetto che la view
                // finisca di mettere le carte in mano ai giocatori
                Pair<Integer, Integer> p = model.getMaxHandSize();
                while (view.getPlayerPanel(p.getLeft()).getComponentCount() < p.getRight());
                // END

                // svolgimento turno vero e proprio
                playTurn(model.getNumberOfPlayers());
            }
        });
        gameThread.start();
        System.out.println("Game won by Player "+ (model.getWinner()) );
    }

    /**
     * Loop del singolo turno, viene "estratto" il player che inizia per primo.
     * Ogni giocatore (tranne il primo) puo pescare dal mazzo o dagli scarti.
     * Quando un giocatore fa Trash viene effettuato un "ultimo giro" fino a tornare
     * al giocatore in questione, gli eventuali Trash degli altri giocatori vengono
     * registrati nell'array playersThatGetOneCardLessNextRound
     */
    private void playTurn(int numberOfPlayers) {
        model.resetPlayersThatGetOneCardLessNextRound();
        int playerTurn = (new Random().nextInt(100)) % numberOfPlayers;
        boolean turnStatus = true;
        // Variabile che mi serve per sapere quale giocatore ha fatto Trash
        // e fare un altro giro di tavolo prima della fine del turno.
        // Inizia da -1 cosi da non creare race conditions
        int playerTrashed = -1;
        while (turnStatus) {
            view.getPlayerPanel(playerTurn % numberOfPlayers).setBorder(
                    BorderFactory.createLineBorder(Color.GREEN)
            );

            if (playerTurn % numberOfPlayers == 0) {
                MyMouseListener ms = new MyMouseListener(this);
                executePlayerTurn(ms);
                try {
                    latch.await();
                    view.getDeckPanel().removeMouseListener(ms);
                    view.getDiscardPanel().removeMouseListener(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            else {}
               // cardDrawnFromBottomOfThePile = executeCpuTurn(playerTurn, cardDrawnFromBottomOfThePile);

            // tolgo il bordo
            view.getPlayerPanel(playerTurn% numberOfPlayers).setBorder(null);
            // incremento il turno, tocca al giocatore successivo
            playerTurn++;
            if (playerTrashed == (playerTurn % numberOfPlayers))
                turnStatus = false;
        }
    }

    private void executePlayerTurn(MouseListener ms) {
        // setting up the listeners
        view.getDeckPanel().addMouseListener(ms);
        view.getDiscardPanel().addMouseListener(ms);
    }

    private void fillPlayerHand(Player player) {
        new Timer(550, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.hand.canHoldMoreCard()) {
                    model.fillPlayerHand(player);
                } else {
                    Timer self = (Timer) e.getSource();
                    self.stop();
                    System.out.println("Model: Timer Stopped");
                }
            }
        }).start();
    }

    public void drawFromDeck() {
        // se le carte nel mazzo sono finite vengono usate
        // quelle nella pila degli scarti, messe al contrario
        // quindi per semplicità pesco da sotto
        if (model.getDeck().cardLeft() == 0){
            System.out.println("Drawing from the bottom of the discard");
            model.getPlayers()[0].playTurn(
                    model.getDiscardPile().drawFromIndex(
                            model.getCardDrawnFromBottomOfThePile()
                    ));
            model.drawFromBottom();
        }
        else {
            System.out.println("Drawing from the deck");
            model.getPlayers()[0].playTurn(model.getDeck().drawCard());
        }
        // inviare notifica alla view
        latch.countDown();
    }
    public void drawFromDiscard() {
        if (model.getDiscardPile().size() == 0)
            throw new IllegalStateException("Discard pile is empty");
        System.out.println("Drawing from the discard");
        // inviare notifica alla view
        model.getPlayers()[0].playTurn(model.getDiscardPile().drawFromPile());
        latch.countDown();
    }
}
