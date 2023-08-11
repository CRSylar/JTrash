package Utilities;

import Model.Card;
import Model.Hand;

public class Utils {

    /**
     * Metodo che controlla la pila degli scarti per decidere se pescare da li piuttosto che dal mazzo
     *
     * @param discardPileTopCard la pila degli scarti su cui fare peek per la carta in cima
     * @param playerHand la mano del giocatore
     * @return un booleano che risponde alla domanda "Pesco dalla pila degli scarti?"
     */
    public static boolean tryToDrawFromDiscard(Card discardPileTopCard, Hand playerHand) {

        int cardValue = discardPileTopCard.getValue();
        // Se la carta è un Jolly o un Re le prendo sempre (valgono entrambe da Jolly)
        if (cardValue == 0 || cardValue == 13)
            return true;
        // se la carta è un J o Q NON la prendo MAI in quanto non puo essere posizionata
        // e va sempre scartata.
        if (isUnplayable(cardValue, playerHand.getHandSize()))
            return false;

        // Controllo se posso sostituire la carta negli scarti con un Jolly/K
        if (playerHand.getCard(cardValue-1).getValue() == 0 || playerHand.getCard(cardValue-1).getValue() == 13)
            return true;
        // se il giocatore ha bisogno di quella carta (perché è coperta) la pesca
        return playerHand.getCard(cardValue-1).isHide();
    }

    /**
     * Metodo di comodo per verificare che una determinata carta sia "giocabile"
     * dal giocatore, es. Se la mia mano contiene massimo 5 carte, il 6,7,8...
     * NON sono carte giocabili
     * @param cardValue il valore della carta da controllare
     * @param handSize la dimensione della mano del giocatore, definisce appunto il "Valore massimo giocabile"
     * @return un booleano che risponde alla domanda "Posso giocare questa carta?"
     */
    public static boolean isUnplayable(int cardValue, int handSize) {
        return cardValue > handSize;
    }
}
