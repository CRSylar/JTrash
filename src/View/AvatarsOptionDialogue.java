package View;

import javax.swing.*;
import java.awt.*;

public class AvatarsOptionDialogue extends JOptionPane{

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
