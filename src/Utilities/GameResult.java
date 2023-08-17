package Utilities;

public class GameResult {
    private final boolean humanWon;

    public GameResult(int winner) {
        humanWon = winner == 0;
    }
    public boolean getResult() {return humanWon;}
}
