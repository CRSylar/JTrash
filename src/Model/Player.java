package Model;

import Utilities.Pair;
import Utilities.Utils;

/**
 * Classe che descrive il comportamento di un
 * player generico (Utente o CPU)
 */
public class Player {

    private final int id;

    /**
     * Un giocatore ha sempre una mano, anche se potenzialmente vuota
     */
    public Hand hand;
    /**
     * Costruisce un player con una mano "base" di 10 carte
     */
    public Player(int id) {
        this.id = id;
        hand = new Hand();
    }

    /**
     * Costruisce un player definendo la dimensione massima della mano,
     * da utilizzare nei turni successivi al primo (di ogni game)
     * @param maxHandSize dimensione massima della mano
     */
    public Player(int id, int maxHandSize) {
        this.id = id;
        hand = new Hand(maxHandSize);
    }


    /**
     * Metodo che effettua le giocate "obbligate" per il turno,
     * Partendo da una carta pescata dal mazzo (qualsiasi)
     * oppure dalla pila degli scarti (in questo caso la carta è sicuramente nel range 1-10/jolly)
     * @param card la carta descritta sopra
     * @return la carta che viene scartata alla fine del turno o dopo un Trash!
     */
    public Pair<Card, Boolean> playTurn(Card card) {
        System.out.println("Drawn card: "+ card + " by player "+id);
        int cardValue = card.getValue();
        // se la mano è tutta visibile la carta viene automaticamente scartata
        if (hand.handFullyVisible())
            return new Pair<Card, Boolean>(card, true);
        // Se ho pescato un Jolly o un K devo attivare una "scelta" del player o della CPU
        if (cardValue == 0 || cardValue == 13) {
            // Per ora implemento una scelta della CPU molto semplice, inserisce il jolly alla prima posizione utile,
            // per quanto riguarda il player umano poi vediamo
            int i = 0;
            while (i++ < hand.getHandSize()) {
                if (hand.cardIsHide(i))
                    break;
            }
            return new Pair<>(hand.swapCardInPosition(i, card), false);
        }
        // Se è stato pescato un J o Q devo scartarlo in quanto non giocabili
        // per i round successivi controllo se il valore della carta è maggiore della lunghezza della mano
        // es. Se nel secondo round ho 9 carte in mano, il 10 diventa non giocabile, etc...
        if (Utils.isUnplayable(cardValue, hand.getHandSize()))
            return new Pair<>(card, true);

        // se arrivo qui la carta ha un valore tra 1 e 10, la devo quindi inserire al suo posto (se è libero)
        // oppure la scambio con un Jolly/K in caso abbia precedentemente inserito queste carte per fillare lo spazio
        if (hand.cardIsHide(cardValue) || isJolly(cardValue)) {
            // se la carta è Hide significa che la posso scambiare
            return new Pair<>(
                    hand.swapCardInPosition(cardValue, card),
                    false
            );
        }
        return new Pair<>(card, true);
    }

    /**
     * Overload del metodo toString utilizzato per debug e visualizzazione su terminale
     * @return una stringa che descrive la mano del giocatore (valori e semi)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < hand.getHandSize(); i++){
            s.append(hand.getCard(i).toString());

            if (i <  hand.getHandSize() - 1)
                s.append('\n');
        }
        return s.toString();
    }

    private boolean isJolly(int cardValue) {
        return (
                hand.getCard(cardValue - 1).getValue() == 0
                        ||
                hand.getCard(cardValue - 1).getValue() == 13
                );
    }

    public int getId() {return id;}
}
