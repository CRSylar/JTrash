package View;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;

public class Sounds {
    private static Sounds instance;

    ArrayList<Clip> clips;
    public static Sounds getInstance() {
        if (instance == null)
            instance = new Sounds();
        return instance;
    }

    private Sounds() {
        clips = new ArrayList<>();
    }

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

    public void stop() {
        for (Clip c : clips) {
            if (c != null && c.isRunning())
                c.stop();
        }
    }
}
