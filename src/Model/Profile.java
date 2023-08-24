package Model;

import Utilities.GameResult;

import java.io.Serializable;

/**
 * Profilo (serializzabile) del Giocatore umano
 * L'unico valore modificabile liberamente è l'avatar,
 * mentre il nome viene scelto al momento della creazione del profilo e non puo essere modificato
 * (se non distruggendo il profilo e creandone uno nuovo)
 * Un Profilo contiene, oltre il nome e l'avatar:
 * - il livello del giocatore
 * - le partite totali giocate
 * - quelle vinte
 * - quelle perse
 * - l'esperienza totale accumulata
 */
public class Profile implements Serializable {
    /**
     * Nome del giocatore
     */
    private final String name;
    /**
     * Livello raggiunto dal giocatore
     */
    private int level;
    /**
     * Partite totali giocate
     */
    private int totalGamesPlayed;
    /**
     * Vittorie
     */
    private int wins;
    /**
     * Sconfitte
     */
    private int losses;
    /**
     * Esperienza totale guadagnata
     */
    private int exp;

    /**
     * L'avatar selezionato tra quelli disponibili
     */
    private int pic;

    /**
     * Il costruttore del profilo è privato, si puo accedere al profilo tramite Singleton
     * il che garantisce unicità a runTime dell'istanza ed evita conflitti
     * @param name il nome da utilizzare per costruire il profilo
     */
    private Profile(String name) {
        this.name = name;
        this.level = 0;
        this.totalGamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.exp = 0;
        this.pic = 0;
    }

    /**
     * Impostazione nuovo avatar
     * @param newPic l'indice del nuovo avatar
     */
    public void setNewAvatar(int newPic) {
        this.pic = newPic;
    }

    /**
     * SINGLETON utilizzato per assicurare unicità al profilo
     */
    private static class ProfileSingleton {
        /**
         * L'istanza del profilo
         */
        private static Profile instance = null;
    }

    /**
     * Utilizzato per gestire la cancellazione del profilo e sua sostituzione
     * @param name il nome da utilizzare
     */
    public static void createNewProfile(String name) {
        if (ProfileSingleton.instance != null)
            throw new RuntimeException("Profile è un Singleton, non ci possono essere 2 profili");
        ProfileSingleton.instance = new Profile(name);
    }

    /**
     *
     * @return il profilo caricato
     */
    public static Profile getProfile() {return ProfileSingleton.instance;}

    /**
     * Verifica lo stato di caricamento del profilo, torna True se Profile == null
     * @return booleano
     */
    public static boolean isProfileNotLoaded() {return ProfileSingleton.instance == null;}

    /**
     * Carica un profilo, utilizzato per caricare un profilo alla lettura del file
     * @param p il profilo da caricare
     */
    public static void loadProfile(Profile p) {
        ProfileSingleton.instance = p;
    }

    /**
     *
     * @return il nome
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return il livello attuale
     */
    public long getLevel() {
        return level;
    }

    /**
     *
     * @return l'esperienza totale accumulata
     */
    public int getExp() {
        return exp;
    }

    /**
     *
     * @return il numero di partite finite con una sconfitta
     */
    public int getLosses() {
        return losses;
    }

    /**
     *
     * @return il totale di partite giocate
     */
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    /**
     *
     * @return il numero di partite finite con una vittoria
     */
    public int getWins() {
        return wins;
    }

    /**
     *
     * @return L'indice dell'avatar attualmente in uso
     */
    public int getPicture() {
        return pic;
    }

    /**
     * Aggiorna il profilo con il risultato di una partita terminata
     * @param result il risultato
     */
    public void updateProfile(GameResult.RESULT result) {
        totalGamesPlayed += 1;

        switch (result) {
            case DRAW -> exp+= 25;
            case LOSS -> losses += 1;
            case WIN -> { wins +=1; exp+= 50;}
        }

        checkForLevelUp();
    }

    /**
     * Controlla se l'esperienza accumulata è sufficiente per passare al livello successivo
     * e applica il risultato al profilo attuale
     */
    private void checkForLevelUp() {
        level = Math.min(
                4,
                exp / (50 *
                        Math.max(1,level) // evitare divisione per 0!
                ));

        if (level == 0 && totalGamesPlayed > 0)
            level += 1;
        if (level == 4 && exp >= 1000 && (wins/losses > 1))
            level += 1;
    }

    /**
     * Override del metodo toString per serializzazione
     * @return la stringa che rappresenta l'oggetto, da serializzare
     */
    @Override
    public String toString() {
        return name+":"+level+"-tg:"+totalGamesPlayed+"-w:"+wins+"-l:"+losses+"-exp:"+exp+"-profile"+pic;
    }
}
