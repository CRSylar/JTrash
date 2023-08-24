package View;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Pannello che rappresenta il menu iniziale e Hub centrale del gioco.
 * Da qui è possibile iniziare una partita con 2/3/4 giocatori,
 * visualizzare i dati del profilo e modificare l'avatar
 * uscire dal gioco
 */
public class MenuPanel extends JPanel {
    /**
     * Immagine di Background del pannello
     */
    BufferedImage bgImage;
    /**
     * Titolo del gioco da disegnare nel pannello
     */
    JLabel title;

    /**
     * Costruisce un'istanza di Menu
     * impostando il titolo, il font, il layoutManager
     * e posizionando il background
     */
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

    /**
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0,0, getWidth(), getHeight(), null);
        Graphics2D g2d = (Graphics2D)g;
        title.setForeground(Color.LIGHT_GRAY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

}
