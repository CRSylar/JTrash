package Model;

import java.util.ArrayList;

/**
 * Classe che Rappresenta un Mazzo di carte "da Poker"
 * completo di due Jolly
 */
public class Deck {
    /**
     * Array di 54 carte
     */
    private final ArrayList<Card> deck = new ArrayList<>(54);

    /**
     * Tiene traccia di quante carte sono state pescate
     * dal mazzo
     */
    private int cardsDrawn;

    /**
     * Costruttore del mazzo, alloca le 54 carte che compongono il deck.
     * Crea un mazzo NON mischiato.
     */
    public Deck() {
        int i = 0;
        for ( int suit = 0; suit <= 3; suit++ ) {
            for ( int value = 1; value <= 13; value++ ) {

                deck.add(i, new Card(value,suit));
                i++;
            }
        }
        deck.add(i++, new Card());
        deck.add(i, new Card());
        cardsDrawn = 0;
        shuffle();
    }

    /**
     * Rimette tutte le carte pescate in precedenza dentro il mazzo
     * e lo mischia per avere un ordine pseudocasuale
     */
    public void shuffle() {
        for (int i = deck.size()-1; i > 0; i--){
            int rand = (int) (Math.random() * (i + 1));
            Card tmp = deck.get(i);
            deck.set(i, deck.get(rand));
            deck.set(rand, tmp);
        }
        cardsDrawn = 0;
    }

    /**
     * Funzione tiene traccia delle carte rimaste nel mazzo
     * risolvendo il problema di "pescare" da un mazzo vuoto
     *
     * @return il numero di carte ancora presenti nel mazzo, come intero
     */
    public int cardLeft() {
        return deck.size() - cardsDrawn;
    }

    /**
     * Pesca una carta dal mazzo
     * @return la carta pescata
     * @throws IllegalStateException se non ci sono carte da pescare
     */
    public Card drawCard() {
        if (cardsDrawn == deck.size()) throw new IllegalStateException("Deck is Empty");

        return deck.get(cardsDrawn++);
    }

    /**
     * Metodo usate per fare il merge tra due Deck, utilizzato per partite con Tre o Quattro
     * giocatori (servono due Mazzi).
     * Nota: dopo il merge viene fatto un nuovo shuffle
     * @param d2 il mazzo da unire al this.deck
     */
    public void mergeWithOtherDeck(ArrayList<Card> d2){
        deck.addAll(d2);
        shuffle();
    }

    /**
     *
     * @return istanza del mazzo in uso
     */
    public ArrayList<Card> getDeck() { return deck;}
}
