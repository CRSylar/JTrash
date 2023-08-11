package Model;

import Utilities.Pair;

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
    private final int numberOfPlayers;
    /**
     * Array di player, contiene le istanze dei giocatori (CPU e Umani)
     */
    private Player[] players;

    public ModelManager(int players) {
        if (players > 2) {
            Deck tmp = new Deck();
            System.out.println("Aggiungo secondo deck per 3+ giocatori");
            deck.mergeWithOtherDeck(tmp.getDeck());
        }
        this.numberOfPlayers = players;
        this.players = new Player[players];
    }

    public void resetPlayersThatGetOneCardLessNextRound() {
        this.playersThatGetOneCardLessNextRound = new int[numberOfPlayers];
    }

    /**
     * Metodo di comodo
     * aggiunge una carta in mano al giocatore e notifica l'osservatore
     * @param player TODO - Indice nell' array dei giocatori che rappresenta il giocatore la cui mano riempire
     */
    public void fillPlayerHand(Player player) {
        player.hand.addCard(deck.drawCard());
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.FILLHAND,
                player
        ));
    }

    /**
     * Metodo interno che controlla se uno dei giocatori
     * è il vincitore (ovvero ha fatto Trash e avrebbe Zero
     * carte
     * @return un booleano.
     */
    public boolean theresAWinner() {
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

    public Card notifyDraw(Card c) {
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.DRAW,
                c
        ));
        return c;
    }

    public int[] getPlayersThatGetOneCardLessNextRound() {return this.playersThatGetOneCardLessNextRound;}
    public int getNumberOfPlayers() {return this.numberOfPlayers;}
    public int getWinner() {return this.winner;}
    public Deck getDeck() { return this.deck;}
    public DiscardPile getDiscardPile() {return this.discardPile;}
    public Player[] getPlayers() {return this.players;}
    public int getCardDrawnFromBottomOfThePile() {return cardDrawnFromBottomOfThePile;}
    public Card drawFromBottom() {
        return discardPile.drawFromIndex(cardDrawnFromBottomOfThePile++);
    }
    public void resetDrawnFromBottom() {
        cardDrawnFromBottomOfThePile = 0;
    }

    public Pair<Integer, Integer> getMaxHandSize() {
        int i = 0;
        int playerId = -1;
        for (Player player: players){
            if (i < player.hand.getMaxSize()) {
                i = player.hand.getMaxSize();
                playerId = player.getId();
            }
        }

        return new Pair<>(playerId, i);
    }

    public Pair<Card, Boolean> humanTurn(Card card) {
        Pair<Card, Boolean> status = players[0].playTurn(card);
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.HAND,
                new Pair<Integer, Hand>(0,players[0].hand)
        ));
        return status;
    }

    public void discard(Card card) {
        discardPile.addCard(card);
        setChanged();
        notifyObservers(
                new Notification(
                        Notification.TYPES.DISCARD,
                        card
                )
        );
    }
}
