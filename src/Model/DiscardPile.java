package Model;

import java.util.Stack;

/**
 * Pila degli scarti, il giocatore puo pescare dal mazzo
 * o la prima carta dalla pila degli scarti
 * nel caso limite in cui il mazzo finisse la Pila degli scarti
 * viene "svuotata" e rimessa nel mazzo (come coda Fi-Lo)
 */
public class DiscardPile {

    /**
     * Stack di carte scartate
     * L'utilizzo di una coda (Li-Fo) come lo stack
     * consente di avere il metodo pop e push
     * che sono le uniche due azioni possibili sugli scarti
     */
    private final Stack<Card> pile;

    /**
     * Costruttore per la Pila degli scarti
     */
    public DiscardPile() {
        pile = new Stack<>();
    }

    /**
     * Metodo per aggiungere una carta alla pila degli scarti
     * @param card la carta da aggiungere
     * @throws NullPointerException se la carta è un puntatore a null;
     */
    public void addCard(Card card) {
        if (card == null)
            throw new NullPointerException("Can't add null to the Pile!");
        pile.push(card);
    }

    /**
     * Metodo per pescare una carta dalla cima della pila degli scarti
     *
     * @return la carta in cima alla pila
     */
    public Card drawFromPile() {
        return pile.pop();
    }

    public Card drawFromIndex(int index) {
        return pile.get(index);
    }

    /**
     * Metodo che ritorna la size dello stack
     * @return la dimensione come intero
     */
    public int size() { return pile.size();}

    /**
     * Metodo che "guarda" la carta in cima allo stack
     * NOTA NON la rimuove
     * @return la carta
     */
    public Card peek() {return pile.peek();}

    /**
     * Metodo usato per svuotare la pila degli scarti
     */
    public void clearPile() { pile.clear();}

}
