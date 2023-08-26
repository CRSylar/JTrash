package View;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
     * TreeMap contente le Clip utilizzate durante il runTime.
     * Ottimizza la lettura dei file sorgente facendolo una sola volta allo startup
     */
    Map<CLIPTYPE, Clip> clipMap;

    /**
     *
     * @return L'istanza del Singleton Sound
     */
    public static Sounds getInstance() {
        if (instance == null)
            instance = new Sounds();
        return instance;
    }

    /**
     * Enumerazione che definisce dei "tag" per la riproduzione delle clip.
     * Questi vengono utilizzati dalla classe Sound come chiavi della TreeMap interna
     * garantendo un rapido accesso alla struttura dati
     */
    public enum CLIPTYPE {
        /**
         * File audio player-wins.wav
         */
        PLAYERWIN,
        /**
         * File audio flipcard.wav
         */
        DRAW,
        /**
         * File audio deck-shuffle.wav
         */
        SHUFFLE,
        /**
         * File audio Trash.wav
         */
        TRASH,
        /**
         * File audio ambient.wav
         */
        LOUNGE;
    }
    /**
     * Costruttore privato
     */
    private Sounds() {
        try {
            clipMap = new TreeMap<>();
            Clip playerWClip = AudioSystem.getClip();
            playerWClip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/player-wins.wav")));
            Clip ambientClip = AudioSystem.getClip();
            ambientClip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/ambient.wav")));
            Clip shuffleClip = AudioSystem.getClip();
            shuffleClip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/deck-shuffle.wav")));
            Clip drawCardClip = AudioSystem.getClip();
            drawCardClip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/flipcard.wav")));
            Clip trashClip = AudioSystem.getClip();
            trashClip.open(AudioSystem.getAudioInputStream(new File("assets/sounds/trash.wav")));

            clipMap.put(CLIPTYPE.PLAYERWIN, playerWClip);
            clipMap.put(CLIPTYPE.DRAW, drawCardClip);
            clipMap.put(CLIPTYPE.SHUFFLE, shuffleClip);
            clipMap.put(CLIPTYPE.LOUNGE, ambientClip);
            clipMap.put(CLIPTYPE.TRASH, trashClip);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Riproduce una traccia audio dall'inizio, con possibilità di impostare una riproduzione in loop
     * @param cliptype il tipo di clip come definito da Enumerazione
     * @param loop riproduzione in loop si/no
     */
    public void play(CLIPTYPE cliptype, boolean loop) {
        Clip c = clipMap.get(cliptype);
        c.setMicrosecondPosition(0);
        c.start();
        if (loop)
            c.loop(Clip.LOOP_CONTINUOUSLY);
    }


    /**
     * Stoppa la traccia audio relativa al tipo passato
     * @param cliptype il tipo di clip come definito da Enumerazione
     */
    public void stop(CLIPTYPE cliptype) {
        clipMap.get(cliptype).stop();
    }
}
