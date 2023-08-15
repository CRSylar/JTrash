package Model;

public class Notification {

    private final TYPES type;
    private final Object o;
    private final int numberOfPlayers;
    public Notification(TYPES type, Object o, int numberOfPlayers) {
        this.o = o;
        this.type = type;
        this.numberOfPlayers = numberOfPlayers;
    }

    public TYPES getType() {return type;}
    public Object getObj() {return o;}

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public enum TYPES {
        FILLHAND,
        DRAW,
        HAND,
        DISCARD
    }
}
