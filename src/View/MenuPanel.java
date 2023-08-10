package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MenuPanel extends JPanel implements ActionListener {
    BufferedImage bgImage;

    public Timer timer = new Timer(30, this);

    float alpha;
    JLabel title;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
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
        if (alpha > 0.9)
            title.setForeground(Color.LIGHT_GRAY);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        alpha += 0.01f;
        if (alpha > 1) {
            alpha = 1;
            timer.stop();
        }
        repaint();
    }
}
