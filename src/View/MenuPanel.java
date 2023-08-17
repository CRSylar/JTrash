package View;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MenuPanel extends JPanel {
    BufferedImage bgImage;
    JLabel title;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        title = new JLabel("JTrash");
        title.setFont( new Font(
                "Book Antiqua", Font.ITALIC, 48
        ));
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 6;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(10, 10, 50, 10);
        add(title, titleConstraints);
        try {
            bgImage = ImageIO.read(
                    Objects.requireNonNull(getClass().getResource(
                            "/tableBackground.jpeg"
                    ))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0,0, getWidth(), getHeight(), null);
        Graphics2D g2d = (Graphics2D)g;
        title.setForeground(Color.LIGHT_GRAY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

}
