package View;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Singleton che gestisce la riproduzione di suoni,
 * in formato non compresso (es. wav)
 */
public class Sounds {
    /**
     * L'istanza della classe
     */
    private static Sounds instance;

    /**
     * Le clip eseguite, mi serve per stoppare successivamente tutte quelle che sono ancora
     * in esecuzione
     */
    ArrayList<Clip> clips;
    public static Sounds getInstance() {
        if (instance == null)
            instance = new Sounds();
        return instance;
    }

    /**
     * Costruttore privato
     */
    private Sounds() {
        clips = new ArrayList<>();
    }

    /**
     * Apre e riproduce un file Audio non compresso
     * @param filename la path al file da riprodurre
     * @param loop se riprodurre in loop o no
     */
    public void play(String filename,boolean loop) {
        try {
            AudioInputStream aIs = AudioSystem.getAudioInputStream(new File(filename));
            Clip clip = AudioSystem.getClip();
            clip.open(aIs);

            clip.start();
            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            clips.add(clip);
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stoppa tutte le clip ancora in esecuzione
     * e svuota l'array delle clip
     */
    public void stop() {
        for (Clip c : clips) {
            if (c != null && c.isRunning())
                c.stop();
        }
        clips.clear();
    }
}
