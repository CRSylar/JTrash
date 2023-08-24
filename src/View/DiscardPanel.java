package View;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel che rappresenta la pila degli scarti ed è gestito
 * da un CardLayout per creare un vero e proprio "stack" di carte.
 */
public class DiscardPanel extends JPanel {
    /**
     * Il CardLayout da utilizzare
     */
    private final CardLayout cl;

    /**
     * Costruisce la pila degli scarti, che inizialmente
     * contiene solo una Jlabel che mostra che la pila è vuota
     */
    public DiscardPanel() {
        JLabel discardLabel = new JLabel();
        setLayout(new CardLayout());
        cl = (CardLayout)getLayout();
        discardLabel.setIcon(new ImageIcon(AssetLoader.getInstance().getEmptyPile()));
        add(discardLabel);
        setPreferredSize(new Dimension(78, 114));
        setBackground(Color.WHITE);
    }

    /**
     * Aggiunge il componente al Panel e flippa la lista per mostrare l'ultima carta inserita (questa)
     * @param comp   the component to be added
     * @return the added component
     */
    @Override
    public Component add(Component comp) {
        super.add(comp);
        System.out.println("Discard Panel Size: "+getComponentCount());
        cl.last(this);
        return comp;
    }

    /**
     * Rimuove la carta in cima alla pila degli scarti
     */
    public void removeTop() {
        int i = getComponentCount();
        remove(i-1);
        System.out.println("Discard Panel Size: "+getComponentCount());
        cl.last(this);
    }

    /**
     * Svuota il Panello e lo reimposta
     */
    public void resetPanel() {
        removeAll();
        add(new JLabel(new ImageIcon(AssetLoader.getInstance().getEmptyPile())));
        setPreferredSize(new Dimension(78, 114));
        setBackground(Color.WHITE);

    }
}
