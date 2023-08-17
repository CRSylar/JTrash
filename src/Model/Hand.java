package Model;

import java.util.ArrayList;

/**
 * Oggetto che descrive la mano di un giocatore (reale o CPU)
 * le carte appartengono alla classe Card, la mano viene creata vuota
 * e riempita all'inizio del turno
 */
public class Hand {

    /**
     * Array che conterrà le carte in mano al giocatore
     */
    private final ArrayList<Card> hand;

    /**
     * Dimensione massima della mano del giocatore per questo turno
     */
    private final int maxSize;

    /**
     * Crea la mano inizialmente vuota
     * e con una dimensione massima iniziale di 10
     */
    public Hand() {
        hand = new ArrayList<>();
        maxSize = 10;
    }

    /**
     * Per i turni successivi al primo questo costruttore
     * prende un parametro per impostare la nuova dimensione massima
     * @param size la nuova dimensione massima per questo turno
     */
    public Hand(int size){
        hand = new ArrayList<>();
        maxSize = size;
    }


    /**
     * Metodo per aggiungere Carte alla mano del player
     * @param card La carta da aggiungere
     * @throws NullPointerException in caso la Card sia null solleva la relativa eccezione;
     * @throws IllegalStateException Se proviamo ad aggiungere carte a una mano gia Piena (maxSize);
     */
    public void addCard(Card card) {
        if (card == null)
            throw new NullPointerException("Can't add null card to hand");
        if (hand.size() == maxSize)
            throw new IllegalStateException("Hand already Full!");
        card.setHided(true);
        hand.add(card);
    }

    /**
     * Metodo per rimuovere una carta dalla mano del giocatore
     * @param position la posizione nella mano della carta da rimuovere
     * @throws ArrayIndexOutOfBoundsException in caso la posizione non sia valida
     */
    public void removeCard(int position) {
        if (position < 0 || position > hand.size() -1)
            throw new ArrayIndexOutOfBoundsException("Can' remove card at illegal index ( < 0 Or > length): "+position);
        hand.remove(position);
    }

    /**
     * Metodo per controllare la dimensione attuale della mano
     * @return un intero.
     */
    public int getHandSize() { return hand.size();}

    /**
     * Metodo per controllare la dimensione massima della mano
     * @return un intero.
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Metodo per accesso rapido a informazione sullo stato della
     * mano del giocatore, risponde alla domanda, puo accettare nuove carte ?
     * @return la risposta booleana alla domanda
     */
    public boolean canHoldMoreCard() {
        return (maxSize - hand.size()) > 0;
    }

    /**
     * Metodo che scambia la carta "pescata" con quella in mano, andando anche a impostare la carta
     * aggiunta alla mano come visibile, infine ritorna la carta "tolta" dalla mano per la nuova iterazione
     * @param position la posizione della carta da inserire, corrisponde al valore della nuova carta o a un Jolly
     * @param newCard l' istanza della carta da inserire
     * @return la carta "uscente" dalla mano del giocatore, che prima era coperta
     */
    public Card swapCardInPosition(int position, Card newCard) {
        Card tmp = hand.get(position-1);
        newCard.setHided(false);
        hand.set(position-1, newCard);
        return tmp;
    }

    /**
     * Metodo che controlla se la mano di un giocatore è completamente visibile
     * il che significa che ha fatto Trash e il turno finisce
     * @return booleano, true se la mano è tutta visibile (trash), false altrimenti
     */
    public boolean handFullyVisible() {
        for (Card card : hand){
            if (card.isHide())
                return false;
        }
        return true;
    }

    /**
     * Metodo di utility per fare una query sullo stato di una carta
     * ritorna il risultato del metodo isHide, un booleano
     * @param position la posizione della carta da cercare
     * @return lo stato di visibilità dalla carta
     */
    public boolean cardIsHide(int position) { return hand.get(position-1).isHide();}
    /**
     * Metodo per "estrarre" una carta dalla mano del giocatore,
     * NOTA la carta non viene eliminata dalla mano!
     * @param position la posizione nella mano della carta da rimuovere
     * @throws ArrayIndexOutOfBoundsException in caso la posizione non sia valida
     */
    public Card getCard(int position) {
        if (position < 0 || position > hand.size() -1)
            throw new ArrayIndexOutOfBoundsException("Can' remove card at illegal index ( < 0 Or > length): "+position);
        return hand.get(position);
    }
}
