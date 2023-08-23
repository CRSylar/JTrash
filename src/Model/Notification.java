package Model;

/**
 * Definizione di una Generica notifica, utilizzata nel Pattern Observer/Observable
 */
public class Notification {

    /**
     * Identifica il tipo della notifica
     */
    private final TYPES type;
    /**
     * Il "Corpo" della notifica, puo essere un oggetto qualsiasi
     */
    private final Object o;
    /**
     * Numero dei giocatori
     */
    private final int numberOfPlayers;

    /**
     * Costruisce una notifica, il numero dei giocatori serve per gestire la visualizzazione delle carte sul tavolo
     * @param type il tipo della notifica, di tipo Notification.TYPE
     * @param o il "corpo" della notifica, un qualsiasi oggetto
     * @param numberOfPlayers il numero dei giocatori
     */
    public Notification(TYPES type, Object o, int numberOfPlayers) {
        this.o = o;
        this.type = type;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     *
     * @return il tipo di questa notifica
     */
    public TYPES getType() {return type;}

    /**
     *
     * @return il Messaggio (corpo) di questa notifica
     */
    public Object getObj() {return o;}

    /**
     *
     * @return il numero dei giocatori della partita in corso
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Definizione dei tipi di notifica gestiti
     * NOTA. NON vengono gestiti altri tipi di notifica
     */
    public enum TYPES {
        FILLHAND,
        DRAW,
        HAND,
        DISCARD
    }
}
