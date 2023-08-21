package View;

import Model.SUITS;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Singleton utilizzata per caricare Tutti gli asset grafici sotto forma di
 * BufferedImage, cosi da poterli avere sempre disponibili ed essere sicuri di caricarli
 * una sola volta durante il runtime
 */
public class AssetLoader {

    /**
     * Rappresenta l'immagine del retro di una carta da gioco
     */
    private BufferedImage cardBack;
    /**
     * Array che conterrà le immagini di tutte le carte di Fiori
     */
    private final ArrayList<BufferedImage> Clubs = new ArrayList<>();
    /**
     * Array che conterrà le immagini di tutte le carte di Picche
     */
    private final ArrayList<BufferedImage> Spades = new ArrayList<>();

    /**
     * Array che conterrà le immagini di tutte le carte di Cuori
     */
    private final ArrayList<BufferedImage> Hearts = new ArrayList<>();
    /**
     * Array che conterrà le immagini di tutte le carte di Quadri
     */
    private final ArrayList<BufferedImage> Diamonds = new ArrayList<>();
    /**
     * Array che conterrà l'immagine del jolly nero
     */
    private BufferedImage BlackJolly;

    /**
     * Asset che rappresenta la pila vuota (per Deck e pila degli scarti)
     */
    private BufferedImage emptyPile;
    /**
     * Array che conterrà l'immagine del jolly rosso
     */
    private BufferedImage RedJolly;

    private List<BufferedImage> avatars = new ArrayList<>();

    /**
     * Costruttore PRIVATO, carica gli tutti gli asset nei relativi membri
     * @throws IOException in caso non sia possibile leggere correttamente uno degli asset
     */
    private AssetLoader() {
        try {
            avatars.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/avatars/avatar1.png"))));
            avatars.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/avatars/avatar2.png"))));
            avatars.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/avatars/avatar3.png"))));
            avatars.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/avatars/avatar4.png"))));

            cardBack = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/cardBack.png")));
            BlackJolly = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/53.png")));
            RedJolly = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/54.png")));
            emptyPile = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/emptyPile.png")));
            for (int i = 1; i <= 13; i++){
                Clubs.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + i + ".png"))));
                Spades.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + (i+13) + ".png"))));
                Hearts.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + (i+26) + ".png"))));
                Diamonds.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/" + (i+39)+ ".png"))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Classe statica che definisce il Singleton
     */
    private static class AssetSingleton {
        /**
         * Istanza della classe AssetLoader
         * che verrà effettivamente utilizzata
         * per tutta la vita del programma
         */
        private static final AssetLoader instance = new AssetLoader();
    }

    /**
     * Metodo utilizzato per accedere all'istanza della classe
     * @return l'istanza della classe, sempre la stessa grazie al SingletonPattern
     *
     */
    public static AssetLoader getInstance() {return AssetSingleton.instance;}

    /**
     * Metodo che ritorna l'immagine del retro di una carta
     * @return l'asset caricato alla creazione del singleton
     */
    public BufferedImage getCardBack() { return cardBack;}

    /**
     * Metodo che ritorna l'immagine creata per rappresentare
     * una pila vuota, usata sia per il mazzo che per la pila degli scarti
     * @return l'asset caricato alla creazione del singleton
     */
    public BufferedImage getEmptyPile() {return emptyPile;}

    public List<BufferedImage> getAvatars() {
        return this.avatars;
    }

    /**
     * Metodo che ritorna l'immagine del Jolly nero
     * @return l'immagine del Jolly nero
     */
    public BufferedImage getBlackJolly() {return BlackJolly;}
    /**
     * Metodo che ritorna l'immagine del Jolly rosso
     * @return l'immagine del Jolly rosso
     */
    public BufferedImage getRedJolly() {return RedJolly;}

    /**
     * Metodo utilizzato per recuperare l'immagine di una specifica carta (Jolly esclusi)
     * @param value il valore della carta (da 1 a 13) dove 1=Asso e 13=King
     * @param suits il seme della carta richiesta
     * @return l'immagine della carta del valore e seme richiesto
     */
    public BufferedImage getCard(int value, SUITS suits) {
        if (value == 0) return getBlackJolly();

        if (value < 1 || value > 13) throw new IllegalArgumentException("Card Value must be a number from 1 o 13");
        BufferedImage out;
        switch (suits){
            case SPADES -> out = Spades.get(value-1);
            case CLUBS -> out = Clubs.get(value-1);
            case DIAMONDS -> out = Diamonds.get(value-1);
            case HEARTS -> out = Hearts.get(value-1);
            default -> throw new IllegalArgumentException("Suits not valid");
        }
        return out;
    }

    public BufferedImage getRotatedCard(BufferedImage card) {
        int w = card.getWidth();
        int h = card.getHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage out = new BufferedImage(h, w, type);
        Graphics2D g2 = out.createGraphics();
        double x = (h-w)/2.0;
        double y = (w-h)/2.0;
        AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
        transform.rotate(Math.PI/2, w/2.0, h/2.0);
        g2.drawImage(card, transform, null);
        g2.dispose();

        return out;
    }
}
