package Utilities;

public class GameResult {
    private final boolean humanWon;
    private final boolean draw;

    public GameResult(int winner) {
        humanWon = winner == 0;
        draw = winner == 4;
    }
    public boolean hasHumanWon() {return humanWon;}

    public boolean isDraw() {
        return draw;
    }
}
