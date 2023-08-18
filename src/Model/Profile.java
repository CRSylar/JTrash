package Model;

import Utilities.GameResult;

import java.io.Serializable;

public class Profile implements Serializable {
    private final String name;
    private int level;
    private int totalGamesPlayed;
    private int wins;
    private int losses;
    private int exp;

    private Profile(String name) {
        this.name = name;
        this.level = 0;
        this.totalGamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.exp = 0;
    }

    private static class ProfileSingleton {
        private static Profile instance = null;
    }

    public static void createNewProfile(String name) {
        ProfileSingleton.instance = new Profile(name);
    }

    public static Profile getProfile() {return ProfileSingleton.instance;}

    public static boolean isProfileLoaded() {return ProfileSingleton.instance != null;}

    public static void loadProfile(Profile p) {
        ProfileSingleton.instance = p;
    }

    public String getName() {
        return name;
    }

    public long getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void updateProfile(GameResult.RESULT result) {
        totalGamesPlayed += 1;

        switch (result) {
            case DRAW -> exp+= 25;
            case LOSS -> losses += 1;
            case WIN -> { wins +=1; exp+= 50;}
        }

        checkForLevelUp();
    }

    private void checkForLevelUp() {
        level = Math.min(
                4,
                exp / Math.max(
                        1,(50 * level) // evitare divisione per 0!
                ));
        if (level == 0 && totalGamesPlayed > 0)
            level += 1;

        if (level == 4 && exp >= 1000 && (wins/losses > 1))
            level += 1;
    }

    @Override
    public String toString() {
        return name+":"+level+"-tg:"+totalGamesPlayed+"-w:"+wins+"-l:"+losses+"-exp:"+exp;
    }
}
