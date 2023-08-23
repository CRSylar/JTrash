package Utilities;

/**
 * Un oggetto che crea una coppia di valori, solitamente usato come Wrapper per
 * funzioni che hanno necessità di restituire al chiamante 2 valori
 * definiti come Left e Right, che possono essere rappresentati da qualsiasi tipo
 * @param <L> qualsiasi oggetto
 * @param <R> qualsiasi oggetto
 */
public class Pair<L, R> {
    /**
     * Primo valore della coppia, left di tipo L
     */
    L left;
    /**
     * Secondo valore della coppia, right di tipo R
     */
    R right;

    /**
     * Costruttore della coppia
     * @param left qualsiasi oggetto
     * @param right qualsiasi oggetto
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Getter del valore sinistro
     * @return left - del tipo utilizzato per costruire la coppia
     */
    public L getLeft() {return left;}
    /**
     * Getter del valore destro
     * @return right - del tipo utilizzato per costruire la coppia
     */
    public R getRight() {return right;}
}
