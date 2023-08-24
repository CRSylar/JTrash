package View;

import javax.swing.*;
import java.awt.*;

/**
 * Estende JOptionPane ed espone un solo metodo statico:
 * Show che mima il metodo showOptionDialogue ma utilizza
 * un JOptionPane gia esistente invece di crearne uno internamente.
 * Questo mi consente di impostare il valore da far assumere al JOptionPane
 * per causarne la chiusura
 */
public class AvatarsOptionDialogue extends JOptionPane{
    /**
     * Visualizza il Pane con le opzioni, come il metodo statico
     * JOptionPane.showOptionDialogue.
     * Prende gli stessi parametri, piu un JOptionPane gia istanziato
     * (mentre showOptionDialogue ne istanzia uno internamente)
     * @param parent il componente che funge da "Padre" per il pane
     * @param pane il pane creato esternamente
     * @param message il messaggio da visualizzare all'interno del pane
     * @param title il titolo della finestra di dialogo
     * @param optionType il tipo di Opzioni disponibili per chiudere il Pane
     * @param messageType il tipo di messaggio
     * @param icon Icona da utilizzare nel pane
     * @param values Array di Oggetti da visualizzare
     * @param initialValue valore iniziale del pane
     * @return l'oggetto (value) cliccato
     */
    @SuppressWarnings("deprecation")
    public static int show(Component parent,
                           JOptionPane pane,
                           Object message,
                           String title,
                           int optionType,
                           int messageType,
                           Icon icon,
                           Object[] values,
                           Object initialValue
    ) {
        pane.setMessage(message);
        pane.setMessageType(messageType);
        pane.setOptionType(optionType);
        pane.setIcon(icon);
        pane.setOptions(values);
        pane.setInitialValue(initialValue);
        pane.setComponentOrientation(parent.getComponentOrientation());
        JDialog dialog = pane.createDialog(parent, title);

        pane.selectInitialValue();
        dialog.show();
        dialog.dispose();

        Object selectedValue = pane.getValue();

        if (selectedValue == null) {
            return -1;
        }
        return (Integer) selectedValue;
    }
}
