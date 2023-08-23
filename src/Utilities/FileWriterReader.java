package Utilities;

import Model.Profile;

import java.io.*;

/**
 * SINGLETON che si occupa
 * di effettuare lettura e scrittura del file di salvataggio
 * dati (profilo).
 * Lancia eccezioni
 */
public class FileWriterReader {

    private FileWriterReader() {}

    private static class WriterReaderSingleton {
        private static final FileWriterReader instance = new FileWriterReader();
    }

    /**
     * Ritorna l'istanza di FileWriterReader
     * @return FileWriteReader instance
     */
    public static FileWriterReader getInstance() {return WriterReaderSingleton.instance;}

    /**
     * Metodo che si occupa di creare un FileOutputStream e l'ObjectOutputStream, necessari per scrivere il file
     * @param profile le informazioni da salvare, ovvero il profilo del giocatore
     * @throws IOException lancia eccezioni in caso non sia possibile scrivere il file.
     */
    public void Write(Profile profile) throws IOException {
        FileOutputStream f = new FileOutputStream("savedGames.dat");
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(profile);
        o.close();
        f.close();
    }

    /**
     * Metodo che si occupa delle operazioni di Lettura
     * crea il FileInputStream e l'ObjectInputStream necessari
     * e parsa i dati salvati, sotto forma di Profilo (classe Profile)
     * @return una istanza di classe Profile, ovvero il profilo letto dal file
     * @throws IOException lancia questa eccezione in caso non sia possibile leggere il file
     * @throws ClassNotFoundException lancia questa eccezione in caso il file sia stato corrotto
     * e/o non sia stato possibile deserializzare le informazioni come Profile
     */
    public Profile Read() throws IOException, ClassNotFoundException {
        try {
            FileInputStream fi = new FileInputStream("savedGames.dat");
            ObjectInputStream oi = new ObjectInputStream(fi);
            Profile profile = (Profile)oi.readObject();
            oi.close();
            fi.close();
            return  profile;
        } catch (EOFException e) {
            return null;
        }
    }
}
