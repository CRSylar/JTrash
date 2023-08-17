package Utilities;

import Model.Profile;

import java.io.*;
import java.util.ArrayList;

public class FileWriterReader {

    private FileWriterReader() {}

    private static class WriterReaderSingleton {
        private static final FileWriterReader instance = new FileWriterReader();
    }
    public static FileWriterReader getInstance() {return WriterReaderSingleton.instance;}

    public void Write(Profile profile) throws IOException {
        FileOutputStream f = new FileOutputStream("savedGames.dat");
        ObjectOutputStream o = new ObjectOutputStream(f);

        o.writeObject(profile);
        o.close();
        f.close();
    }

    public Profile Read() throws IOException, ClassNotFoundException {
        try {
            FileInputStream fi = new FileInputStream("savedGames.dat");
            ObjectInputStream oi = new ObjectInputStream(fi);
            ArrayList<Profile> profiles = new ArrayList<>();
            profiles.add((Profile)oi.readObject());
            oi.close();
            fi.close();
            
        } catch (EOFException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
