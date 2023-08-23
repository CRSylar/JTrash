package Model;

/**
 * Un Oggetto ti tipo Carta rappresenta una delle 54 carte da Gioco
 * Ogni carta ha un Seme associato.
 */
public class Card {
    /**
     * Enumerazione che definisce i semi delle carte;
     * rispettivamente:
     * Picche,
     * Cuori
     * Quadri
     * Fiori.
     */
    public enum SUITS {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    /**
     * Il valore della carta, da 0 a 13:
     * 0 Rappresenta il Jolly,
     * da 11 a 13 rappresentano rispettivamente J, Q, K
     */
    private final int value;

    /**
     * Il seme della carta
     * Notare che i Jolly NON hanno seme
     */
    private final SUITS suit;

    /**
     * Variabile che contiene lo stato di visibilità della
     * carta, di default sono tutte girate a faccia in giu
     * (isHided = true)
     */
    private boolean isHided = true;
    /**
     * Crea un Jolly, assegnando il valore zero alla carta e null al seme
     */
    public Card() {
        this.value = 0;
        this.suit = null;
    }

    /**
     * Crea una Carta con il valore e il seme Specificato
     * @param value valore della carta, da 1 a 13, zero ammesso SOLO per i Jolly
     * @param suits seme della carta, deve essere un valore tra 0 e 3 come definito nell'enumerazione apposita
     * @throws IllegalArgumentException in caso uno dei due valori non rispetti i vincoli definiti
     */
    public Card(int value, int suits) {
        if (value < 1 || value > 13)
            throw new IllegalArgumentException("Illegal Value for a card");
        this.value = value;
        switch (suits) {
            case 0 -> this.suit = SUITS.CLUBS;
            case 1 -> this.suit = SUITS.SPADES;
            case 2 -> this.suit = SUITS.HEARTS;
            case 3 -> this.suit = SUITS.DIAMONDS;
            default -> throw new IllegalArgumentException("Illegal SUIT for the new Card");
        }
    }

    /**
     * Metodo getter per il seme della carta
     * @return il seme definito nell'enumerazione o null in caso di Jolly
     */
    public SUITS getSuit() {
        return this.suit;
    }

    /**
     * Metodo getter per interrogare il valore della carta
     * @return il valore della carta, da 11 a 13 il valore è rispettivamente J, Q, K
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Overload del metodo toString,
     * Comodo per ritornare una rappresentazione come stringa
     * della Carta
     * @return il valore e il seme della Carta come stringa
     */
    public String toString() {
        if (this.suit == null)
            return "Jolly";
        String s = "";
        switch (this.value) {
            case 1 -> s += "Ace of ";
            case 2 -> s += "Two of ";
            case 3 -> s += "Three of ";
            case 4 -> s += "Four of ";
            case 5 -> s += "Five of ";
            case 6 -> s += "Six of ";
            case 7 -> s += "Seven of ";
            case 8 -> s += "Eight of ";
            case 9 -> s += "Nine of ";
            case 10 -> s += "Ten of ";
            case 11 -> s += "Jack of ";
            case 12 -> s += "Queen of ";
            case 13 -> s += "King of ";
        }
        return s+this.suit;
    }

    public boolean isHide() { return isHided;}
    public void setHided(boolean state) { this.isHided = state;}

}
