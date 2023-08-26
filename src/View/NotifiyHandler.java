package View;

/**
 * Interfaccia degli Handler delle notifiche utilizzata
 * per implementare il pattern Strategy
 */
public interface NotifiyHandler {

    /**
     * Esegue la/e operazione/i
     * specifiche per ogni tipo di evento notificato
     */
    void handle();
}
