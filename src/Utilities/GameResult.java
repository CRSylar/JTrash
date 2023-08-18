package Utilities;

public class GameResult {

    RESULT result;

    public enum RESULT {
        WIN,
        DRAW,
        LOSS
    }

    public GameResult(int winner) {
        result = winner == 4 ?
                RESULT.DRAW : winner == 0 ?
                    RESULT.WIN : RESULT.LOSS;
    }

    public RESULT getResult() {
        return result;
    }
}
