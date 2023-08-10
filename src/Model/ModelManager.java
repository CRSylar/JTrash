package Model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


@SuppressWarnings("deprecation")
public class ModelManager extends Observable {

    /**
     * Mazzo usato per durante tutta la partita,
     * viene mischiato a inizio turno
     * e unito con un mazzo temporaneo per partite con piu di due giocatori
     */
    private final Deck deck = new Deck();

    /**
     * Pila degli scarti, si puo pescare la carta in cima
     * in caso il mazzo si esaurisca, viene messa nel mazzo in ordine inverso
     */
    private final DiscardPile discardPile = new DiscardPile();

    /**
     * Contiene l'informazione su chi è il vincitore
     * inizialmente = -1, ergo nessun vincitore
     */
    private int winner = -1;
    /**
     * Array d'interi utilizzato per tenere traccia dei giocatori che fanno
     * Trash durante un turno e che quindi devono ricevere una carta in meno
     * al turno successivo
     */
    private int[] playersThatGetOneCardLessNextRound;
    private int cardDrawnFromBottomOfThePile;

    /**
     * Numero di giocatori per la partita in corso
     */
    int numberOfPlayers;
    /**
     * Array di player, contiene le istanze dei giocatori (CPU e Umani)
     */
    Player[] players;

    public ModelManager(int players) {
        if (players > 2) {
            Deck tmp = new Deck();
            System.out.println("Aggiungo secondo deck per 3+ giocatori");
            deck.mergeWithOtherDeck(tmp.getDeck());
        }
        this.numberOfPlayers = players;
        this.players = new Player[players];
        this.playersThatGetOneCardLessNextRound = new int[players];
    }

    /**
     * Metodo di comodo
     * riempie la mano di un giocatore
     * @param index - Indice nell' array dei giocatori che rappresenta il giocatore la cui mano riempire
     */
    public void fillPlayerHand(int index) {
        /*
        while (players[0].hand.canHoldMoreCard()) {
            players[0].hand.addCard(deck.drawCard());
            setChanged();
            notifyObservers("nuovaCarta aggiunta alla mano di "+index);
        }
         */
        new Timer(550, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (players[index].hand.canHoldMoreCard()) {
                    players[index].hand.addCard(deck.drawCard());
                    setChanged();
                    notifyObservers(index); // TODO standardizzare la notifica con OBJ
                } else {
                    Timer self = (Timer) e.getSource();
                    self.stop();
                    System.out.println("Model: Timer Stopped");
                }
            }
        }).start();
    }

    /**
     * GameLoop, assegna la mano (e relativa dimensione massima) ai giocatori
     * basandosi sui valori nell'array playersThatGetOneCardLessNextRound,
     * che conterrà il valore 1 nella posizione relativa al giocatore che ha fatto
     * Trash il turno precedente.
     * Dopo di che avvia un turno
     */
    public void startGame() {
        Thread gameThread = new Thread( () -> {
            while (!theresAWinner()) {
                deck.shuffle();
                // fill the players hands
                for (int i=0; i < numberOfPlayers; i++) {
                    if (players[i] == null)
                        players[i] = new Player();
                    else if (playersThatGetOneCardLessNextRound[i] == 1)
                        players[i] = new Player(i, players[i].hand.getHandSize() - 1);
                    else
                        players[i] = new Player(i, players[i].hand.getHandSize());
                    fillPlayerHand(i);
                }
                playTurn();
            }
        });
       gameThread.start();
       System.out.println("Game won by Player "+ (winner) );
    }

    /**
     * Loop del singolo turno, viene "estratto" il player che inizia per primo.
     * Ogni giocatore (tranne il primo) puo pescare dal mazzo o dagli scarti.
     * Quando un giocatore fa Trash viene effettuato un "ultimo giro" fino a tornare
     * al giocatore in questione, gli eventuali Trash degli altri giocatori vengono
     * registrati nell'array playersThatGetOneCardLessNextRound
     */
    private void playTurn() {
        discardPile.clearPile();
    }

    /**
     * Metodo interno che controlla se uno dei giocatori
     * è il vincitore (ovvero ha fatto Trash e avrebbe Zero
     * carte
     * @return un booleano.
     */
    private boolean theresAWinner() {
        int i = 0;
        for (Player player : players){
            if (player != null && player.hand.getHandSize() == 0) {
                winner = i;
                return true;
            }
            i++;
        }
        return false;
    }
}
