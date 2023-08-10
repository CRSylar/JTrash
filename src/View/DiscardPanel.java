package View;

import javax.swing.*;
import java.awt.*;

public class DiscardPanel extends JPanel {

    private final CardLayout cl;
    public DiscardPanel() {
        JLabel discardLabel = new JLabel();
        setLayout(new CardLayout());
        cl = (CardLayout)getLayout();
        discardLabel.setIcon(new ImageIcon(AssetLoader.getInstance().getEmptyPile()));
        add(discardLabel);
        setPreferredSize(new Dimension(78, 114));
        setBackground(Color.WHITE);
    }

    @Override
    public Component add(Component comp) {
        super.add(comp);
        System.out.println("Discard Panel Size: "+getComponentCount());
        cl.last(this);
        return comp;
    }

    public void removeTop() {
        int i = getComponentCount();
        remove(i-1);
        System.out.println("Discard Panel Size: "+getComponentCount());
        cl.last(this);
    }

    public void resetPanel() {
        removeAll();
        add(new JLabel(new ImageIcon(AssetLoader.getInstance().getEmptyPile())));
        setPreferredSize(new Dimension(78, 114));
        setBackground(Color.WHITE);

    }
}
