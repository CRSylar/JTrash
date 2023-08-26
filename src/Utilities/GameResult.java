package Utilities;

import javax.management.InvalidAttributeValueException;

/**
 * Oggetto che definisce il tipo di un risultato di una partita terminata
 */
public class GameResult {

    /**
     * Il tipo di risultato (dal punto di vista del giocatore umano)
     */
    RESULT result;

    /**
     * Enumerazione che definisce i possibili risultati di una partita, dal punto di vista del giocatore "umano"
     */
    public enum RESULT {
        /**
         * Vittoria
         */
        WIN,
        /**
         * Pareggio
         */
        DRAW,
        /**
         * Sconfitta
         */
        LOSS
    }

    /**
     * Costruisce un'istanza di GameResult
     * @param winner il giocatore che ha vinto la partita appena terminata
     * @throws InvalidAttributeValueException Solleva un eccezione se gli viene passato un id vincitore incoerente
     * (al massimo puo valere 4 in quanto ci sono massimo 4 giocatori)
     */
    public GameResult(int winner) throws InvalidAttributeValueException {
        if (winner < 0 || winner > 4)
            throw new InvalidAttributeValueException("Can't allocate GameResult with an invalid winner");
        result = winner == 4 ?
                RESULT.DRAW : winner == 0 ?
                    RESULT.WIN : RESULT.LOSS;
    }

    /**
     * Accesso, in sola lettura, del risultato
     * @return il risultato usato per costruire l'istanza, che deve essere un RESULT
     */
    public RESULT getResult() {
        return result;
    }
}
