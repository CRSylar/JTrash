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
    /**
     * Definisce quante carte sono state pescate dal fondo della pila degli scarti
     * nel caso in cui questa venga usata come mazzo
     * (significa che il mazzo è finito e nessun giocatore ha fatto Trash)
     */
    private int cardDrawnFromBottomOfThePile;

    /**
     * Numero di giocatori per la partita in corso
     */
    private final int numberOfPlayers;
    /**
     * Array di player, contiene le istanze dei giocatori (CPU e Umani)
     */
    private final Player[] players;

    /**
     * Costruisce un istanza di ModelManager, al quale vengono passati il numero di giocatori
     * che prenderanno parte alla partita
     * @param players il numero di giocatori per la partita attuale
     */
    public ModelManager(int players) {
        if (players > 2) {
            Deck tmp = new Deck();
            deck.mergeWithOtherDeck(tmp.getDeck());
        }
        this.numberOfPlayers = players;
        this.players = new Player[players];
    }

    /**
     * Resetta l' array utilizzato per controllare quale/i giocatore/i deve ricevere una carta in meno
     * all'inizio del round (solo per round successivi al primo)
     */
    public void resetPlayersThatGetOneCardLessNextRound() {
        this.playersThatGetOneCardLessNextRound = new int[numberOfPlayers];
    }

    /**
     * Setta una flag all'interno dell' array per marcare il giocatore come
     * giocatore che riceverà una carta in meno al prossimo turno
     * @param playerTurn il giocatore che ha fatto trash e deve ricevere una carta in meno al prossimo turno
     */
    public void setPlayersThatGetOneCardLessNextRound(int playerTurn) {
        playersThatGetOneCardLessNextRound[playerTurn] = 1;
    }
    /**
     * Aggiunge una carta in mano al giocatore e notifica l'osservatore
     * @param player il giocatore la cui mano riempire
     */
    public void fillPlayerHand(Player player) {
        player.hand.addCard(deck.drawCard());
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.FILLHAND,
                player,
                players.length

        ));
    }

    /**
     * Controlla se uno dei giocatori
     * è il vincitore (ovvero ha fatto Trash e avrebbe Zero
     * carte
     * @return un booleano.
     */
    public boolean theresAWinner() {
        boolean ret = false;
        int i = 0;
        for (Player player : players){
            if (player != null && player.hand.getMaxSize() == 0) {
                winner = i;
                if (ret)
                    winner = 4;
                ret = true;
            }
            i++;
        }
        return ret;
    }

    /**
     * Gioca una carta, e ritorna un Pair formato da una carta e lo stato (ovvero la nuova carta e se è giocabile o meno)
     * @param playerTurn il giocatore il cui turno computare
     * @param card la carta che è stata pescata
     * @return Pair<Card, Boolean> contenente la nuova carta e la giocabilità della stessa
     * (se il valore Right è true -> Left è uguale alla carta passata, quindi da scartare,
     * se è false -> Left è una nuova carta, da controllare)
     */
    public Pair<Card, Boolean> computeTurn(int playerTurn, Card card) {
        Pair<Card, Boolean> status = players[playerTurn].playTurn(card);
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.HAND,
                new Pair<Integer, Hand>(playerTurn,players[playerTurn].hand),
                players.length
        ));
        return status;
    }

    /**
     * Scarta la carta e notifica gli Observer
     * @param card lo scarto
     */
    public void discard(Card card) {
        discardPile.addCard(card);
        setChanged();
        notifyObservers(
                new Notification(
                        Notification.TYPES.DISCARD,
                        card,
                        players.length
                )
        );
    }

    /**
     * Notifica gli Observer che una carta è stata pescata
     * @param c la carta pescata da inviare nella notifica
     */
    public void notifyDraw(Card c) {
        setChanged();
        notifyObservers(new Notification(
                Notification.TYPES.DRAW,
                c,
                players.length
        ));
    }

    /**
     *
     * @return Array contenente le flag di quale/i giocatore/i devono ricevere una carta in meno il prossimo round
     */
    public int[] getPlayersThatGetOneCardLessNextRound() {return this.playersThatGetOneCardLessNextRound;}

    /**
     *
     * @return numero dei giocatori nella partita in corso
     */
    public int getNumberOfPlayers() {return this.numberOfPlayers;}

    /**
     *
     * @return il player che ha vinto la partita
     */
    public int getWinner() {return this.winner;}

    /**
     *
     * @return il Mazzo utilizzato nel turno in corso
     */
    public Deck getDeck() { return this.deck;}

    /**
     *
     * @return la pila degli scarti del turno in corso
     */
    public DiscardPile getDiscardPile() {return this.discardPile;}

    /**
     *
     * @return L' Array con le istanze di Model.Player che stanno giocando la partita attuale
     */
    public Player[] getPlayers() {return this.players;}

    /**
     * Pesca una carta dal fondo della pila degli scarti (pila degli scarti capovolta, usata come mazzo quando questo termina)
     * @return la carta pescata
     */
    public Card drawFromBottom() {
        return discardPile.drawFromIndex(cardDrawnFromBottomOfThePile++);
    }
}
