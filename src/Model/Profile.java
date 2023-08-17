package Model;

import java.io.Serializable;

public class Profile implements Serializable {
    private final String name;
    private final int level;
    private final int totalGamesPlayed;
    private final int wins;
    private final int losses;
    private final long exp;

    public Profile(String name) {
        this.name = name;
        this.level = 0;
        this.totalGamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.exp = 0;
    }

    @Override
    public String toString() {
        return name+":"+level+"-tg:"+totalGamesPlayed+"-w:"+wins+"-l:"+losses+"-exp:"+exp;
    }
}
