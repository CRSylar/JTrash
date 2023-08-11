package Model;

public class Notification {

    private final TYPES type;
    private final Object o;
    public Notification(TYPES type, Object o) {
        this.o = o;
        this.type = type;
    }

    public TYPES getType() {return type;}
    public Object getObj() {return o;}

    public enum TYPES {
        FILLHAND
    }
}
